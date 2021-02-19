package soundtrack.models;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Event {
    private int eventId;

    @NotNull(message = "Start date cannot be null!")
    @FutureOrPresent(message = "Events cannot be created retroactively!")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null!")
    @FutureOrPresent(message = "Events cannot be created retroactively!")
    private LocalDate endDate;

    @NotNull(message = "Events must have an owner!")
    private User owner; //service will get this

    private int ownerId;

    @NotEmpty(message = "Events must have at least one staff member!")
    private Map<User, List<String>> staffAndRoles = new HashMap<User, List<String>>(); //service will get this

    private List<Integer> staffIds = new ArrayList<>();

    @NotNull(message = "Events must have a location!")
    private Location location;

    private int locationId; //service will grab the actual location

    private List<Item> equipment = new ArrayList<>();

    private List<Integer> equipmentIds = new ArrayList<>(); //service will grab the actual equipment

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
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

    public Map<User, List<String>> getStaffAndRoles() {
        return staffAndRoles;
    }

    public void setStaffAndRoles(Map<User, List<String>> staffAndRoles) {
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
}
