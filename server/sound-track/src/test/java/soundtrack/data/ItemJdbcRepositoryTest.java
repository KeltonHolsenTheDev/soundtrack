package soundtrack.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import soundtrack.models.Item;
import soundtrack.models.ItemCategory;

import java.util.List;

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
}
