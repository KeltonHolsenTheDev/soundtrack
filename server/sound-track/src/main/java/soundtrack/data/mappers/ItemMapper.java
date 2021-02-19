package soundtrack.data.mappers;

import org.springframework.jdbc.core.RowMapper;
import soundtrack.models.Item;
import soundtrack.models.ItemCategory;
import soundtrack.models.Location;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemMapper implements RowMapper<Item> {

    @Override
    public Item mapRow(ResultSet resultSet, int i) throws SQLException {
        Item item = new Item();
        item.setItemId(resultSet.getInt("item_id"));
        item.setItemName(resultSet.getString("item_name"));
        item.setDescription(resultSet.getString("description"));
        item.setBrand(resultSet.getString("brand"));
//        item.setItemType(resultSet.getString("item_type"));
        item.setItemCategory(ItemCategory.valueOf(resultSet.getString("item_category")));
        item.setLocationId(resultSet.getInt("location_id"));
        item.setLocationDescription(resultSet.getString("location_description"));
        item.setBroken(resultSet.getBoolean("is_broken"));
        item.setNotes(resultSet.getString("notes"));
        return item;
    }
}
