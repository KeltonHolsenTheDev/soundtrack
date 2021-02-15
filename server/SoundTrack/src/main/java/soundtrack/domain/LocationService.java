package soundtrack.domain;

import org.springframework.beans.factory.annotation.Autowired;
import soundtrack.data.LocationRepository;
import soundtrack.models.Location;

import java.util.List;

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

        location = repository.add(location);
        result.setPayLoad(location);
        return result;
    }

    public Result<Location> 
}
