package soundtrack.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import soundtrack.data.mappers.ItemMapper;
import soundtrack.models.Item;
import soundtrack.models.ItemCategory;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ItemJdbcRepository implements ItemRepository{

    private final JdbcTemplate jdbcTemplate;

    public ItemJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Item> findAll() {
        final String sql = "select item_id, item_name, description, brand, item_type, item_category, " +
                "location_id, location_description, is_broken, notes from item;";
        return jdbcTemplate.query(sql, new ItemMapper());
    }

    @Override
    public List<Item> findByCategory(ItemCategory category) {
        final String sql = "select item_id, item_name, description, brand, item_type, item_category, " +
                "location_id, location_description, is_broken, notes from item where item_category = ?;";
        return jdbcTemplate.query(sql, new ItemMapper(), category).stream()
                .filter(i -> i.getItemCategory() == category).collect(Collectors.toList());
    }

    @Override
    public List<Item> findByType(String itemType) {
        final String sql = "select item_id, item_name, description, brand, item_type, item_category, " +
                "location_id, location_description, is_broken, notes from item where item_type = ?;";
        return jdbcTemplate.query(sql, new ItemMapper(), itemType).stream()
                .filter(i -> i.getItemType().equals(itemType)).collect(Collectors.toList());
    }

    @Override
    public Item findById(int id) {
        final String sql = "select item_id, item_name, description, brand, item_type, item_category, " +
                "location_id, location_description, is_broken, notes from item where item_id = ?;";
        return jdbcTemplate.query(sql, new ItemMapper(), id).stream()
                .filter(i -> i.getItemId() == (id)).findFirst().orElse(null);
    }

    @Override
    public Item add(Item item) {
        final String sql = "insert into item " +
                "select i.item_name, i.description, i.brand, i.item_type, it.type_name, i.item_category " +
                "i.location_id, i.location_description, i.is_broken, i.getNotes " +
                "from item i inner join item_type it on i.item_type_id = it.item_type_id;";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, item.getItemName());
            ps.setString(2, item.getDescription());
            ps.setString(3, item.getBrand());
            ps.setString(4, item.getItemType());
            ps.setString(5, item.getItemCategory().toString());
            ps.setInt(6, item.getLocationId());
            ps.setString(7, item.getLocationDescription());
            ps.setBoolean(8, item.isBroken());
            ps.setString(9, item.getNotes());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        item.setItemId(keyHolder.getKey().intValue());
        return item;
    }

    @Override
    public boolean update(Item item) {
        final String sql = "update item set " +
                "item_name = ?, " +
                "description = ? " +
                "brand = ?" +
                "item_type = ?" +
                "item_category = ?" +
                "location_id = ?" +
                "location_description = ?" +
                "is_broken = ?" +
                "notes = ?" +
                "where item_id = ?;";

        return jdbcTemplate.update(sql, item.getItemName(), item.getDescription(), item.getBrand(),
                item.getItemType(), item.getItemCategory(), item.getLocationId(), item.getLocation(),
                item.getLocationDescription(), item.isBroken(), item.getNotes()) > 0;
    }

    @Override
    public boolean deleteById(int itemId) {
        //Additional deletions will need to go here when other models are implemented
        return jdbcTemplate.update("delete from item where item_id = ?;", itemId) > 0;
    }
}
