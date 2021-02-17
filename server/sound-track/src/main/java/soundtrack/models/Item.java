package soundtrack.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class Item {

    private int itemId;

    @NotBlank(message = "Item name cannot be null or blank!")
    private String itemName;

    private String description;

    @NotBlank(message = "Brand cannot be null or blank!")
    private String brand;

    @NotBlank(message = "Item must have a type")
    private String itemType;

    @NotNull(message = "Item must have a category!")
    private ItemCategory itemCategory;

    private int locationId;

    private Location location;

    @NotBlank(message = "If you don't describe where it goes, how will anyone find it?")
    private String locationDescription;

    private boolean isBroken;

    private String notes;

    public Item() {

    }

    public Item(int itemId, @NotBlank(message = "Item name cannot be null or blank!") String itemName, @NotBlank(message = "") String description, String brand, String itemType, ItemCategory itemCategory, String locationDescription, boolean isBroken, String notes) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.description = description;
        this.brand = brand;
        this.itemType = itemType;
        this.itemCategory = itemCategory;
        this.locationId = locationId;
        this.locationDescription = locationDescription;
        this.isBroken = isBroken;
        this.notes = notes;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public ItemCategory getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(ItemCategory itemCategory) {
        this.itemCategory = itemCategory;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) { this.locationId = locationId;}

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public boolean isBroken() {
        return isBroken;
    }

    public void setBroken(boolean broken) {
        isBroken = broken;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return itemId == item.itemId &&
                locationId == item.locationId &&
                isBroken == item.isBroken &&
                Objects.equals(itemName, item.itemName) &&
                Objects.equals(description, item.description) &&
                Objects.equals(brand, item.brand) &&
                Objects.equals(itemType, item.itemType) &&
                itemCategory == item.itemCategory &&
                Objects.equals(location, item.location) &&
                Objects.equals(locationDescription, item.locationDescription) &&
                Objects.equals(notes, item.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, itemName, description, brand, itemType, itemCategory, locationId, location, locationDescription, isBroken, notes);
    }
}
