package ua.training.foodtracker.data;

import ua.training.foodtracker.entity.Food;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class FoodTestData {

    public Food FOOD = Food.builder().name("Apple").nameUa("Яблуко")
            .calories(50).carbs(3000).fat(2000).protein(500).id(1L).build();

/*
    public FoodDto FOOD_DTO = new FoodDto.Builder().setName("Apple").setNameUa("Яблуко")
            .setCalories(50).setCarbs(BigDecimal.valueOf(3)).setFat(BigDecimal.valueOf(2))
            .setProtein(BigDecimal.valueOf(0.5)).setLocale(new Locale("ua")).build();
*/

    public Food FOOD_NO_NAME = Food.builder().nameUa("Яблуко")
            .calories(50).carbs(3000).fat(2000).protein(500).id(1L).build();

    public Food FOOD_NO_NAME_UA = Food.builder().name("Apple")
            .calories(50).carbs(3000).fat(2000).protein(500).id(1L).build();

    public List<Food> FOOD_LIST = new ArrayList<>(Arrays.asList(
            Food.builder().name("Apple").nameUa("Яблуко")
                    .calories(50).carbs(3000).fat(2000).protein(500).id(1L).build(),
            Food.builder().name("Pineapple").nameUa("Ананас")
                    .calories(100).carbs(2000).fat(2000).protein(800).id(2L).build(),
            Food.builder().name("Orange").nameUa("Апельсин")
                    .calories(60).carbs(4000).fat(2000).protein(1000).id(3L).build(),
            Food.builder().name("Pasta").nameUa("Паста")
                    .calories(330).carbs(31000).fat(2000).protein(1200).id(4L).build(),
            Food.builder().name("French fries").nameUa("Картопля фрі")
                    .calories(450).carbs(44000).fat(42000).protein(5500).id(5L).build()
    ));
}
