package ua.training.foodtracker.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserMealDTO {
    String foodName;
    Integer amount;
}
