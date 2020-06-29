package ua.training.foodtracker.dto;

import lombok.*;
import ua.training.foodtracker.config.ValidationErrorMessages;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class UserDto {

    @Pattern(regexp = "^[0-9A-Za-z]{4,25}$", message = ValidationErrorMessages.USER_USERNAME)
    private String username;

    @Size(min = 2, max = 45, message = ValidationErrorMessages.NAMES)
    private String firstName;

    @Size(min = 2, max = 45, message = ValidationErrorMessages.NAMES)
    private String lastName;

    @Min(value = 1, message = ValidationErrorMessages.POSITIVE_NUMBER)
    @Max(value = Integer.MAX_VALUE, message = ValidationErrorMessages.INTEGER_MAX)
    private Integer height;

    @Min(value = 1, message = ValidationErrorMessages.POSITIVE_NUMBER)
    @Max(value = Integer.MAX_VALUE, message = ValidationErrorMessages.INTEGER_MAX)
    private Integer weight;

    private String activityLevel;
    private String dateOfBirth;
    private String role;
    private String gender;

}
