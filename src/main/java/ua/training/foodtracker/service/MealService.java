package ua.training.foodtracker.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.training.foodtracker.config.Utils;
import ua.training.foodtracker.dto.CaloriesChartData;
import ua.training.foodtracker.dto.MealDto;
import ua.training.foodtracker.dto.UserMealStatDto;
import ua.training.foodtracker.dto.UserTodayStatisticsDto;
import ua.training.foodtracker.dto.lists.MealsDto;
import ua.training.foodtracker.dto.lists.UsersMealStatDto;
import ua.training.foodtracker.entity.*;
import ua.training.foodtracker.repository.MealRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service for {@link Meal} entity
 */
@Slf4j
@Service
public class MealService {

    private MealRepository mealRepository;
    private ServiceUtils serviceUtils;

    public MealService(MealRepository mealRepository, ServiceUtils serviceUtils) {
        this.mealRepository = mealRepository;
        this.serviceUtils = serviceUtils;
    }


    private final BigDecimal HUNDRED = new BigDecimal(100);
    private final BigDecimal THOUSAND = new BigDecimal(1000);

    /**
     * Save meal to database
     *
     * @return saved meal
     */
    public Meal save(Food food, Integer amount, User user) {

        return mealRepository.save(Meal.builder()
                .user(user)
                .food(food)
                .amount(amount)
                .dateTime(LocalDateTime.now())
                .build());
    }

    /**
     * Find all meals
     *
     * @return page of meals
     */
    public MealsDto findAllMeals(Pageable pageable) {

        return MealsDto.builder().meals(mealRepository.findAll(pageable).map(meal -> MealDto.builder()
                .username(meal.getUser().getUsername())
                .foodName(serviceUtils.isLocaleUa() ? meal.getFood().getNameUa() : meal.getFood().getName())
                .amount(meal.getAmount())
                .date(meal.getDateTime().toLocalDate())
                .time(meal.getDateTime().toLocalTime()).build()))
                .build();
    }

    /**
     * Find all today's meals for user
     *
     * @return today's user's meals page
     */
    public UsersMealStatDto findAllTodaysMeals(Pageable pageable, Long userId) {
        return UsersMealStatDto.builder()
                .usersFood(mealRepository
                        .findByUser_IdAndDateTimeBetween(userId,
                                LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
                                LocalDateTime.of(LocalDate.now(), LocalTime.MAX),
                                pageable)
                        .map(meal -> UserMealStatDto.builder()
                                .amount(meal.getAmount())
                                .date(meal.getDateTime().toLocalDate())
                                .time(meal.getDateTime().toLocalTime())
                                .foodName(serviceUtils.getLocalizedFoodName(meal.getFood()))
                                .build()))
                .build();

    }


    /**
     * Find all meals for user
     *
     * @return user's meals page
     */
    public UsersMealStatDto findAllPrincipalStat(Pageable pageable, Long userId) {

        return UsersMealStatDto.builder()
                .usersFood(mealRepository
                        .findByUser_Id(userId, pageable)
                        .map(meal -> UserMealStatDto.builder()
                                .amount(meal.getAmount())
                                .date(meal.getDateTime().toLocalDate())
                                .time(meal.getDateTime().toLocalTime())
                                .foodName(serviceUtils.getLocalizedFoodName(meal.getFood()))
                                .build()))
                .build();
    }

    /**
     * Counts calories norm and consumed today's calories, carbs, protein, fat for user
     */
    public UserTodayStatisticsDto getTodaysPrincipalStatistics(User user) {
        List<Meal> meals = mealRepository
                .findByUser_IdAndDateTimeBetween(user.getId(),
                        LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
                        LocalDateTime.of(LocalDate.now(), LocalTime.MAX));

        return UserTodayStatisticsDto.builder()
                .calories(sumFoodCalories(meals))
                .carbs(sumFoodElements(meals, Food::getCarbs))
                .protein(sumFoodElements(meals, Food::getProtein))
                .fat(sumFoodElements(meals, Food::getFat))
                .caloriesNorm(serviceUtils.countCaloriesNorm(user))
                .build();
    }

    /**
     * Counts consumed today's calories
     */
    public Integer todaysCalories(Long userId) {
        return mealRepository
                .findByUser_IdAndDateTimeBetween(userId,
                        LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
                        LocalDateTime.of(LocalDate.now(), LocalTime.MAX))
                .stream()
                .mapToInt(meal -> meal.getFood().getCalories() * meal.getAmount() / 100)
                .reduce(Integer::sum)
                .orElse(0);
    }

    /**
     * Counts calories in meals list
     *
     * @param meals
     * @return sum of meals calories
     */
    private Integer countCalories(List<Meal> meals) {
        return meals.stream()
                .mapToInt(meal -> meal.getFood().getCalories() * meal.getAmount() / 100)
                .reduce(Integer::sum)
                .orElse(0);
    }

    /**
     * Make bar chart of calories comsumed by days
     *
     * @param user
     * @param days on x-axis
     * @return sum of calories by dates and dates
     */
    public CaloriesChartData getCaloriesChartData(User user, int days) {

        Map<LocalDate, List<Meal>> mealsByDates = mealRepository
                .findByUser_IdAndDateTimeBetween(user.getId(),
                        LocalDateTime.of(LocalDate.now().minusDays(days - 1), LocalTime.MIN),
                        LocalDateTime.of(LocalDate.now(), LocalTime.MAX))
                .stream()
                .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate(), Collectors.toList()));

        List<LocalDate> dates = Stream
                .iterate(LocalDate.now().minusDays(days - 1), date -> date.plusDays(1))
                .limit(days)
                .collect(Collectors.toList());

        List<Integer> calories = dates.stream()
                .map(date -> mealsByDates.containsKey(date) ? countCalories(mealsByDates.get(date)) : 0)
                .collect(Collectors.toList());

        return CaloriesChartData.builder()
                .data(calories)
                .labels(dates.stream().map(date -> date.format(ServiceUtils.dayMonthDateFormat)).collect(Collectors.toList()))
                .caloriesNorm(serviceUtils.countCaloriesNorm(user))
                .build();
    }

    /**
     * Summing calories from meals
     */
    private Integer sumFoodCalories(List<Meal> meals) {
        return meals.stream()
                .mapToInt(meal -> meal.getFood().getCalories() * meal.getAmount() / 100)
                .reduce(Integer::sum)
                .orElse(0);
    }

    /**
     * Summing carbs, fat or protein from meals
     *
     * @param meals  meals to sum from
     * @param getter food getter (for carbs, fat or protein)
     * @return food element sum in grams
     */
    private BigDecimal sumFoodElements(List<Meal> meals, Function<Food, Integer> getter) {
        return meals.stream()
                .map(meal -> new BigDecimal(getter.apply(meal.getFood()) * meal.getAmount()).divide(HUNDRED, 1))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO).divide(THOUSAND, 1, RoundingMode.HALF_UP);
    }


}
