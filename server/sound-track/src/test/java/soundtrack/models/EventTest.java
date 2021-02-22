package soundtrack.models;

import org.assertj.core.util.VisibleForTesting;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {

    @Test
    void emptyEventNameShouldFail() {
        Event event = makeNewEvent();
        event.setEventName("   ");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Event>> violations = validator.validate(event);

        assertEquals(1, violations.size());
    }

    @Test
    void nullEventNameShouldFail() {
        Event event = makeNewEvent();
        event.setEventName(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Event>> violations = validator.validate(event);

        assertEquals(1, violations.size());
    }

    @Test
    void invalidStartDateShouldFail() {
        Event event = makeNewEvent();
        event.setStartDate(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Event>> violations = validator.validate(event);

        assertEquals(1, violations.size());
    }

    @Test
    void startDatePastShouldFail() {
        Event event = makeNewEvent();
        event.setStartDate(LocalDate.of(2021, 2, 21));

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Event>> violations = validator.validate(event);

        assertEquals(1, violations.size());
    }

    @Test
    void startDatePresentShouldPass() {
        Event event = makeNewEvent();
        event.setStartDate(LocalDate.now());

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Event>> violations = validator.validate(event);

        assertEquals(0, violations.size());
    }

    @Test
    void startDateFutureShouldPass() {
        Event event = makeNewEvent();
        event.setStartDate(LocalDate.of(2022,8,18));

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Event>> violations = validator.validate(event);

        assertEquals(0, violations.size());
    }

    @Test
    void nullEndDateShouldFail() {
        Event event = makeNewEvent();
        event.setEndDate(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Event>> violations = validator.validate(event);

        assertEquals(1, violations.size());
    }

    @Test
    void endDatePastShouldFail() {
        Event event = makeNewEvent();
        event.setEndDate(LocalDate.of(2021, 2, 21));

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Event>> violations = validator.validate(event);

        assertEquals(1, violations.size());
    }

    @Test
    void endDatePresentShouldPass() {
        Event event = makeNewEvent();
        event.setEndDate(LocalDate.now());

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Event>> violations = validator.validate(event);

        assertEquals(0, violations.size());
    }

    @Test
    void nullUserShouldFail() {
        Event event = makeNewEvent();
        event.setOwner(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Event>> violations = validator.validate(event);

        assertEquals(1, violations.size());
    }

    @Test
    void nullStaffAndRolesShouldFail() {
        Event event = makeNewEvent();
        event.setStaffAndRoles(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Event>> violations = validator.validate(event);

        assertEquals(1, violations.size());
    }

    @Test
    void emptyStaffAndRolesShouldFail() {
        Event event = makeNewEvent();
        List<UserRole> staffAndRoles = new ArrayList<>();
        staffAndRoles.add(null);
        event.setStaffAndRoles(staffAndRoles);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Event>> violations = validator.validate(event);

        assertEquals(1, violations.size());
    }

    @Test
    void nullLocationShouldPass() {
        Event event = makeNewEvent();
        event.setLocation(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Event>> violations = validator.validate(event);

        assertEquals(1, violations.size());
    }

    @Test
    void invalidEventShouldFail() {
        Event event = new Event();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Event>> violations = validator.validate(event);

        assertEquals(6, violations.size());
    }

    @Test
    void validEventShouldPass() {
        Event event = makeNewEvent();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Event>> violations = validator.validate(event);

        assertEquals(0, violations.size());
    }

    private Event makeNewEvent() {
        Event event = new Event();
        event.setEventId(1);
        event.setEventName("Church Service");
        event.setStartDate(LocalDate.of(2022,2,21));
        event.setEndDate(LocalDate.of(2022,2,21));

        Location location = makeNewLocation();
        event.setLocationId(location.getLocationId());
        event.setLocation(location);
        Item item = makeNewItem();
        event.setEquipment(List.of(item));
        event.setEquipmentIds(List.of(1));

        User kelton = makeNewUser();

        List<UserRole> userRoles = new ArrayList<>();
        userRoles.add(new UserRole(kelton, List.of("tech")));
        kelton.setRoles(List.of("tech"));

        event.setStaffAndRoles(userRoles);
        event.setStaffIds(List.of(1));
        event.setOwnerId(1);
        event.setOwner(kelton);
        return event;
    }

    private Item makeNewItem() {
        Item item = new Item();
        item.setItemId(1);
        item.setItemName("Microphone 1");
        item.setDescription("Bass Mic");
        item.setBrand("Sony");
        item.setItemType("microphone");
        item.setItemCategory(ItemCategory.AUDIO);
        item.setLocationId(1);
        item.setLocation(makeNewLocation());
        item.setLocationDescription("shelf A");
        return item;
    }

    private Location makeNewLocation() {
        Location location = new Location();
        location.setLocationId(1);
        location.setAddress("123 Church Street");
        location.setName("The Church");
        return location;
    }

    private User makeNewUser() {
        User kelton = new User();
        kelton.setUserId(1);
        kelton.setFirstName("Kelton");
        kelton.setLastName("Holsen");
        kelton.setEmail("kholsen@gmail.com");
        kelton.setPhone("555-455-5555");
        kelton.setAccessLevel(AccessLevel.ROLE_ADMINISTRATOR);
        kelton.setPassword("swordfishfishfish");
        return kelton;
    }
}
