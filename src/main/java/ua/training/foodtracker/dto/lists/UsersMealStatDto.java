package ua.training.foodtracker.dto.lists;

import lombok.*;
import org.springframework.data.domain.Page;
import ua.training.foodtracker.dto.UserMealStatDto;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UsersMealStatDto {
    private Page<UserMealStatDto> usersFood;
}
