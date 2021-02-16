package soundtrack.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import soundtrack.models.Location;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class LocationJdbcRepositoryTest {

    @Autowired
    LocationJdbcRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    private List<Location> getExpected() {
        return List.of(
                new Location(1, "123 4th Street", "The Chapel"),
                new Location(2, "45 West Avenue", "The Barn"),
                new Location(3, "44 Sunset Blvd.", "Pa's House")
        );
    }

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<Location> actual = repository.findAll();
        List<Location> expected = getExpected();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindById() {
        Location expected = getExpected().get(0);
        Location actual = repository.findById(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldAdd() {
        Location location = new Location();
        location.setAddress("451 21st Avenue");
        location.setName("The Bluff House");
        assertEquals(location, repository.addLocation(location));
        assertEquals(4, repository.findAll().size());
    }

    @Test
    void shouldUpdate() {
        Location location = new Location();
        location.setLocationId(3);
        location.setAddress("451 21st Avenue");
        location.setName("The Bluff House");
        assertTrue(repository.updateLocation(location));
        assertEquals(repository.findById(3), location);
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(3));
        assertEquals(2, repository.findAll().size());
    }

}