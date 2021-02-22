package soundtrack.models;

import org.assertj.core.util.VisibleForTesting;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {

    @Test
    void emptyEventNameShouldFail() {
        Event event = new Event();
        event.setEventName("   ");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Event>> violations = validator.validate(event);

        assertEquals(1, violations.size());
    }

    @Test
    void nullEventNameShouldFail() {
        Event event = new Event();
        event.setEventName(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Event>> violations = validator.validate(event);

        assertEquals(6, violations.size());
    }

    @Test
    void emptyStartDateShouldFail() {
        Event event = new Event();
        event.setEventName("   ");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Event>> violations = validator.validate(event);

        assertEquals(1, violations.size());
    }
}
