package ua.training.foodtracker.dto.lists;

import lombok.*;
import org.springframework.data.domain.Page;
import ua.training.foodtracker.dto.FoodInfoDto;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FoodInfosDto {
    Page<FoodInfoDto> foodInfos;
}
