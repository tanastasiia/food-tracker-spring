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
public class UserMealStatDto {
    private String foodName;
    private Integer amount;
    private LocalDate date;
    private LocalTime time;

}
