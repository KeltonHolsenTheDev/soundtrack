package soundtrack.data.mappers;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import soundtrack.models.Location;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationMapper implements RowMapper<Location> {
    @Override
    public Location mapRow(ResultSet resultSet, int i) throws SQLException {
        Location location = new Location();
        location.setLocationId(resultSet.getInt("location_id"));
        location.setName(resultSet.getString("location_name"));
        location.setAddress(resultSet.getString("address"));
        return location;
    }
}
