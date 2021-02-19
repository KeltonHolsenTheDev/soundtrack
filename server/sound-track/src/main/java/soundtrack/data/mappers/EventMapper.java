package soundtrack.data.mappers;


import org.springframework.jdbc.core.RowMapper;
import soundtrack.models.Event;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventMapper implements RowMapper<Event> {
    @Override
    public Event mapRow(ResultSet resultSet, int i) throws SQLException {
        return null;
    }
}
