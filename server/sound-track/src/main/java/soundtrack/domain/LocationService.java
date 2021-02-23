package soundtrack.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soundtrack.data.EventRepository;
import soundtrack.data.ItemRepository;
import soundtrack.data.LocationRepository;
import soundtrack.models.Location;

import javax.validation.*;
import java.util.List;
import java.util.Set;

@Service
public class LocationService {

    private final LocationRepository repository;

    private final EventRepository eventRepository;

    private final ItemRepository itemRepository;

    public LocationService(LocationRepository repository, EventRepository eventRepository, ItemRepository itemRepository) {
        this.repository = repository;
        this.eventRepository = eventRepository;
        this.itemRepository = itemRepository;
    }

    public List<Location> findAll() {return repository.findAll(); }

    public Location findById(int locationId) {return repository.findById(locationId);}

    public Result<Location> add(Location location) {
        Result<Location> result = validate(location);
        if (!result.isSuccess()) {
            return result;
        }

        if (location.getLocationId() != 0) {
            result.addMessage("locationId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        location = repository.addLocation(location);
        result.setPayLoad(location);
        return result;
    }

    public Result<Location> update(Location location) {
        Result<Location> result = validate(location);
        if (!result.isSuccess()) {
            return result;
        }

        if (location.getLocationId() <=0) {
            result.addMessage("locationId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        else if (!repository.updateLocation(location)) {
            result.addMessage(String.format("locationId: %s, not found", location.getLocationId()), ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int locationId) {
        itemRepository.deleteByLocation(locationId);
        eventRepository.deleteByLocation(locationId);
        return repository.deleteById(locationId);
    }

    private Result<Location> validate(Location location) {
        Result<Location> result = new Result<>();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();


        if (result.isSuccess()) {
            Set<ConstraintViolation<Location>> violations = validator.validate(location);

            if (!violations.isEmpty()) {
                for (ConstraintViolation<Location> violation : violations) {
                    result.addMessage(violation.getMessage(), ResultType.INVALID);
                }
            }
        }
        return result;
    }
}
