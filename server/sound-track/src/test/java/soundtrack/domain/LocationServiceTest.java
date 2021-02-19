package soundtrack.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import soundtrack.data.LocationRepository;
import soundtrack.models.Location;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class LocationServiceTest {

    @Autowired
    LocationService service;

    @MockBean
    LocationRepository repository;

    @Test
    void shouldNotAddLocationWhenAddressIsBlank() {
        Location location = makeLocation();
        location.setAddress("   ");

        Result<Location> actual = service.add(location);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotAddLocationWhenAddressIsNull() {
        Location location = makeLocation();
        location.setAddress(null);

        Result<Location> actual = service.add(location);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotAddLocationIfLocationIdIsNotZero() {
        Location location = makeLocation();
        location.setLocationId(1);

        Result<Location> actual = service.add(location);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldAddLocation() {
        Location location = makeLocation();
        Location mockOut = makeLocation();

        when(repository.addLocation(location)).thenReturn(mockOut);

        Result<Location> actual = service.add(location);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(mockOut, actual.getPayLoad());
    }

    @Test
    void shouldNotUpdateWhenAddressIsNull() {
        Location location = makeLocation();
        Result<Location> actual = service.update(location);
        location = makeLocation();
        location.setLocationId(1);
        location.setAddress(null);
        actual = service.update(location);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotUpdateWhenAddressIsBlank() {
        Location location = makeLocation();
        Result<Location> actual = service.update(location);
        location = makeLocation();
        location.setLocationId(1);
        location.setAddress("  ");
        actual = service.update(location);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotUpdateIfLocationIdIsZeroOrLess() {
        Location location = makeLocation();
        Result<Location> actual = service.update(location);
        location = makeLocation();
        location.setLocationId(0);
        actual = service.update(location);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotUpdateIfLocationIdNotFound() {
        Location location = makeLocation();
        location = makeLocation();
        location.setLocationId(1);
        Result<Location> actual = service.update(location);
        assertEquals(ResultType.NOT_FOUND, actual.getType());
    }

    @Test
    void shouldUpdate() {
        Location location = makeLocation();
        location.setLocationId(1);

        when(repository.updateLocation(location)).thenReturn(true);

        Result<Location> actual = service.update(location);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldDelete() {
        Location location = makeLocation();
        location.setLocationId(1);

        when(repository.deleteById(location.getLocationId())).thenReturn(true);

        boolean result = service.deleteById(location.getLocationId());
        assertTrue(result);
    }

    Location makeLocation() {
        Location location = new Location();
        location.setName("DHouse");
        location.setAddress("123 Oak St.");
        return location;
    }
}
