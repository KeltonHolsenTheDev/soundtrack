package soundtrack.models;

import javax.validation.constraints.*;

public class Location {

    @NotNull(message = "Id cannot be null!")
    @Min(value = 1, message = "Id cannot be less than 1")
    private int locationId;

    @NotNull(message = "Address cannot be null!")
    @NotBlank(message = "Location needs an address")
    private String address;
    
    private String name;

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
}
