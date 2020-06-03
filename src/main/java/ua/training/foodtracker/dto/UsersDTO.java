package ua.training.foodtracker.dto;

import lombok.*;
import ua.training.foodtracker.entity.User;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UsersDTO {
    private List<User> users;

}
