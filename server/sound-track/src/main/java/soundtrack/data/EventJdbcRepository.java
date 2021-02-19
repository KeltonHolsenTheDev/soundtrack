package soundtrack.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import soundtrack.data.mappers.EventMapper;
import soundtrack.models.Event;

import java.util.List;

@Repository
public class EventJdbcRepository implements EventRepository {

    private final JdbcTemplate jdbcTemplate;

    public EventJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Event> findAll() {
       final String sql = "select event_id, location_id, start_date, end_date, owner_id from event;";
       //attach relevant things
       return jdbcTemplate.query(sql, new EventMapper());
    }

    @Override
    public Event findById(int eventId) {
        return null;
    }

    @Override
    public Event findByOwner(int ownerId) {
        return null;
    }

    @Override
    public Event addEvent(Event event) {
        return null;
    }

    @Override
    public boolean updateEvent(Event event) {
        return false;
    }

    @Override
    public boolean deleteEvent(Event event) {
        return false;
    }
}
