package ua.training.foodtracker.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import ua.training.foodtracker.validation.NameNotNull;
import ua.training.foodtracker.validation.ValidationErrorMessages;

import javax.validation.constraints.*;
import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Slf4j

@NameNotNull
public class FoodDto {

    private Long id;
    @Size(max = 45, message = ValidationErrorMessages.NAMES)
    private String name;

    @Size(max = 45, message = ValidationErrorMessages.NAMES)
    private String nameUa;

    private BigDecimal carbs;
    private BigDecimal protein;
    private BigDecimal fat;

    @Min(value = 0, message = ValidationErrorMessages.POSITIVE_NUMBER)
    @Max(value = Integer.MAX_VALUE, message = ValidationErrorMessages.INTEGER_MAX)
    private Integer calories;

    private Boolean isGlobal;

}
