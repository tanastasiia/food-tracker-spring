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
@EqualsAndHashCode
public class UserDto {

    private String username;
    private String firstName;
    private String lastName;
    private Integer height;
    private Integer weight;
    private String activityLevel;
    private String dateOfBirth;
    private String role;
    private String gender;

}
