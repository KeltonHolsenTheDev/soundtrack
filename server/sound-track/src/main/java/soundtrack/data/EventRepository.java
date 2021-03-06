package soundtrack.data;

import soundtrack.models.Event;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository {
    List<Event> findAll();
    Event findById(int eventId);
    List<Event> findByOwner(int ownerId);
    List<Event> findByDate(LocalDate date);
    List<Event> findByUser(int userId);
    Event addEvent(Event event);
    boolean updateEvent(Event event);
    boolean deleteById(int eventId);

    void deleteByLocation(int locationId);

    void deleteByOwner(int ownerId);
}
