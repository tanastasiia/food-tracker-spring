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
public class UserMealStatDTO {
    String foodName;
    Integer amount;
    LocalDate date;
    LocalTime time;

}
