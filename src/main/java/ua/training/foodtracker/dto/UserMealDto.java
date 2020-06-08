package ua.training.foodtracker.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserMealDto {
    private String foodName;
    private Integer amount;
}
