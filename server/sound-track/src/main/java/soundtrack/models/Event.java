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
    private User owner;

    @NotEmpty(message = "Events must have at least one staff member!")
    private Map<User, List<String>> staffAndRoles = new HashMap<User, List<String>>();

    @NotNull(message = "Events must have a location!")
    private Location location;

    private List<Item> equipment = new ArrayList<>();

}
