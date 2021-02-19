package soundtrack.controllers;

import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import soundtrack.domain.Result;
import soundtrack.domain.ResultType;
import soundtrack.models.Event;
import soundtrack.models.Item;

import javax.validation.Valid;
import java.net.BindException;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000","http://127.0.0.1:8081"})
@RequestMapping("/api/event")
public class EventController {

    private final EventService service;

    public EventController(EventService service) { this.service = service; }

    @GetMapping
    public List<Event> findAll() { return service.findAll(); }

    @GetMapping("/{eventId}")
    public ResponseEntity<Object> findById(@PathVariable int eventId) {
        Event event = service.findById(eventId);
        if (event == null) {
            return new ResponseEntity<>("Event not found.", HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(event, HttpStatus.OK);
        }
    }

    @GetMapping("/{eventName}")
    public ResponseEntity<Object> findByName(@PathVariable String eventName) {
        Event event = service.findByName(eventName);
        if (event == null) {
            return new ResponseEntity<>("Event not found.", HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(event, HttpStatus.OK);
        }
    }

    @GetMapping("/{eventDate}")
    public ResponseEntity<Object> findByDate(@PathVariable LocalDate eventDate) {
        Event event = service.findByDate(eventDate);
        if (event == null) {
            return new ResponseEntity<>("Event not found.", HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(event, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<Object> addEvent(@RequestBody @Valid Event event, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Result<Event> eventResult = service.addEvent(event);
        if (!eventResult.isSuccess()) {
            return new ResponseEntity<>(eventResult.getMessages(), HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>(event, HttpStatus.ACCEPTED);
        }
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Object> updateEvent(@PathVariable int eventId, @RequestBody @Valid Event event, BindingResult result) {
        if (event.getEventId() != eventId) {
            return new ResponseEntity<>("Path variable " + eventId + " does not match event Id " + event.getEventId(), HttpStatus.CONFLICT);
        }
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Result<Event> eventResult = service.updateEvent(event);
        if (eventResult.getType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>("Event not found", HttpStatus.NOT_FOUND);
        }
        else if (eventResult.getType() == ResultType.INVALID) {
            return new ResponseEntity<>(eventResult.getMessages(), HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>(eventResult.getPayLoad(), HttpStatus.ACCEPTED);
        }
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Object> deleteById(@PathVariable int eventId) {
        if (service.deleteById(eventId)) {
            return new ResponseEntity<>("Deleted!", HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<>("Event not found", HttpStatus.NOT_FOUND);
        }
    }
}
