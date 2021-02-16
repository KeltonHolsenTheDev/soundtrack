package soundtrack.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import soundtrack.domain.LocationService;

@RestController
public class LocationController {
    private final LocationService service;

    public LocationController(LocationService service) {
        this.service = service;
    }
}
