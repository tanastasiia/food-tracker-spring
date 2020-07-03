package ua.training.foodtracker.dto;


import lombok.*;
import ua.training.foodtracker.validation.ValidationErrorMessages;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserMealDto {

    @Size(min = 2, max = 45, message = ValidationErrorMessages.NAMES)
    private String foodName;

    @Max(value = Integer.MAX_VALUE, message = ValidationErrorMessages.INTEGER_MAX)
    @Min(value = 1, message = ValidationErrorMessages.POSITIVE_NUMBER)
    private Integer amount;
}
