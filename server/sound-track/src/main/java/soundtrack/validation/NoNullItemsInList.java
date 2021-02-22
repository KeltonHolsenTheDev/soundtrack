package soundtrack.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {NullItemsValidator.class}) // 3
@Target({ElementType.METHOD, ElementType.FIELD})                          // 2
@Retention(RetentionPolicy.RUNTIME)
public @interface NoNullItemsInList {
    String message() default "{null items in a list are forbidden}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}