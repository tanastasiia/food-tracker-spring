package ua.training.foodtracker.dto.lists;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FoodNamesDto {
    private List<String> foodNames;
}
