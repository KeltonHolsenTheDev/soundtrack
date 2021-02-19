package soundtrack.data.mappers;


import org.springframework.jdbc.core.RowMapper;
import soundtrack.models.Event;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class EventMapper implements RowMapper<Event> {
    @Override
    public Event mapRow(ResultSet resultSet, int i) throws SQLException {
        Event event = new Event();
        event.setEventId(resultSet.getInt("event_id"));
        event.setEventName(resultSet.getString("event_name"));
        event.setStartDate(resultSet.getDate("start_date").toLocalDate());
        event.setEndDate(resultSet.getDate("end_date").toLocalDate());
        event.setLocationId(resultSet.getInt("location_id"));
        event.setOwnerId(resultSet.getInt("owner_id"));
        return event;
    }
}
