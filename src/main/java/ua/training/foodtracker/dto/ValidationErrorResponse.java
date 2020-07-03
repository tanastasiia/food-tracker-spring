package ua.training.foodtracker.dto;

import lombok.*;

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
