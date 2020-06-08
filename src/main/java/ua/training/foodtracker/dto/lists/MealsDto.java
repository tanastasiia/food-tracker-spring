package ua.training.foodtracker.dto.lists;

import lombok.*;
import org.springframework.data.domain.Page;
import ua.training.foodtracker.dto.MealDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MealsDto {
    private Page<MealDto> meals;
}