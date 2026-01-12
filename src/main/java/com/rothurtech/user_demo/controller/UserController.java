package com.rothurtech.user_demo.controller;

import com.rothurtech.user_demo.entity.User;
import com.rothurtech.user_demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // Get all users with optional sorting
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false, defaultValue = "salary") String sort) {
        List<User> users = service.getAll();
        if (sort.equalsIgnoreCase("salary")) {
            Collections.sort(users, (u1, u2) -> Double.compare(u1.getSalary(), u2.getSalary()));
        } else if (sort.equalsIgnoreCase("age")) {
            Collections.sort(users, (u1, u2) -> Integer.compare(u1.getAge(), u2.getAge()));
        }
        return ResponseEntity.ok(users);
    }

    //Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        User user = service.getUserById(id);
        return ResponseEntity.ok(user);
    }

    //Create
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User created = service.create(user);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    //Update
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @Valid @RequestBody User userDetails) {
        User updated = service.update(id, userDetails);
        return ResponseEntity.ok(updated);
    }

    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/async-test/{id}")
    public CompletableFuture<String> asyncTest(@PathVariable String id) throws InterruptedException {
        return service.processUser(id);
    }



}
