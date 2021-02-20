package soundtrack.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import soundtrack.data.EventRepository;
import soundtrack.data.ItemRepository;
import soundtrack.data.LocationRepository;
import soundtrack.data.UserRepository;
import soundtrack.models.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventServiceTest {

    @Autowired
    EventService service;

    @MockBean
    EventRepository eventRepository;

    @MockBean
    ItemRepository itemRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    LocationRepository locationRepository;

    private static Event makeEvent() {
        Event event = new Event(1, "Church Service", LocalDate.of(2021, 02, 21), LocalDate.of(2021, 02, 21), 1, 1);
        event.setLocationId(1);
        event.setLocation(new Location(1, "123 Church Street", "The Church"));
        event.setEquipment(List.of(ItemServiceTest.makeNewItem()));
        event.setEquipmentIds(List.of(1));
        User kelton = new User("Kelton", "Holsen", "kholsen@gmail.com", "555-455-5555", AccessLevel.ROLE_ADMINISTRATOR, "swordfishfishfish");
        Map<User, List<String>> userRoles = new HashMap<>();
        userRoles.put(kelton, List.of("tech"));
        event.setStaffAndRoles(userRoles);
        event.setOwnerId(1);
        event.setOwner(kelton);
        return event;
    }

    @Test
    void shouldAddValidEvent() {
        Event toAdd = makeEvent();
        when(eventRepository.addEvent(toAdd)).thenReturn(toAdd);
        when(locationRepository.findAll()).thenReturn(List.of(new Location(1, "123 Church Street", "The Church")));
        when(eventRepository.findAll()).thenReturn(new ArrayList<>());
        when(userRepository.findById(1)).thenReturn(new User("Kelton", "Holsen", "kholsen@gmail.com", "555-455-5555", AccessLevel.ROLE_ADMINISTRATOR, "swordfishfishfish"));

        Result<Event> result = service.addEvent(toAdd);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotAddEventWithOverlap() {
        Event toAdd = makeEvent();
        when(eventRepository.addEvent(toAdd)).thenReturn(toAdd);
        when(locationRepository.findAll()).thenReturn(List.of(new Location(1, "123 Church Street", "The Church")));
        when(eventRepository.findAll()).thenReturn(List.of(makeEvent()));
        when(userRepository.findById(1)).thenReturn(new User("Kelton", "Holsen", "kholsen@gmail.com", "555-455-5555", AccessLevel.ROLE_ADMINISTRATOR, "swordfishfishfish"));
        toAdd.setEventId(2);

        Result<Event> result = service.addEvent(toAdd);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddEventWithOverlapUser() {
        Event toAdd = makeEvent();
        when(eventRepository.addEvent(toAdd)).thenReturn(toAdd);
        when(locationRepository.findAll()).thenReturn(List.of(new Location(1, "123 Church Street", "The Church")));
        when(eventRepository.findAll()).thenReturn(List.of(makeEvent()));
        when(userRepository.findById(1)).thenReturn(new User("Kelton", "Holsen", "kholsen@gmail.com", "555-455-5555", AccessLevel.ROLE_ADMINISTRATOR, "swordfishfishfish"));
        toAdd.setEventId(2);
        toAdd.setLocation(new Location(2, "The Barn", "124 Barn Bluff"));
        Item differentItem = ItemServiceTest.makeNewItem();
        differentItem.setItemId(2);
        toAdd.setEquipment(List.of(differentItem));

        Result<Event> result = service.addEvent(toAdd);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddEventWithOverlapLocation() {
        Event toAdd = makeEvent();
        when(eventRepository.addEvent(toAdd)).thenReturn(toAdd);
        when(locationRepository.findAll()).thenReturn(List.of(new Location(1, "123 Church Street", "The Church")));
        when(eventRepository.findAll()).thenReturn(List.of(makeEvent()));
        when(userRepository.findById(1)).thenReturn(new User(1, "Kelton", "Holsen", "kholsen@gmail.com", "555-455-5555", AccessLevel.ROLE_ADMINISTRATOR, "swordfishfishfish"));
        toAdd.setEventId(2);

        Item differentItem = ItemServiceTest.makeNewItem();
        differentItem.setItemId(2);
        toAdd.setEquipment(List.of(differentItem));

        User notKelton = new User("Kelton", "Holsen", "kholsen@gmail.com", "555-455-5555", AccessLevel.ROLE_ADMINISTRATOR, "swordfishfishfish");
        notKelton.setUserId(3);
        Map<User, List<String>> userRoles = new HashMap<>();
        userRoles.put(notKelton, List.of("tech"));
        toAdd.setStaffAndRoles(userRoles);

        Result<Event> result = service.addEvent(toAdd);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddEventWithOverlapItem() {
        Event toAdd = makeEvent();
        when(eventRepository.addEvent(toAdd)).thenReturn(toAdd);
        when(locationRepository.findAll()).thenReturn(List.of(new Location(1, "123 Church Street", "The Church")));
        when(eventRepository.findAll()).thenReturn(List.of(makeEvent()));
        when(userRepository.findById(1)).thenReturn(new User(1, "Kelton", "Holsen", "kholsen@gmail.com", "555-455-5555", AccessLevel.ROLE_ADMINISTRATOR, "swordfishfishfish"));
        toAdd.setEventId(2);

        toAdd.setLocation(new Location(2, "The Barn", "124 Barn Bluff"));

        User notKelton = new User("Kelton", "Holsen", "kholsen@gmail.com", "555-455-5555", AccessLevel.ROLE_ADMINISTRATOR, "swordfishfishfish");
        notKelton.setUserId(3);
        Map<User, List<String>> userRoles = new HashMap<>();
        userRoles.put(notKelton, List.of("tech"));
        toAdd.setStaffAndRoles(userRoles);

        Result<Event> result = service.addEvent(toAdd);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddEventWithBadValues() {
        Event toAdd = makeEvent();
        when(eventRepository.addEvent(toAdd)).thenReturn(toAdd);
        when(locationRepository.findAll()).thenReturn(List.of(new Location(1, "123 Church Street", "The Church")));
        when(eventRepository.findAll()).thenReturn(new ArrayList<>());
        when(userRepository.findById(1)).thenReturn(new User("Kelton", "Holsen", "kholsen@gmail.com", "555-455-5555", AccessLevel.ROLE_ADMINISTRATOR, "swordfishfishfish"));
        toAdd.setEventId(2);
        toAdd.setStartDate(null);

        Result<Event> result = service.addEvent(toAdd);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddEventWithBadDates() {
        Event toAdd = makeEvent();
        when(eventRepository.addEvent(toAdd)).thenReturn(toAdd);
        when(locationRepository.findAll()).thenReturn(List.of(new Location(1, "123 Church Street", "The Church")));
        when(eventRepository.findAll()).thenReturn(new ArrayList<>());
        when(userRepository.findById(1)).thenReturn(new User("Kelton", "Holsen", "kholsen@gmail.com", "555-455-5555", AccessLevel.ROLE_ADMINISTRATOR, "swordfishfishfish"));
        toAdd.setEventId(2);
        toAdd.setStartDate(LocalDate.of(2025, 4, 4));

        Result<Event> result = service.addEvent(toAdd);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddEventWithBrokenItems() {
        Event toAdd = makeEvent();
        when(eventRepository.addEvent(toAdd)).thenReturn(toAdd);
        when(locationRepository.findAll()).thenReturn(List.of(new Location(1, "123 Church Street", "The Church")));
        when(eventRepository.findAll()).thenReturn(new ArrayList<>());
        when(userRepository.findById(1)).thenReturn(new User("Kelton", "Holsen", "kholsen@gmail.com", "555-455-5555", AccessLevel.ROLE_ADMINISTRATOR, "swordfishfishfish"));
        toAdd.setEventId(2);
        Item brokenMic = ItemServiceTest.makeNewItem();
        brokenMic.setBroken(true);
        toAdd.setEquipment(List.of(brokenMic));

        Result<Event> result = service.addEvent(toAdd);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldUpdateEvent() {
        Event toAdd = makeEvent();
        when(eventRepository.updateEvent(toAdd)).thenReturn(true);
        when(locationRepository.findAll()).thenReturn(List.of(new Location(1, "123 Church Street", "The Church")));
        when(eventRepository.findAll()).thenReturn(new ArrayList<>());
        when(userRepository.findById(1)).thenReturn(new User("Kelton", "Holsen", "kholsen@gmail.com", "555-455-5555", AccessLevel.ROLE_ADMINISTRATOR, "swordfishfishfish"));

        Result<Event> result = service.updateEvent(toAdd);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotUpdateNonPresentEvent() {
        Event toAdd = makeEvent();
        when(eventRepository.updateEvent(toAdd)).thenReturn(false);
        when(locationRepository.findAll()).thenReturn(List.of(new Location(1, "123 Church Street", "The Church")));
        when(eventRepository.findAll()).thenReturn(new ArrayList<>());
        when(userRepository.findById(1)).thenReturn(new User("Kelton", "Holsen", "kholsen@gmail.com", "555-455-5555", AccessLevel.ROLE_ADMINISTRATOR, "swordfishfishfish"));

        Result<Event> result = service.updateEvent(toAdd);
        assertFalse(result.isSuccess());
    }
}