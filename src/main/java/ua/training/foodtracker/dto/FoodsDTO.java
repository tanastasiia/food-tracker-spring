package ua.training.foodtracker.dto;

import lombok.*;
import org.springframework.data.domain.Page;
import ua.training.foodtracker.entity.Food;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FoodsDTO {
    private Page<Food> foods;
}
