package ua.training.foodtracker.config.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NameNotNullConstraintValidator.class)
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public  @interface NameNotNull {

    String message() default ValidationErrorMessages.FOOD_BOTH_NAMES_EMPTY;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
