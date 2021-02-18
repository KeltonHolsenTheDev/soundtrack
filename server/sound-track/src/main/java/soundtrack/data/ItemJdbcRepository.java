package soundtrack.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import soundtrack.data.mappers.ItemMapper;
import soundtrack.models.Item;
import soundtrack.models.ItemCategory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ItemJdbcRepository implements ItemRepository{

    private final JdbcTemplate jdbcTemplate;

    public ItemJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private void attachItemTypes(Item item) {
        final String sql = "select it.type_name from item_type it " +
                "inner join item i on i.item_type_id = it.item_type_id;";
        String type_name = jdbcTemplate.query(sql, this::mapTypeName).stream().findFirst().orElse(null);
        item.setItemType(type_name);
    }

    private String mapTypeName(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getString("type_name");
    }

    private void attachLocation(Item item) {
        final String sql = "select l.location_name,  from item_type it " +
                "inner join item i on i.item_type_id = it.item_type_id;";
        String type_name = jdbcTemplate.query(sql, this::mapTypeName).stream().findFirst().orElse(null);
        item.setItemType(type_name);
    }

    @Override
    public List<Item> findAll() {
        final String sql = "select item_id, item_name, description, brand, item_type_id, item_category, " +
                "location_id, location_description, is_broken, notes from item;";
        List<Item> items = jdbcTemplate.query(sql, new ItemMapper());
        items.forEach(this::attachItemTypes);
        return items;
    }

    private void addItemType(List<String> typeNames, int itemId) {
        for (String typeName: typeNames) {
            String sql = "select item_type_id from item_type where type_name = ?;";
            int itemTypeId = jdbcTemplate.query(sql, this::mapItemTypeId, typeName).stream().findFirst().orElse(-1);

            if (itemTypeId == -1) {
                sql = "insert into item_type (type_name) values (?);";
                jdbcTemplate.update(sql, typeName);
                sql = "select item_type_id from item_type where type_name = ?;";
                itemTypeId = jdbcTemplate.query(sql, this::mapItemTypeId, typeName).stream().findFirst().orElse(-1);
            }

            if (itemTypeId == -1) {
                System.out.println("Item type " + typeName + "could not be added to item " + itemId);
            }
        }
    }

    private int mapItemTypeId(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getInt("item_type_id");
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
