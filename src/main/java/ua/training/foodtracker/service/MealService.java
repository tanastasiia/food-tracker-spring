package ua.training.foodtracker.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.training.foodtracker.config.Utils;
import ua.training.foodtracker.dto.MealDto;
import ua.training.foodtracker.dto.UserMealStatDto;
import ua.training.foodtracker.dto.UserTodayStatisticsDto;
import ua.training.foodtracker.dto.lists.MealsDto;
import ua.training.foodtracker.dto.lists.UsersMealStatDto;
import ua.training.foodtracker.entity.*;
import ua.training.foodtracker.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;

@Slf4j
@Service
public class MealService {

    private MealRepository mealRepository;

    @PersistenceContext
    EntityManager entityManager;

    public MealService( MealRepository mealRepository){
        this.mealRepository = mealRepository;
    }


    private final BigDecimal HUNDRED = new BigDecimal(100);
    private final BigDecimal THOUSAND = new BigDecimal(1000);

    public Meal save(Food food, Integer amount) {

        return mealRepository.save(Meal.builder()
                .user(entityManager.getReference(User.class, Utils.getPrincipalId()))
                .food(food)
                .amount(amount)
                .dateTime(LocalDateTime.now())
                .build());
    }

    public MealsDto findAllMeals(Pageable pageable) {

        return MealsDto.builder().meals(mealRepository.findAll(pageable).map(meal -> MealDto.builder()
                .username(meal.getUser().getUsername())
                .foodName(Utils.isLocaleUa() ? meal.getFood().getNameUa() : meal.getFood().getName())
                .amount(meal.getAmount())
                .date(meal.getDateTime().toLocalDate())
                .time(meal.getDateTime().toLocalTime()).build()))
                .build();
    }

    public UsersMealStatDto findAllTodaysMeals(Pageable pageable, Long userId) {
        return UsersMealStatDto.builder()
                .usersFood(mealRepository
                        .findByUser_IdAndDateTimeBetween(userId,
                                LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
                                LocalDateTime.of(LocalDate.now(), LocalTime.MAX),
                                pageable).map(meal -> UserMealStatDto.builder()
                                .amount(meal.getAmount())
                                .date(meal.getDateTime().toLocalDate())
                                .time(meal.getDateTime().toLocalTime())
                                .foodName(ServiceUtils.getLocalizedName(meal.getFood()))
                                .build()))
                .build();

    }

    public UsersMealStatDto findAllPrincipalStat(Pageable pageable, Long userId) {

        return UsersMealStatDto.builder()
                .usersFood(mealRepository
                        .findByUser_Id(userId, pageable)
                        .map(meal -> UserMealStatDto.builder()
                                .amount(meal.getAmount())
                                .date(meal.getDateTime().toLocalDate())
                                .time(meal.getDateTime().toLocalTime())
                                .foodName(ServiceUtils.getLocalizedName(meal.getFood()))
                                .build()))
                .build();
    }

    public UserTodayStatisticsDto getTodaysPrincipalStatistics(Long userId)  {
        List<Meal> meals = mealRepository
                .findByUser_IdAndDateTimeBetween(userId,
                        LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
                        LocalDateTime.of(LocalDate.now(), LocalTime.MAX));

        return UserTodayStatisticsDto.builder()
                .calories(sumFoodCalories(meals))
                .carbs(sumFoodElements(meals, Food::getCarbs))
                .protein(sumFoodElements(meals, Food::getProtein))
                .fat(sumFoodElements(meals, Food::getFat))
                .caloriesNorm(countCaloriesNorm())
                .build();
    }


    public int todaysCalories() {
        return mealRepository
                .findByUser_IdAndDateTimeBetween(Utils.getPrincipalId(),
                        LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
                        LocalDateTime.of(LocalDate.now(), LocalTime.MAX))
                .stream()
                .mapToInt(meal -> meal.getFood().getCalories() * meal.getAmount() / 100)
                .reduce(Integer::sum).orElse(0);
    }

    private Integer sumFoodCalories(List<Meal> meals) {
        return meals.stream()
                .mapToInt(meal -> meal.getFood().getCalories() * meal.getAmount() / 100)
                .reduce(Integer::sum)
                .orElse(0);
    }

    public int countCaloriesNorm() {
        User user = entityManager.getReference(User.class, Utils.getPrincipalId());

        return (int) (ActivityLevel.valueOf(user.getActivityLevel()).getValue()
                * (Gender.valueOf(user.getGender()).getValue()
                + 10 * user.getWeight()
                + 6.25 * user.getHeight()
                - 5 * user.getAge()));
    }

    private BigDecimal sumFoodElements(List<Meal> meals, Function<Food, Integer> getter) {
        return meals.stream()
                .map(meal -> new BigDecimal(getter.apply(meal.getFood()) * meal.getAmount()).divide(HUNDRED, 1))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO).divide(THOUSAND, 1, RoundingMode.HALF_UP);
    }


}
