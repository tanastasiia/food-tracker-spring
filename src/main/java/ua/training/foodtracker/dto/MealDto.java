package ua.training.foodtracker.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MealDto {
    String username;
    String foodName;
    Integer amount;
    String date;
    LocalTime time;
}
