package soundtrack.data;

import org.springframework.jdbc.core.JdbcTemplate;
import soundtrack.data.mappers.ItemMapper;
import soundtrack.models.Item;
import soundtrack.models.ItemCategory;

import java.util.List;

public class ItemJdbcRepository implements ItemRepository{

    private final JdbcTemplate jdbcTemplate;

    public ItemJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Item> findAll() {
        final String sql = "select item_id, item_name, description, brand, item_type, item_category, location_id, location_description, is_broken, notes from item;";
        return jdbcTemplate.query(sql, new ItemMapper());
    }

    @Override
    public List<Item> findByCategory(ItemCategory category) {
        return null;
    }

    @Override
    public List<Item> findByType(String itemType) {
        return null;
    }

    @Override
    public Item findById(int id) {
        return null;
    }

    @Override
    public Item add(Item item) {
        return null;
    }

    @Override
    public boolean update(Item item) {
        return false;
    }

    @Override
    public boolean deleteById(int itemId) {
        return false;
    }
}
