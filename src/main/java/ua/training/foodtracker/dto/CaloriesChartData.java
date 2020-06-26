package ua.training.foodtracker.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CaloriesChartData {

    private List<Integer> data;
    private List<String> labels;
    private Integer caloriesNorm;
}
