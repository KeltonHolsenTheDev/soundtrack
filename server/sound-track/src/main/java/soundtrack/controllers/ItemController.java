package soundtrack.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import soundtrack.domain.ItemService;
import soundtrack.domain.Result;
import soundtrack.domain.ResultType;
import soundtrack.models.Item;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000","http://127.0.0.1:8081"})
@RequestMapping("/api/item")
public class ItemController {

    private final ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }

    @GetMapping
    public List<Item> findAll() {
        return service.findAll();
    }

    @GetMapping("/{itemId")
    public ResponseEntity<Object> findById(@PathVariable int itemId) {
        Item item = service.findById(itemId);
        if (item == null) {
            return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(item, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<Object> addItem(@RequestBody @Valid Item item, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Result<Item> itemResult = service.addItem(item);
        if (!itemResult.isSuccess()) {
            return new ResponseEntity<>(itemResult.getMessages(), HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>(item, HttpStatus.ACCEPTED);
        }
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<Object> updateItem(@PathVariable int itemId, @RequestBody @Valid Item item, BindingResult result) {
        if (item.getItemId() != itemId) {
            return new ResponseEntity<>("Path variable " + itemId + " does not match item Id " + item.getItemId(), HttpStatus.CONFLICT);
        }
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Result<Item> itemResult = service.updateItem(item);
        if (itemResult.getType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
        }
        else if (itemResult.getType() == ResultType.INVALID) {
            return new ResponseEntity<>(itemResult.getMessages(), HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>(itemResult.getPayLoad(), HttpStatus.ACCEPTED);
        }
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Object> deleteById(@PathVariable int itemId) {
        if (service.deleteById(itemId)) {
            return new ResponseEntity<>("Deleted!", HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
        }
    }

}
