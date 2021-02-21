package soundtrack.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest {

    @MockBean
    EventRepository eventRepository;

    @MockBean
    ItemRepository itemRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    LocationRepository locationRepository;

    @Autowired
    MockMvc mvc;

    /*@Test cannot run due to user authorities issue
    void shouldAdd() throws Exception {
        Event event = makeNewEvent();
        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(event);
        when(eventRepository.addEvent(event)).thenReturn(event);
        when(locationRepository.findAll()).thenReturn(List.of(makeNewLocation()));
        when(userRepository.findById(1)).thenReturn(makeNewUser());
        when(itemRepository.findAll()).thenReturn(List.of(makeNewItem()));

        var request = post("/api/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isAccepted());
    }*/

    @Test
    void shouldNotAddBadData() throws Exception {
        Event event = makeNewEvent();
        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(event);
        when(eventRepository.addEvent(event)).thenReturn(event);
        when(locationRepository.findAll()).thenReturn(List.of(makeNewLocation()));
        when(userRepository.findById(1)).thenReturn(makeNewUser());
        when(itemRepository.findAll()).thenReturn(List.of(makeNewItem()));

        var request = post("/api/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isAccepted());
    }

    @Test
    void shouldUpdate() {

    }

    @Test
    void shouldNotUpdateWithConflict() {

    }

    @Test
    void shouldHandleConflict() {

    }

    @Test
    void shouldReturn404ForNotFound() {

    }

    @Test
    void shouldDelete() throws Exception {
        when(eventRepository.deleteById(1)).thenReturn(true);
        var request = delete("/api/event/1");

        mvc.perform(request)
                .andExpect(status().isAccepted());
    }

    private Event makeNewEvent() {
        Event event = new Event();
        event.setEventId(1);
        event.setEventName("Church Service");
        event.setStartDate(LocalDate.of(2021,2,21));
        event.setEndDate(LocalDate.of(2021,2,21));

        Location location = makeNewLocation();
        event.setLocationId(location.getLocationId());
        event.setLocation(location);
        Item item = makeNewItem();
        event.setEquipment(List.of(item));
        event.setEquipmentIds(List.of(1));

        User kelton = makeNewUser();

        List<UserRole> userRoles = new ArrayList<>();
        userRoles.add(new UserRole(kelton, List.of("tech")));
        kelton.setRoles(List.of("tech"));

        event.setStaffAndRoles(userRoles);
        event.setStaffIds(List.of(1));
        event.setOwnerId(1);
        event.setOwner(kelton);
        return event;
    }

    private Item makeNewItem() {
        Item item = new Item();
        item.setItemId(1);
        item.setItemName("Microphone 1");
        item.setDescription("Bass Mic");
        item.setBrand("Sony");
        item.setItemType("microphone");
        item.setItemCategory(ItemCategory.AUDIO);
        item.setLocationId(1);
        item.setLocation(makeNewLocation());
        item.setLocationDescription("shelf A");
        return item;
    }

    private Location makeNewLocation() {
        Location location = new Location();
        location.setLocationId(1);
        location.setAddress("123 Church Street");
        location.setName("The Church");
        return location;
    }

    private User makeNewUser() {
        User kelton = new User();
        kelton.setUserId(1);
        kelton.setFirstName("Kelton");
        kelton.setLastName("Holsen");
        kelton.setEmail("kholsen@gmail.com");
        kelton.setPhone("555-455-5555");
        kelton.setAccessLevel(AccessLevel.ROLE_ADMINISTRATOR);
        kelton.setPassword("swordfishfishfish");
        return kelton;
    }
}
