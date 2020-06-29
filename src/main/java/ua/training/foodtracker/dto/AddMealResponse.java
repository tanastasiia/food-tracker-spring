package ua.training.foodtracker.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AddMealResponse {
    private int calories;
    private String message;
}
