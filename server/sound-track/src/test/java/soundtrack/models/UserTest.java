package soundtrack.models;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    @Test
    void emptyUserShouldFail() {
        User user = new User();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(6, violations.size());
    }

    @Test
    void nullFirstNameShouldFail() {
        User user = makeNewUser();
        user.setFirstName(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
    }

    @Test
    void emptyFirstNameShouldFail() {
        User user = makeNewUser();
        user.setFirstName("   ");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
    }

    @Test
    void nullLastNameShouldFail() {
        User user = makeNewUser();
        user.setLastName(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
    }

    @Test
    void emptyLastNameShouldFail() {
        User user = makeNewUser();
        user.setLastName("   ");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
    }

    @Test
    void nullEmailShouldFail() {
        User user = makeNewUser();
        user.setEmail(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
    }

    @Test
    void emptyEmailShouldFail() {
        User user = makeNewUser();
        user.setEmail("   ");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(2, violations.size());
    }

    @Test
    void invalidEmailShouldFail() {
        User user = makeNewUser();
        user.setEmail("this is definitely wrong");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
    }

    @Test
    void validEmailShouldPass() {
        User user = makeNewUser();
        user.setEmail("thisIsDefinitelyRight@gmail.com");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(0, violations.size());
    }

    @Test
    void invalidPhonePatternWithTooManyNumbersShouldFail() {
        User user = makeNewUser();
        user.setPhone("94209348396");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
    }

    @Test
    void invalidPhonePatternWithTooLittleNumbersShouldFail() {
        User user = makeNewUser();
        user.setPhone("942093483");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
    }

    @Test
    void nullAccessLevelShouldFail() {
        User user = makeNewUser();
        user.setAccessLevel(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
    }

    @Test
    void nullPasswordShouldFail() {
        User user = makeNewUser();
        user.setPassword(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
    }

    @Test
    void emptyPasswordShouldFail() {
        User user = makeNewUser();
        user.setPassword("                       ");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
    }

    @Test
    void shortPasswordShouldFail() {
        User user = makeNewUser();
        user.setPassword("jjjjjjjjjjjjjjj");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
    }

    @Test
    void validPassWordShouldPass() {
        User user = makeNewUser();
        user.setPassword("jjjjjjjjjjjjjjjjj");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(0, violations.size());
    }

    @Test
    void nullRolesShouldFail() {
        User user = makeNewUser();
        user.setRoles(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
    }

    @Test
    void validUserShouldPass() {
        User user = makeNewUser();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(0, violations.size());
    }

    private User makeNewUser() {
        User kelton = new User("Kelton", "Holsen", "kholsen@gmail.com",
                "555-455-5555", AccessLevel.ROLE_ADMINISTRATOR, "swordfishfishfish");
        kelton.setRoles(List.of("The Boss"));
        return kelton;
    }
}
