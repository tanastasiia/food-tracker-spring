package ua.training.foodtracker.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PasswordChangeDto {
    private String oldPassword;
    private String newPassword;
}
