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

@Slf4j
@Service
public class FoodInfoService {

    private FoodInfoRepository foodInfoRepository;

    @PersistenceContext
    EntityManager entityManager;

    public FoodInfoService(FoodInfoRepository foodInfoRepository) {
        this.foodInfoRepository = foodInfoRepository;
    }

    public Optional<FoodInfo> findFoodByFoodNameAndUser(String foodName, Long userId) {

        return foodInfoRepository.findAllByFoodNameOrFoodNameUaAndUserIdOrGlobal(foodName, userId);

    }

    @Transactional
    public Optional<FoodInfo> save(FoodDto foodDto, Long userId) {

        if (!findFoodByFoodNameAndUser(foodDto.getName(), userId).isPresent()) {
            boolean isGlobal = foodDto.getIsGlobal().orElse(false);
            return Optional.of(foodInfoRepository
                    .save(FoodInfo.builder()
                            .food(Food.builder()
                                    .name(foodDto.getName())
                                    .nameUa(foodDto.getNameUa())
                                    .carbs(ServiceUtils.toMilligrams(foodDto.getCarbs()))
                                    .protein(ServiceUtils.toMilligrams(foodDto.getProtein()))
                                    .fat(ServiceUtils.toMilligrams(foodDto.getFat()))
                                    .calories(foodDto.getCalories())
                                    .build())
                            .isGlobal(isGlobal)
                            .user(entityManager.getReference(User.class, userId))
                            .build()));

        }
        return Optional.empty();

    }

    public FoodInfosDto findAll(Pageable pageable) {
        return FoodInfosDto.builder()
                .foodInfos(foodInfoRepository
                        .findAll(pageable)
                        .map(foodInfo -> FoodInfoDto.builder()
                                .isGlobal(foodInfo.getIsGlobal())
                                .username(foodInfo.getUser() == null ? null : foodInfo.getUser().getUsername())
                                .foodDto(new FoodDto(foodInfo.getFood()))
                                .build()))
                .build();


    }

    public FoodNamesDto foodNamesList(Long userId) {
        return FoodNamesDto.builder()
                .foodNames(foodInfoRepository.findAllByUser_IdOrIsGlobalTrue(userId).stream()
                        .map(foodInfo -> ServiceUtils.getLocalizedName(foodInfo.getFood()))
                        .filter(str -> !str.isEmpty())
                        .collect(Collectors.toList()))
                .build();
    }


}
