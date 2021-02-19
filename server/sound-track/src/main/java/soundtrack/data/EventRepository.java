package soundtrack.data;

import soundtrack.models.Event;

import java.util.List;

public interface EventRepository {
    List<Event> findAll();
    Event findById(int eventId);
    Event findByOwner(int ownerId);
    Event addEvent(Event event);
    boolean updateEvent(Event event);
    boolean deleteById(int eventId);
}
