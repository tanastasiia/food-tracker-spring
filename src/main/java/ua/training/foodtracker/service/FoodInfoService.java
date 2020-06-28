package ua.training.foodtracker.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.training.foodtracker.dto.FoodDto;
import ua.training.foodtracker.dto.FoodInfoDto;
import ua.training.foodtracker.dto.lists.FoodInfosDto;
import ua.training.foodtracker.dto.lists.FoodNamesDto;
import ua.training.foodtracker.entity.Food;
import ua.training.foodtracker.entity.FoodInfo;
import ua.training.foodtracker.entity.User;
import ua.training.foodtracker.repository.FoodInfoRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        return foodInfoRepository.findAllByFoodNameOrFoodNameUaAndUserIdOrGlobal(foodName, userId);
    }

    /**
     * Save new food
     */
    @Transactional
    public Optional<FoodInfo> save(FoodDto foodDto, User user) {

        if (!findFoodByFoodNameAndUser(foodDto.getName(), user.getId()).isPresent()) {
            boolean isGlobal = false; // foodDto.getIsGlobal().orElse(false);
            return Optional.of(foodInfoRepository
                    .save(FoodInfo.builder()
                            .food(Food.builder()
                                    .name(foodDto.getName())
                                    .nameUa(foodDto.getNameUa())
                                    .carbs(serviceUtils.toMilligrams(foodDto.getCarbs()))
                                    .protein(serviceUtils.toMilligrams(foodDto.getProtein()))
                                    .fat(serviceUtils.toMilligrams(foodDto.getFat()))
                                    .calories(foodDto.getCalories())
                                    .build())
                            .isGlobal(isGlobal)
                            .user(user)
                            .build()));

        }
        return Optional.empty();

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
