package ua.training.foodtracker.dto;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserTodayStatisticsDto {
    private BigDecimal carbs;
    private BigDecimal protein;
    private BigDecimal fat;
    private Integer calories;
    private Integer caloriesNorm;
}
