package soundtrack.domain;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import soundtrack.data.EventRepository;
import soundtrack.data.ItemRepository;
import soundtrack.data.LocationRepository;
import soundtrack.data.UserRepository;
import soundtrack.models.Event;
import soundtrack.models.Item;
import soundtrack.models.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.*;

@Service
public class EventService {
    private final EventRepository eventRepository;

    private final UserRepository userRepository;

    private final ItemRepository itemRepository;

    private final LocationRepository locationRepository;

    public EventService(EventRepository eventRepository, UserRepository userRepository, ItemRepository itemRepository, LocationRepository locationRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.locationRepository = locationRepository;
    }

    public List<Event> findAll() {
        List<Event> all = eventRepository.findAll();
        all.forEach(this::attachModels);
        return all;
    }

    public Event findById(int eventId) {
        Event event = eventRepository.findById(eventId);
        if (event == null) {
            return null;
        }
        attachModels(event);
        return event;
    }

    public List<Event> findByOwner(int ownerId) {
        List<Event> all = eventRepository.findByOwner(ownerId);
        all.forEach(this::attachModels);
        return all;
    }

    public List<Event> findByDate(LocalDate date) {
        List<Event> all = eventRepository.findByDate(date);
        all.forEach(this::attachModels);
        return all;
    }

    public Result<Event> addEvent(Event event) {
        Result<Event> result = validate(event);
        if (!result.isSuccess()) {
            return result;
        }
        Event out = eventRepository.addEvent(event);
        if (out == null) {
            result.addMessage("Internal database error prevented adding event", ResultType.NOT_FOUND);
        }
        else {
            result.setPayLoad(out);
        }
        return result;
    }

    public Result<Event> updateEvent(Event event) {
        Result<Event> result = validate(event);
        if (!result.isSuccess()) {
            return result;
        }
        if (eventRepository.updateEvent(event)) {
            result.setPayLoad(event);
        }
        else {
            result.addMessage("Event not found", ResultType.NOT_FOUND);
        }
        return result;
    }

    public boolean deleteById(int eventId) {
        return eventRepository.deleteById(eventId);
    }

    private Result<Event> validate(Event event) {
        Result<Event> result = new Result<>();
        if (event == null) {
            result.addMessage("Event cannot be null!", ResultType.NOT_FOUND);
            return result;
        }
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Event>> violations = validator.validate(event);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Event> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
            return result;
        }
        if (event.getStartDate().isAfter(event.getEndDate())) {
            result.addMessage("Event start date must come before end date!", ResultType.INVALID);
        }
        validateNoConflicts(event, result);
        for (Item item: event.getEquipment()) {
            if (item.isBroken()) {
                result.addMessage("Item " + item.getItemId() + ": " + item.getItemName() + " is broken!", ResultType.INVALID);
            }
        }

        return result;
    }

    private void validateNoConflicts(Event event, Result<Event> result){
        List<Event> existing = eventRepository.findAll();
        for (Event e: existing) {
            if (checkOverlap(event, e)) { //conflicts don't matter if the time is different
                if (e.getLocation().equals(event.getLocation())) { //two events can't use the same location
                    result.addMessage("Event at " + event.getLocation().getName() + " overlaps with existing event " + e.getEventId() + " from " + e.getStartDate() + " to " + e.getEndDate(), ResultType.INVALID);
                }
                for (Item item: event.getEquipment()) { //two events can't use the same item
                    for (Item i: e.getEquipment()) {
                        if (i.equals(item)) {
                            result.addMessage("Event " + e.getEventName() + " is already using " + item.getItemName() + " at that time.", ResultType.INVALID);
                        }
                    }
                }
                for (User user: event.getStaffAndRoles().keySet()) { //a volunteer cannot work two events at the same time
                    for (User u: e.getStaffAndRoles().keySet()) {
                        if (u.equals(user)) {
                            result.addMessage("Event " + e.getEventName() + " is already using " + user.getFirstName() + " " + user.getLastName() + " at that time.", ResultType.INVALID);
                        }
                    }
                }
            }
        }
    }

    private boolean checkOverlap(Event event, Event e) {
        boolean overlap = false;
        // A   B    A   B
        if (event.getStartDate().compareTo(e.getStartDate()) >= 0 && event.getStartDate().compareTo(e.getEndDate()) < 0) {
            overlap = true;
        }
        //B    A   B   A
        if (event.getEndDate().compareTo(e.getStartDate()) > 0 && event.getEndDate().compareTo(e.getEndDate()) <= 0) {
            overlap = true;
        }
        //B    A   A    B
        if (event.getStartDate().compareTo(e.getStartDate()) <= 0 && event.getEndDate().compareTo(e.getEndDate()) >= 0) {
            overlap = true;
        }

        //Special case for updates to allow old dates to overlap new for the same event
        if (e.getEventId() == event.getEventId()) {
            overlap = false;
        }
        return overlap;
    }

    private void attachModels(Event event) {
        event.setLocation(locationRepository.findById(event.getLocationId()));
        event.setOwner(userRepository.findById(event.getOwnerId()));
        List<Item> items = new ArrayList<>();
        for (int itemId: event.getEquipmentIds()) {
            Item item = itemRepository.findById(itemId);
            item.setLocation(locationRepository.findById(item.getLocationId()));
            items.add(item);
        }
        event.setEquipment(items);
        Map<User, List<String>> staffRoles = new HashMap<>();
        for (int userId: event.getStaffIds()) {
            Map<User, String> userMap = userRepository.findUserEventRoles(userId, event.getEventId());
            User user = userRepository.findById(userId);
            staffRoles.put(user, (List<String>) userMap.values());
        }
        event.setStaffAndRoles(staffRoles);
    }
}
