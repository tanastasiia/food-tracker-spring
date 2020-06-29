package ua.training.foodtracker.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ValidationErrorResponse {
    private String message;
    private String field;

}
