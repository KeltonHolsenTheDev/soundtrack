package soundtrack.controllers;

import org.springframework.stereotype.Controller;
import soundtrack.domain.LocationService;

@Controller
public class LocationController {
    private final LocationService service;

    public LocationController(LocationService service) {
        this.service = service;
    }
}
