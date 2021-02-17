package soundtrack.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import soundtrack.domain.Result;
import soundtrack.domain.ResultType;
import soundtrack.domain.UserService;

import soundtrack.models.User;

import javax.validation.Valid;
import java.util.List;

/**
 * Warning: this class is not guaranteed to control your users.
 */
@RestController
@CrossOrigin(origins = {"http://localhost:3000","http://127.0.0.1:8081"})
@RequestMapping("/api/user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> findAll() {
        return service.findAll();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> findUserById(@PathVariable int userId) {
        User user = service.findById(userId);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<Object> addUser(@RequestBody @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors().toString());
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Result<User> userResult = service.add(user);
        if (!userResult.isSuccess()) {
            return new ResponseEntity<>(userResult.getMessages(), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable int userId, @RequestBody @Valid User user, BindingResult result) {
        if (userId != user.getUserId()) {
            return new ResponseEntity<>("User IDs must match", HttpStatus.CONFLICT);
        }
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Result<User> userResult = service.update(user);
        if (userResult.getType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } else if (userResult.getType() == ResultType.INVALID) {
            return new ResponseEntity<>(userResult.getMessages(), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable int userId) {
        if (service.deleteById(userId)) {
            return new ResponseEntity<>("Deleted", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

}
