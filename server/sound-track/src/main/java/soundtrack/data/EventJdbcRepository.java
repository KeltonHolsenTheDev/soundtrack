package soundtrack.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import soundtrack.data.mappers.EventMapper;
import soundtrack.models.Event;
import soundtrack.models.Item;
import soundtrack.models.User;
import soundtrack.models.UserRole;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class EventJdbcRepository implements EventRepository {

    private final JdbcTemplate jdbcTemplate;

    public EventJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Event> findAll() {
       final String sql = "select event_id, event_name, location_id, start_date, end_date, owner_id from event_;";
       List<Event> events = jdbcTemplate.query(sql, new EventMapper());
       events.stream().forEach(this::attachItemIds);
       events.stream().forEach(this::attachStaffIds);
       return events;
    }

    private void attachStaffIds(Event event) {
        final String sql = "select u.user_id from user_role u inner join event_user_role e on e.user_role_id = u.user_role_id " +
                "where e.event_id = ?;";
        List<Integer> staffIds = jdbcTemplate.query(sql, this::mapUserId, event.getEventId());
        event.setStaffIds(staffIds);
    }

    private void attachItemIds(Event event) {
        final String sql = "select item_id from event_item where event_id = ?;";
        List<Integer> itemIds = jdbcTemplate.query(sql, this::mapItemId, event.getEventId());
        event.setEquipmentIds(itemIds);
    }

    private int mapUserId(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getInt("user_id");
    }

    private int mapItemId(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getInt("item_id");
    }

    @Override
    public Event findById(int eventId) {
        final String sql = "select event_id, event_name, location_id, start_date, end_date, owner_id from event_ where event_id = ?;";
        Event event = jdbcTemplate.query(sql, new EventMapper(), eventId).stream().findFirst().orElse(null);
        if (event == null) {
            return null;
        }
        attachItemIds(event);
        attachStaffIds(event);
        return event;
    }

    @Override
    public List<Event> findByOwner(int ownerId) {
        final String sql = "select event_id, event_name, location_id, start_date, end_date, owner_id from event_ where owner_id = ?;";
        List<Event> events = jdbcTemplate.query(sql, new EventMapper(), ownerId);
        events.forEach(this::attachItemIds);
        events.forEach(this::attachStaffIds);
        return events;
    }

    @Override
    public List<Event> findByDate(LocalDate date) {
        List<Event> all = this.findAll();
        return all.stream().filter(event -> ((event.getStartDate().isBefore(date) || event.getStartDate().isEqual(date))
                && (event.getEndDate().isAfter(date) || event.getEndDate().isEqual(date)))).collect(Collectors.toList());
    }

    @Override
    public List<Event> findByUser(int userId) {
        final String sql = "select e.event_id, e.event_name, e.location_id, e.start_date, e.end_date, e.owner_id from event_ e " +
                "inner join event_user_role eu on e.event_id = eu.event_id " +
                "inner join user_role u on u.user_role_id = eu.user_role_id " +
                "where u.user_id = ?;";
        List<Event> events = jdbcTemplate.query(sql, new EventMapper(), userId);
        events.forEach(this::attachItemIds);
        events.forEach(this::attachStaffIds);
        return events;
    }

    @Override
    public Event addEvent(Event event) {
        final String sql = "insert into event_ (event_name, start_date, end_date, location_id, owner_id) values (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, event.getEventName());
            ps.setDate(2, Date.valueOf(event.getStartDate()));
            ps.setDate(3, Date.valueOf(event.getEndDate()));
            ps.setInt(4, event.getLocation().getLocationId());
            ps.setInt(5, event.getOwner().getUserId());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }
        else {
            addEquipment(event.getEquipment(), keyHolder.getKey().intValue());
            addStaff(event.getStaffAndRoles(), keyHolder.getKey().intValue());
        }
        event.setEventId(keyHolder.getKey().intValue());
        return event;
    }

    private void addEquipment(List<Item> equipment, int eventId) {
        for (Item item: equipment) {
            final String sql = "insert into event_item (event_id, item_id) values (?, ?);";
            jdbcTemplate.update(sql, eventId, item.getItemId());
        }
    }

    private void addStaff(List<UserRole> staffAndRoles, int eventId) {
        for (UserRole userRole: staffAndRoles) {
            List<String> eventRoles = userRole.getRoles();
            for (String role: eventRoles) {
                //get the ids of the user's roles for the event, assuming that they won't have been assigned roles they can't do
                String sql = "select u.user_role_id from user_role u inner join role r on u.role_id = r.role_id " +
                        "where r.role_name = ?;";
                int roleId = jdbcTemplate.query(sql, this::mapRoleId, role).stream().findFirst().orElse(-1);
                if (roleId == -1) {
                    //this shouldn't happen but we should handle it anyways by throwing it away
                    System.out.println("Service somehow passed us a user in a role they shouldn't have");
                    continue;
                }
                //assign that role in the bridge-bridge table
                sql = "insert into event_user_role (user_role_id, event_id) values (?, ?);";
                jdbcTemplate.update(sql, roleId, eventId);
            }
        }
    }

    private int mapRoleId(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getInt("user_role_id");
    }

    @Override
    public boolean updateEvent(Event event) {
        final String sql = "update event_ set " +
                "event_name = ?, " +
                "start_date = ?, " +
                "end_date = ?, " +
                "location_id = ?, " +
                "owner_id = ? " +
                "where event_id = ?;";
        boolean success = jdbcTemplate.update(sql, event.getEventName(), event.getStartDate(), event.getEndDate(), event.getLocation().getLocationId(), event.getOwner().getUserId(),
                event.getEventId()) > 0;
        if (success) {
            updateEquipment(event);
            updateStaff(event);
        }
        return success;
    }

    private void updateEquipment(Event event) {
        jdbcTemplate.update("delete from event_item where event_id = ?;", event.getEventId());
        addEquipment(event.getEquipment(), event.getEventId());
    }

    private void updateStaff(Event event) {
        jdbcTemplate.update("delete from event_user_role where event_id = ?;", event.getEventId());
        addStaff(event.getStaffAndRoles(), event.getEventId());
    }

    @Override
    public boolean deleteById(int eventId) {
        jdbcTemplate.update("delete from event_item where event_id = ?;", eventId);
        jdbcTemplate.update("delete from event_user_role where event_id = ?;", eventId);
        return jdbcTemplate.update("delete from event_ where event_id = ?;", eventId) > 0;
    }
}
