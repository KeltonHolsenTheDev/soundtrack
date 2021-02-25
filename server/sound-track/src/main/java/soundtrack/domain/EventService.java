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
import soundtrack.models.UserRole;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import java.util.*;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.naming.*;

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

    public List<Event> findByUser(int userId) {
        List<Event> all = eventRepository.findByUser(userId);
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
            sendVolunteerEmails(event);
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

        //Make sure we don't have duplicate UserRoles in the event
        HashSet<Integer> userIds = new HashSet<>();
        for (UserRole ur: event.getStaffAndRoles()) {
            userIds.add(ur.getUser().getUserId());
        }
        if (userIds.size() != event.getStaffAndRoles().size()) {
            result.addMessage("Multiple user-role pairs exist for the same user in this event.", ResultType.INVALID);
        }

        //Frontend should only allow users to be given roles that they have, but we want to make sure that no bad roles snuck in
        for (UserRole ur: event.getStaffAndRoles()) {
            for (String role: ur.getRoles()) {
                if (!ur.getUser().getRoles().contains(role)) {
                    result.addMessage("User " + ur.getUser().getUserId() + " is not qualified for role " + role, ResultType.INVALID);
                }
            }
        }

        return result;
    }

    private void validateNoConflicts(Event event, Result<Event> result){
        List<Event> existing = this.findAll();
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
                for (User user: event.getStaffAndRoles().stream().map(UserRole::getUser).collect(Collectors.toList())) { //a volunteer cannot work two events at the same time
                    for (User u: e.getStaffAndRoles().stream().map(UserRole::getUser).collect(Collectors.toList())) {
                        if (u.getUserId() == user.getUserId()) {
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
        List<UserRole> roles = new ArrayList<>();
        for (int userId: event.getStaffIds()) {
            User user = userRepository.findById(userId);
            List<String> userRoles = userRepository.findUserEventRoles(userId, event.getEventId());
            roles.add(new UserRole(user, userRoles));
        }
        event.setStaffAndRoles(roles);
    }

    private void sendVolunteerEmails(Event event) {
        List<UserRole> userRoles = event.getStaffAndRoles();
        //make sure we aren't emailing someone who wasn't added to the event
        Event previousValue = eventRepository.findById(event.getEventId());
        if (previousValue != null) {
            userRoles = userRoles.stream().filter(userRole -> !previousValue.getStaffAndRoles().contains(userRole)).collect(Collectors.toList());
        }
        for (UserRole userRole: userRoles) {
            Properties properties = System.getProperties();
            properties.setProperty("mail.smtp.host", "smtp.gmail.com");
            properties.setProperty("mail.smtp.auth", "true");
            properties.setProperty("mail.smtp.starttls.enable", "true");
            properties.setProperty("mail.smtp.port", "587");
            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("soundtrackbyteamjak@gmail.com", "I pledge allegiance 2 Artemis.");
                }
            });
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress("soundtrackbyteamjak@gmail.com"));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(userRole.getUser().getEmail()));
                message.setSubject("Event Notification: " + event.getEventName());
                String text = "This is a notification that " + event.getOwner().getFirstName() + " " + event.getOwner().getLastName() +
                        " has signed you up to volunteer at event " + event.getEventName() + " taking place from " + event.getStartDate() + " to " +
                        event.getEndDate() + ". You will be doing the following roles:\n";
                for (String role: userRole.getRoles()) {
                    text += role + "\n";
                }
                message.setText(text);
                Transport.send(message);
            } catch (AddressException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
}
