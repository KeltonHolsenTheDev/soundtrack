package soundtrack.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import soundtrack.data.mappers.LocationMapper;
import soundtrack.models.Location;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class LocationJdbcRepository implements LocationRepository {

    private final JdbcTemplate jdbcTemplate;

    public LocationJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Location> findAll() {
        final String sql = "select (location_id, location_name, address) from location;";
        return jdbcTemplate.query(sql, new LocationMapper());
    }

    @Override
    public Location findById(int id) {
        final String sql = "select (location_id, location_name, address) from location where location_id = ?;";
        return jdbcTemplate.query(sql, new LocationMapper(), id).stream().findFirst().orElse(null);
    }

    @Override
    public Location addLocation(Location location) {
        final String sql = "insert into location (address, location_name) values (?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, location.getAddress());
            ps.setString(2, location.getName());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        location.setLocationId(keyHolder.getKey().intValue());
        return location;
    }

    @Override
    public boolean updateLocation(Location location) {
        final String sql = "update location set " +
                "address = ?, " +
                "location_name = ?, " +
                "where location_id = ?;";

        return jdbcTemplate.update(sql, location.getAddress(), location.getName(), location.getLocationId()) > 0;
    }


    @Override
    public boolean deleteById(int id) {
        //Additional deletions will need to go here when other models are implemented
        return jdbcTemplate.update("delete from location where location_id = ?;", id) > 0;
    }
}
