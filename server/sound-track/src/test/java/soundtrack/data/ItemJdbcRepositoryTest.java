package soundtrack.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import soundtrack.models.Item;
import soundtrack.models.ItemCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ItemJdbcRepositoryTest {

    @Autowired
    ItemJdbcRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    private List<Item> getExpected() {
        return List.of(
                new Item(1, "Microphone 1", "Bass mic", "Sony",
                        "microphone", ItemCategory.AUDIO, 1 ,null,"Shelf A",
                        false, "no notes"),
                new Item(2, "Drum", "Kick", "DrumstickInc",
                        "drum", ItemCategory.AUDIO, 1 ,null,"Shelf B",
                        false, "no notes"),
                new Item(3, "Short Throw", "Projector", "Panasonic",
                        "projector", ItemCategory.VIDEO, 1 ,null,"Shelf C",
                        false, "this one is good for short distance projecting")
        );
    }

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<Item> actual = repository.findAll();
        List<Item> expected = getExpected();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindById() {
        Item actual = repository.findById(1);
        Item expected = getExpected().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindByItemCategory() {
        List<Item> actual = repository.findByCategory(ItemCategory.AUDIO);
        List<Item> expected = getExpected().stream().limit(2).collect(Collectors.toList());
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindByItemType() {
        List<Item> actual = repository.findByType("projector");
        List<Item> expected = new ArrayList<>();
        expected.add(getExpected().get(2));
        assertEquals(expected, actual);
    }

    @Test
    void shouldAdd() {
        Item actual = repository.add(makeNewItem());
        Item expected = makeNewItem();
        expected.setItemId(4);
        assertEquals(expected, actual);
        assertEquals(4, repository.findAll().size());
    }

    @Test
    void shouldUpdate() {
        Item newItem = makeNewItem();
        newItem.setItemName("Microphone 4");
        assertTrue(repository.update(newItem));
        assertEquals(newItem, repository.findById(1));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(2));
        assertFalse(repository.deleteById(2));
    }

    private Item makeNewItem() {
        Item item = new Item(1, "Microphone 2", "Treble mic", "Sony",
                "microphone", ItemCategory.AUDIO, 1 ,null,"Shelf A",
                false, "no notes");
        return item;
    }
}
