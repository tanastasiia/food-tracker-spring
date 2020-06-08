package ua.training.foodtracker.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import ua.training.foodtracker.entity.Food;
import ua.training.foodtracker.service.ServiceUtils;

import java.math.BigDecimal;
import java.util.Optional;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Slf4j
public class FoodDto {

    private String name;
    private String nameUa;
    private BigDecimal carbs;
    private BigDecimal protein;
    private BigDecimal fat;
    private Integer calories;
    private Optional<Boolean> isGlobal;


    public FoodDto(Food food) {
        this.name = food.getName();
        this.nameUa = food.getNameUa();
        this.carbs = ServiceUtils.toGrams(food.getCarbs());
        this.protein = ServiceUtils.toGrams(food.getProtein());
        this.fat = ServiceUtils.toGrams(food.getFat());
        this.calories = food.getCalories();
    }


}
