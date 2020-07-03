package ua.training.foodtracker.validation;

import ua.training.foodtracker.dto.FoodDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameNotNullConstraintValidator implements ConstraintValidator<NameNotNull, FoodDto> {

    @Override
    public boolean isValid(FoodDto foodDto, ConstraintValidatorContext ctx) {
        if ((foodDto.getName() == null || foodDto.getName().isEmpty()) && (foodDto.getNameUa() == null || foodDto.getName().isEmpty())) {
            ctx.buildConstraintViolationWithTemplate(ValidationErrorMessages.FOOD_BOTH_NAMES_EMPTY)
                    .addPropertyNode("name")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            return false;
        }
        return true;
    }
}
