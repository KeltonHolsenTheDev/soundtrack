package soundtrack.models;

import javax.validation.constraints.*;
import java.util.Objects;

public class Location {
    
    private int locationId;

    @NotBlank(message = "Address cannot be null or blank!")
    private String address;

    private String name;

    public Location() {
    }

    public Location(int locationId, @NotBlank(message = "Address cannot be null or blank!") String address, String name) {
        this.locationId = locationId;
        this.address = address;
        this.name = name;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return locationId == location.locationId &&
                Objects.equals(address, location.address) &&
                Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationId, address, name);
    }

    @Override
    public String toString() {
        return "Location{" +
                "locationId=" + locationId +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
