package ua.training.foodtracker.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserTodayStatisticsDTO {
    private Integer carbs;
    private Integer protein;
    private Integer fat;
    private Integer calories;
    private Integer caloriesNorm;
}
