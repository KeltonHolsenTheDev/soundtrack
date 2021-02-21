package soundtrack.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemTest {

    @Test
    void emptyItemShouldFail() {
        Item item = new Item();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Item>> violations = validator.validate(item);

        assertEquals(6, violations.size());
    }

    @Test
    void nullItemNameShouldFail() {
        Item item = makeNewItem();
        item.setItemName(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Item>> violations = validator.validate(item);

        assertEquals(1, violations.size());
    }

    @Test
    void emptyItemNameShouldFail() {
        Item item = makeNewItem();
        item.setItemName("   ");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Item>> violations = validator.validate(item);

        assertEquals(1, violations.size());
    }

    @Test
    void nullItemTypeShouldFail() {
        Item item = makeNewItem();
        item.setItemType(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Item>> violations = validator.validate(item);

        assertEquals(1, violations.size());
    }

    @Test
    void emptyItemTypeShouldFail() {
        Item item = makeNewItem();
        item.setItemType("   ");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Item>> violations = validator.validate(item);

        assertEquals(1, violations.size());
    }

    @Test
    void nullItemCategoryShouldFail() {
        Item item = makeNewItem();
        item.setItemCategory(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Item>> violations = validator.validate(item);

        assertEquals(1, violations.size());
    }

    @Test
    void invalidLocationIdShouldFail() {
        Item item = makeNewItem();
        item.setLocationId(0);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Item>> violations = validator.validate(item);

        assertEquals(1, violations.size());
    }

    @Test
    void nullLocationShouldFail() {
        Item item = makeNewItem();
        item.setLocation(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Item>> violations = validator.validate(item);

        assertEquals(1, violations.size());
    }

    @Test
    void nullLocationDescriptionShouldFail() {
        Item item = makeNewItem();
        item.setLocationDescription(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Item>> violations = validator.validate(item);

        assertEquals(1, violations.size());
    }

    @Test
    void emptyLocationDescriptionShouldFail() {
        Item item = makeNewItem();
        item.setLocationDescription("   ");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Item>> violations = validator.validate(item);

        assertEquals(1, violations.size());
    }


    private Item makeNewItem() {
        Item item = new Item(1, "Microphone 1", "Treble mic", "Sony",
                "microphone", ItemCategory.AUDIO, 1 ,null,"Shelf A",
                false, "no notes");
        item.setLocation(new Location(1, "123 Church Street", "The Church"));
        return item;
    }
}
