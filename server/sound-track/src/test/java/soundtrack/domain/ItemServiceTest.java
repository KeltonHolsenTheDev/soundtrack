package soundtrack.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import soundtrack.data.ItemRepository;
import soundtrack.data.LocationRepository;
import soundtrack.models.Item;
import soundtrack.models.ItemCategory;
import soundtrack.models.Location;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ItemServiceTest {

    @Autowired
    ItemService service;

    @MockBean
    ItemRepository itemRepository;

    @MockBean
    LocationRepository locationRepository;

    @Test
    void shouldAddValidItem() {
        Item item = new Item(1, "Microphone 1", "Bass mic", "Sony", "microphone", ItemCategory.AUDIO,
                "Shelf A", false, "no notes");
        item.setLocationId(1);
        item.setLocation(new Location(1, "123 Church Street", "The Church"));
        when(itemRepository.add(item)).thenReturn(item);
        when(locationRepository.findAll()).thenReturn(List.of(new Location(1, "123 Church Street", "The Church")));
        Result<Item> result = service.addItem(item);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotAddItemForNonPresentLocation() {
        Item item = new Item(1, "Microphone 1", "Bass mic", "Sony", "microphone", ItemCategory.AUDIO,
                "Shelf A", false, "no notes");
        item.setLocationId(1);
        item.setLocation(new Location(1, "123 Church Street", "The Church"));
        when(itemRepository.add(item)).thenReturn(item);
        when(locationRepository.findAll()).thenReturn(new ArrayList<>());
        Result<Item> result = service.addItem(item);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldUpdateValidItem() {
        Item item = new Item(1, "Microphone 1", "Bass mic", "Sony", "microphone", ItemCategory.AUDIO,
                "Shelf A", false, "no notes");
        item.setLocationId(1);
        item.setLocation(new Location(1, "123 Church Street", "The Church"));
        when(itemRepository.update(item)).thenReturn(true);
        when(locationRepository.findAll()).thenReturn(List.of(new Location(1, "123 Church Street", "The Church")));
        Result<Item> result = service.updateItem(item);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotUpdateItemToBadLocation() {
        Item item = new Item(1, "Microphone 1", "Bass mic", "Sony", "microphone", ItemCategory.AUDIO,
                "Shelf A", false, "no notes");
        item.setLocationId(1);
        item.setLocation(new Location(1, "123 Church Street", "The Church"));
        when(itemRepository.update(item)).thenReturn(true);
        when(locationRepository.findAll()).thenReturn(new ArrayList<>());
        Result<Item> result = service.updateItem(item);
        assertFalse(result.isSuccess());
    }
}