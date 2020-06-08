package ua.training.foodtracker.dto;

import lombok.*;
import ua.training.foodtracker.entity.User;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserDto {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Integer height;
    private Integer weight;
    private String activityLevel;
    private Integer age;
    private String gender;

    public UserDto(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.height = user.getHeight();
        this.weight = user.getWeight();
        this.activityLevel = user.getActivityLevel();
        this.age = user.getAge();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.gender = user.getGender();
    }


}
