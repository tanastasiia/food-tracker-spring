package ua.training.foodtracker.data;

import ua.training.foodtracker.entity.Food;
import ua.training.foodtracker.entity.FoodInfo;
import ua.training.foodtracker.entity.User;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FoodInfoTestData {

    private UserTestData userTestData = new UserTestData();
    private FoodTestData foodTestData = new FoodTestData();

    public FoodInfo FOOD_INFO_GLOBAL = FoodInfo.builder()
            .user(userTestData.ADMIN_USER)
            .food(foodTestData.FOOD)
            .isGlobal(true).id(1L).build();


    public FoodInfo FOOD_INFO_NOT_GLOBAL = FoodInfo.builder()
            .user(userTestData.USER)
            .food(foodTestData.FOOD)
            .isGlobal(false).id(1L).build();


    public List<FoodInfo> FOOD_INFO_LIST = generateFoodInfoList();

    private List<FoodInfo> generateFoodInfoList() {

        List<Food> foods = foodTestData.FOOD_LIST;
        List<User> users = userTestData.USERS_LIST;

        return IntStream.range(0, Math.min(foods.size(), users.size())).boxed().map(i ->
                FoodInfo.builder()
                        .id((long) i)
                        .food(foods.get(i))
                        .user(users.get(i))
                        .isGlobal(i % 2 == 0)
                        .build())
                .collect(Collectors.toList());
    }
}
