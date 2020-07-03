package ua.training.foodtracker.dto;


import lombok.*;
import ua.training.foodtracker.validation.ValidationErrorMessages;
import ua.training.foodtracker.entity.User;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserRegDto {


    @Pattern(regexp = "^[0-9A-Z]{4,25}$", message = ValidationErrorMessages.USER_USERNAME)
    private String username;

    @Size(min=5, max=100, message= ValidationErrorMessages.USER_PASSWORD)
    private String password;

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

    public UserRegDto(User user) {
        this.username = user.getUsername();
        this.height = user.getHeight();
        this.weight = user.getWeight();
        this.activityLevel = user.getActivityLevel();
        this.dateOfBirth = user.getDateOfBirth().format(DateTimeFormatter.ISO_LOCAL_DATE);
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.role = user.getRole();
        this.gender = user.getGender();
    }

}
