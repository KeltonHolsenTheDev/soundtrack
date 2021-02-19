package soundtrack.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import soundtrack.models.Event;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventJdbcRepositoryTest {

    @Autowired
    EventJdbcRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    private static List<Event> makeDefaults() {
        List<Event> defaults = List.of (
            new Event(1, "Church Service", LocalDate.of(2021, 02, 21), LocalDate.of(2021, 02, 21), 1, 1),
            new Event(2, "Rock Concert", LocalDate.of(2021, 02, 24), LocalDate.of(2021, 02, 25), 2, 2)
        );
        defaults.get(0).setEquipmentIds(List.of(1));
        defaults.get(0).setStaffIds(List.of(1));
        defaults.get(1).setEquipmentIds(List.of(2));
        defaults.get(1).setStaffIds(List.of(2));
        return defaults;
    }

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<Event> events = repository.findAll();
        assertEquals(events, makeDefaults());
    }

    @Test
    void shouldFindAnEvent() {
        Event actual = repository.findById(1);
        assertEquals(actual, makeDefaults().get(0));
    }
}