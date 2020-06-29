package ua.training.foodtracker.dto.lists;

import lombok.*;
import ua.training.foodtracker.dto.UserDto;
import ua.training.foodtracker.entity.User;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UsersDto {
    private List<UserDto> users;

}
