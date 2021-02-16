package soundtrack.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class Item {

    private int itemId;

    @NotBlank(message = "Item name cannot be null or blank!")
    private String itemName;

    @NotBlank(message = "")
    private String description;

    private String Brand;

    private int itemTypeId;

    private ItemCategory itemCategory;

    private String locationDescription;

    private boolean isBroken;

    private String notes;

    public Item(int itemId, @NotBlank(message = "Item name cannot be null or blank!") String itemName, @NotBlank(message = "") String description, String brand, int itemTypeId, ItemCategory itemCategory, String locationDescription, boolean isBroken, String notes) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.description = description;
        Brand = brand;
        this.itemTypeId = itemTypeId;
        this.itemCategory = itemCategory;
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
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public int getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(int itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public ItemCategory getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(ItemCategory itemCategory) {
        this.itemCategory = itemCategory;
    }

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return itemId == item.itemId && itemTypeId == item.itemTypeId && isBroken == item.isBroken && Objects.equals(itemName, item.itemName) && Objects.equals(description, item.description) && Objects.equals(Brand, item.Brand) && itemCategory == item.itemCategory && Objects.equals(locationDescription, item.locationDescription) && Objects.equals(notes, item.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, itemName, description, Brand, itemTypeId, itemCategory, locationDescription, isBroken, notes);
    }
}
