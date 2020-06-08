package ua.training.foodtracker.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FoodInfoDto {
    private FoodDto foodDto;
    private String username;
    private boolean isGlobal;
}
