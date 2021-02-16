package soundtrack.models;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationTest {

    @Test
    void emptyLocationShouldFail() {
        Location location = new Location();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Location>> violations = validator.validate(location);

        assertEquals(2, violations.size());
    }

    @Test
    void nullLocationAddressShouldFail() {
        Location location = makeNewLocation();
        location.setAddress(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Location>> violations = validator.validate(location);

        assertEquals(2, violations.size());
    }

    @Test
    void nullLocationNameShouldPass() {
        Location location = makeNewLocation();
        location.setName(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Location>> violations = validator.validate(location);

        assertEquals(0, violations.size());
    }

    @Test
    void blankLocationAddressShouldFail() {
        Location location = makeNewLocation();
        location.setAddress("   ");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Location>> violations = validator.validate(location);

        assertEquals(1, violations.size());
    }


    Location makeNewLocation() {
        Location location = new Location();
        location.setLocationId(1);
        location.setAddress("481 Desnoyer St SE");
        location.setName("481");
        return location;
    }
}
