package ua.training.foodtracker.dto;

import lombok.*;
import ua.training.foodtracker.entity.User;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserDTO {

    private String username;
    private String password;
    private String firstName;
    private Integer height;
    private Integer weight;
    private String activityLevel;
    private Integer age;
    private String firstNameUa;
    private String gender;

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.height = user.getHeight();
        this.weight = user.getWeight();
        this.activityLevel = user.getActivityLevel();
        this.age = user.getAge();
        this.firstName = user.getFirstName();
        this.firstNameUa = user.getFirstNameUa();
        this.gender = user.getGender();
    }


}
