package soundtrack.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import soundtrack.data.ItemRepository;
import soundtrack.data.LocationRepository;
import soundtrack.models.Item;
import soundtrack.models.ItemCategory;
import soundtrack.models.Location;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

    @MockBean
    ItemRepository itemRepository;

    @MockBean
    LocationRepository locationRepository;

    @Autowired
    MockMvc mvc;

    @Test
    void shouldAdd() throws Exception {
        Item item = new Item(1, "Microphone 1", "Bass mic", "Sony",
                "microphone", ItemCategory.AUDIO, 1 ,null,"Shelf A",
                false, "no notes");
        item.setLocationId(1);
        item.setLocation(new Location(1, "123 Church Street", "The Church"));
        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(item);
        when(itemRepository.add(item)).thenReturn(item);
        when(locationRepository.findAll()).thenReturn(List.of(new Location(1, "123 Church Street", "The Church")));

        var request = post("/api/item")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isAccepted());
    }

    @Test
    void should400ForBadData() throws Exception {
        Item item = new Item(1, "Microphone 1", "Bass mic", "Sony",
                "microphone", ItemCategory.AUDIO, 1 ,null,"Shelf A",
                false, "no notes");
        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(item);
        when(itemRepository.add(item)).thenReturn(item);

        var request = post("/api/item")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldAllowValidUpdate() throws Exception {
        Item item = new Item(1, "Microphone 1", "Bass mic", "Sony",
                "microphone", ItemCategory.AUDIO, 1 ,null,"Shelf A",
                false, "no notes");
        item.setLocationId(1);
        item.setLocation(new Location(1, "123 Church Street", "The Church"));
        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(item);
        when(itemRepository.update(item)).thenReturn(true);
        when(locationRepository.findAll()).thenReturn(List.of(new Location(1, "123 Church Street", "The Church")));

        var request = put("/api/item/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isAccepted());
    }

    @Test
    void shouldNotAllowBadUpdate() throws Exception {
        Item item = new Item(1, "Microphone 1", "Bass mic", "Sony",
                "microphone", ItemCategory.AUDIO, 1 ,null,"Shelf A",
                false, "no notes");
        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(item);
        when(itemRepository.update(item)).thenReturn(true);

        var request = put("/api/item/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldHandleConflict() throws Exception {
        Item item = new Item(1, "Microphone 1", "Bass mic", "Sony",
                "microphone", ItemCategory.AUDIO, 1 ,null,"Shelf A",
                false, "no notes");
        item.setLocationId(1);
        item.setLocation(new Location(1, "123 Church Street", "The Church"));
        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(item);
        when(itemRepository.update(item)).thenReturn(true);
        when(locationRepository.findAll()).thenReturn(List.of(new Location(1, "123 Church Street", "The Church")));

        var request = put("/api/item/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isConflict());
    }

    @Test
    void shouldReturn404ForNotFound() throws Exception {
        Item item = new Item(1, "Microphone 1", "Bass mic", "Sony",
                "microphone", ItemCategory.AUDIO, 1 ,null,"Shelf A",
                false, "no notes");
        item.setLocationId(1);
        item.setLocation(new Location(1, "123 Church Street", "The Church"));
        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(item);
        when(itemRepository.update(item)).thenReturn(false);
        when(locationRepository.findAll()).thenReturn(List.of(new Location(1, "123 Church Street", "The Church")));

        var request = put("/api/item/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isNotFound());
    }
}