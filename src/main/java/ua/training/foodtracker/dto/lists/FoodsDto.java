package ua.training.foodtracker.dto.lists;

import lombok.*;
import org.springframework.data.domain.Page;
import ua.training.foodtracker.entity.Food;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FoodsDto {
    private Page<Food> foods;
}
