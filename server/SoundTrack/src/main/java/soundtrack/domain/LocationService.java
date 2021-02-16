package soundtrack.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soundtrack.data.LocationRepository;
import soundtrack.models.Location;

import javax.validation.*;
import java.util.List;
import java.util.Set;

@Service
public class LocationService {

    private final LocationRepository repository;

    public LocationService(LocationRepository repository) {this.repository=repository; }

    public List<Location> findAll() {return repository.findAll(); }

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

        if (!repository.updateLocation(location)) {
            String msg = String.format("locationId: %s, not found", location.getLocationId());
        }

        return result;
    }

    public boolean deleteById(int locationId) {
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
