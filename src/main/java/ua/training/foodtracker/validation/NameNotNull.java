package ua.training.foodtracker.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * At least one of name or nameUa not empty in food
 */
@Documented
@Constraint(validatedBy = NameNotNullConstraintValidator.class)
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public  @interface NameNotNull {

    String message() default ValidationErrorMessages.FOOD_BOTH_NAMES_EMPTY;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
