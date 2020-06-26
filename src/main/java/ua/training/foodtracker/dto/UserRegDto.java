package ua.training.foodtracker.dto;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserRegDto {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Integer height;
    private Integer weight;
    private String activityLevel;/*
    private Integer age;*/
    private LocalDate dateOfBirth;
    private String role;
    private String gender;

}
