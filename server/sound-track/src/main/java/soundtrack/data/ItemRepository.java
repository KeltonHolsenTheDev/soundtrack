package soundtrack.data;

import soundtrack.models.Item;
import soundtrack.models.ItemCategory;

import java.util.List;

public interface ItemRepository {
    List<Item> findAll();
    List<Item> findByCategory(ItemCategory category);
    List<Item> findByType(String itemType);
    //List<Item> findByEvent(Event event);
    Item findById(int id);
    Item add(Item item);
    boolean update(Item item);
    boolean deleteById(int itemId);

}
