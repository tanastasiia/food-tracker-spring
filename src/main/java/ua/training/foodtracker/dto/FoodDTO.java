package ua.training.foodtracker.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FoodDTO {

    private String name;
    private String nameUa;
    private Integer carbs;
    private Integer protein;
    private Integer fat;
    private Integer calories;
}
