package soundtrack.data;

import soundtrack.models.Location;

import java.util.List;

public interface LocationRepository {
    List<Location> findAll();
    Location findById(int id);
    Location addLocation(Location location);
    boolean updateLocation(Location location);
    boolean deleteById(int id);
}
