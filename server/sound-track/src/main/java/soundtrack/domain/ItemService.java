package soundtrack.domain;

import org.springframework.stereotype.Service;
import soundtrack.data.ItemRepository;
import soundtrack.data.LocationRepository;
import soundtrack.models.Item;
import soundtrack.models.Location;
import soundtrack.models.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final LocationRepository locationRepository;

    public ItemService(ItemRepository itemRepository, LocationRepository locationRepository) {
        this.itemRepository = itemRepository;
        this.locationRepository = locationRepository;
    }

    public List<Item> findAll() {
        List<Item> all = itemRepository.findAll();
        all.forEach(this::locate);
        return all;
    }

    public Item findById(int itemId) {
        Item item = itemRepository.findById(itemId);
        if (item == null) {
            return null;
        }
        locate(item);
        return item;
    }

    public Result<Item> addItem(Item item) {
        Result<Item> result = validate(item);
        if (!result.isSuccess()) {
            return result;
        }
        Item out = itemRepository.add(item);
        if (out == null) {
            result.addMessage("Repository failed to add item", ResultType.NOT_FOUND);
        }
        else {
            result.setPayLoad(item);
        }
        return result;
    }

    public Result<Item> updateItem(Item item) {
        Result<Item> result = validate(item);
        if (!result.isSuccess()) {
            return result;
        }
        if (itemRepository.update(item)) {
            result.setPayLoad(item);
            return result;
        }
        else {
            result.addMessage("Item not found", ResultType.NOT_FOUND);
            return result;
        }
    }

    public boolean deleteById(int itemId) {
        return itemRepository.deleteById(itemId);
    }

    private Result<Item> validate(Item item) {
        Result<Item> result = new Result<>();
        if (item == null) {
            result.addMessage("Item cannot be null!", ResultType.NOT_FOUND);
            return result;
        }
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Item>> violations = validator.validate(item);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Item> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
            return result;
        }
        List<Location> locations = locationRepository.findAll();
        if (!locations.contains(item.getLocation())) {
            result.addMessage("Item location is not one in our system", ResultType.INVALID);
        }
        return result;
    }

    private void locate(Item item) {
        item.setLocation(locationRepository.findById(item.getItemId()));
    }
}
