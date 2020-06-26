package ua.training.foodtracker.dto;

import lombok.*;
import ua.training.foodtracker.entity.User;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserDto {

    private String username;
    private String firstName;
    private String lastName;
    private Integer height;
    private Integer weight;
    private String activityLevel;
    private LocalDate dateOfBirth;
    private String role;
    private String gender;

    public UserDto(User user) {
        this.username = user.getUsername();
        this.height = user.getHeight();
        this.weight = user.getWeight();
        this.activityLevel = user.getActivityLevel();
        this.dateOfBirth = user.getDateOfBirth();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.role = user.getRole();
        this.gender = user.getGender();
    }
}
