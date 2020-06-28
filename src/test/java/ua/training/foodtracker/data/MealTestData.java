package ua.training.foodtracker.data;

import ua.training.foodtracker.entity.Meal;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MealTestData {

    private UserTestData userTestData = new UserTestData();
    private FoodTestData foodTestData = new FoodTestData();
    private BigDecimal HUNDRED = BigDecimal.valueOf(100);

    public Meal MEAL = Meal.builder().id(1L).amount(200)
            .dateTime(LocalDateTime.of(2020, 5, 23, 12, 42, 45))
            .user(userTestData.USER)
            .food(foodTestData.FOOD)
            .build();


    public int TODAYS_CALORIES = 50 * 2 + 100 * 340 / 100 + 60 * 4 + 330 * 250 / 100 + 450 * 310 / 100;
    public BigDecimal TODAYS_PROTEIN = BigDecimal.valueOf(500 * 200).divide(HUNDRED, 1)
            .add(BigDecimal.valueOf(800 * 340).divide(HUNDRED, 1))
            .add(BigDecimal.valueOf(1000 * 400).divide(HUNDRED, 1))
            .add(BigDecimal.valueOf(1200 * 250).divide(HUNDRED, 1))
            .add(BigDecimal.valueOf(5500 * 310).divide(HUNDRED, 1))
            .divide(BigDecimal.valueOf(1000), 1, RoundingMode.HALF_UP);


    public List<Meal> TODAYS_MEALS_LIST = new ArrayList<>(Arrays.asList(
            Meal.builder()
                    .id(1L)
                    .amount(200)
                    .dateTime(LocalDateTime.now())
                    .food(foodTestData.FOOD_LIST.get(0))
                    .user(userTestData.USERS_LIST.get(0))
                    .build(),
            Meal.builder()
                    .id(2L)
                    .amount(340)
                    .dateTime(LocalDateTime.now())
                    .food(foodTestData.FOOD_LIST.get(1))
                    .user(userTestData.USERS_LIST.get(1))
                    .build(),
            Meal.builder()
                    .id(3L)
                    .amount(400)
                    .dateTime(LocalDateTime.now())
                    .food(foodTestData.FOOD_LIST.get(2))
                    .user(userTestData.USERS_LIST.get(2))
                    .build(),
            Meal.builder()
                    .id(4L)
                    .amount(250)
                    .dateTime(LocalDateTime.now())
                    .food(foodTestData.FOOD_LIST.get(3))
                    .user(userTestData.USERS_LIST.get(3))
                    .build(),
            Meal.builder()
                    .id(5L)
                    .amount(310)
                    .dateTime(LocalDateTime.now())
                    .food(foodTestData.FOOD_LIST.get(4))
                    .user(userTestData.USERS_LIST.get(4))
                    .build()

    ));
}
