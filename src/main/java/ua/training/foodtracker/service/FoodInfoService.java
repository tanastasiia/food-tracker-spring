package ua.training.foodtracker.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.training.foodtracker.config.LocaleConfiguration;
import ua.training.foodtracker.dto.FoodDto;
import ua.training.foodtracker.dto.FoodInfoDto;
import ua.training.foodtracker.dto.MessageDto;
import ua.training.foodtracker.dto.lists.FoodInfosDto;
import ua.training.foodtracker.dto.lists.FoodNamesDto;
import ua.training.foodtracker.entity.Food;
import ua.training.foodtracker.entity.FoodInfo;
import ua.training.foodtracker.entity.Role;
import ua.training.foodtracker.entity.User;
import ua.training.foodtracker.exception.FoodExistsException;
import ua.training.foodtracker.repository.FoodInfoRepository;
import ua.training.foodtracker.validation.Messages;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service for {@link FoodInfo} entity
 */
@Slf4j
@Service
public class FoodInfoService {

    private FoodInfoRepository foodInfoRepository;
    private ServiceUtils serviceUtils;

    public FoodInfoService(FoodInfoRepository foodInfoRepository, ServiceUtils serviceUtils) {
        this.foodInfoRepository = foodInfoRepository;
        this.serviceUtils = serviceUtils;
    }


    /**
     * Find food by food name available for the user
     */
    public Optional<FoodInfo> findFoodByFoodNameAndUser(String foodName, Long userId) {
        List<FoodInfo> foodInfos =  foodInfoRepository.findAllByFoodNameOrFoodNameUaAndUserIdOrGlobal(foodName, userId);
        if(foodInfos.size() != 1){
            return foodInfos.stream().filter(foodInfo -> foodInfo.getUser().getId().equals(userId)).findFirst();
        }  else {
            return Optional.ofNullable(foodInfos.get(0));
        }
    }

    /**
     * Save new food
     *
     * @retuen success or fail localized message
     */
    @Transactional
    public MessageDto save(FoodDto foodDto, User user) throws FoodExistsException {
        if (!findFoodByFoodNameAndUser(foodDto.getName(), user.getId()).isPresent()) {
            foodInfoRepository.save(foodInfoBuilderToSave(foodDto, user));
            return MessageDto.builder()
                    .message(serviceUtils.getLocalizedMessage(serviceUtils.ADD_FOOD_SUCCESS))
                    .build();

        } else throw new FoodExistsException();
    }

    /**
     * Build foodInfo depending on locale and admin role.
     * For user role: available only one food name field, and it gets written in name or name_ua column depending on current user locale
     * For admin user: available both fields and isGlobal field;
     *
     * @param foodDto to build food from
     * @param user    to take role from
     * @return foodInfo object
     */
    private FoodInfo foodInfoBuilderToSave(FoodDto foodDto, User user) {
        boolean isGlobal = Optional.ofNullable(foodDto.getIsGlobal()).orElse(false);
        Food.FoodBuilder food = Food.builder()
                .carbs(serviceUtils.toMilligrams(foodDto.getCarbs()))
                .protein(serviceUtils.toMilligrams(foodDto.getProtein()))
                .fat(serviceUtils.toMilligrams(foodDto.getFat()))
                .calories(foodDto.getCalories());

        if (user.getRole().equals(Role.ROLE_USER.name())) {
            food = serviceUtils.isLocaleUa()
                    ? food.nameUa(foodDto.getName())
                    : food.name(foodDto.getName());
        } else {
            food = food.name(foodDto.getName()).nameUa(foodDto.getNameUa());
        }
        return FoodInfo.builder()
                .food(food.build())
                .isGlobal(isGlobal)
                .user(user)
                .build();
    }

    /**
     * Find all food
     *
     * @return page of food infos
     */
    public FoodInfosDto findAll(Pageable pageable) {
        return FoodInfosDto.builder()
                .foodInfos(foodInfoRepository
                        .findAll(pageable)
                        .map(foodInfo -> FoodInfoDto.builder()
                                .isGlobal(foodInfo.getIsGlobal())
                                .username(foodInfo.getUser() == null ? null : foodInfo.getUser().getUsername())
                                .foodDto(FoodDto.builder()
                                        .name(foodInfo.getFood().getName())
                                        .nameUa(foodInfo.getFood().getNameUa())
                                        .carbs(serviceUtils.toGrams(foodInfo.getFood().getCarbs()))
                                        .protein(serviceUtils.toGrams(foodInfo.getFood().getProtein()))
                                        .fat(serviceUtils.toGrams(foodInfo.getFood().getFat()))
                                        .calories(foodInfo.getFood().getCalories())
                                        .build())
                                .build()))
                .build();


    }

    /**
     * List of food names available for user
     */
    public FoodNamesDto foodNamesList(Long userId) {
        return FoodNamesDto.builder()
                .foodNames(foodInfoRepository.findAllByUser_IdOrIsGlobalTrue(userId).stream()
                        .map(foodInfo -> serviceUtils.getLocalizedFoodName(foodInfo.getFood()))
                        .filter(str -> !str.isEmpty())
                        .collect(Collectors.toList()))
                .build();
    }


}
