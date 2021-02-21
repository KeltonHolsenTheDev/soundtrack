package soundtrack.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.*;

public class Event {
    private int eventId;

    @NotBlank(message = "Event needs a name")
    private String eventName;

    @NotNull(message = "Start date cannot be null!")
    @FutureOrPresent(message = "Events cannot be created retroactively!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null!")
    @FutureOrPresent(message = "Events cannot be created retroactively!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotNull(message = "Events must have an owner!")
    private User owner; //service will get this

    private int ownerId;

    @NotEmpty(message = "Events must have at least one staff member!")
    private List<UserRole> staffAndRoles = new ArrayList<>(); //service will get this

    private List<Integer> staffIds = new ArrayList<>();

    @NotNull(message = "Events must have a location!")
    private Location location;

    private int locationId; //service will grab the actual location

    private List<Item> equipment = new ArrayList<>();

    private List<Integer> equipmentIds = new ArrayList<>(); //service will grab the actual equipment

    public Event() {
    }

    public Event(int eventId, @NotBlank(message = "Event needs a name") String eventName,
                 @NotNull(message = "Start date cannot be null!")
                 @FutureOrPresent(message = "Events cannot be created retroactively!") LocalDate startDate,
                 @NotNull(message = "End date cannot be null!")
                 @FutureOrPresent(message = "Events cannot be created retroactively!") LocalDate endDate,
                 int ownerId, int locationId) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.ownerId = ownerId;
        this.locationId = locationId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public List<UserRole> getStaffAndRoles() {
        return staffAndRoles;
    }

    public void setStaffAndRoles(List<UserRole> staffAndRoles) {
        this.staffAndRoles = staffAndRoles;

    }

    public List<Integer> getStaffIds() {
        return staffIds;
    }

    public void setStaffIds(List<Integer> staffIds) {
        this.staffIds = staffIds;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public List<Item> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<Item> equipment) {
        this.equipment = equipment;
    }

    public List<Integer> getEquipmentIds() {
        return equipmentIds;
    }

    public void setEquipmentIds(List<Integer> equipmentIds) {
        this.equipmentIds = equipmentIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return eventId == event.eventId &&
                ownerId == event.ownerId &&
                locationId == event.locationId &&
                Objects.equals(eventName, event.eventName) &&
                Objects.equals(startDate, event.startDate) &&
                Objects.equals(endDate, event.endDate) &&
                Objects.equals(staffIds, event.staffIds) &&
                Objects.equals(equipmentIds, event.equipmentIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, eventName, startDate, endDate, ownerId, staffIds, locationId, equipmentIds);
    }
}
