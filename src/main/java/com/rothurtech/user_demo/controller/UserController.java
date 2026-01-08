package com.rothurtech.user_demo.controller;

import com.rothurtech.user_demo.entity.User;
import com.rothurtech.user_demo.repository.UserRepository;
import com.rothurtech.user_demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;


    public UserController(UserService service) {
        this.service = service;
    }

    // get all users
    @GetMapping
    public List<User> getAllUsers() {
        return service.getAll();
    }

    // get by Id
    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return service.getUserById(id);
    }

    // Update
    @PutMapping("/{id}")
    public User updateUser(@PathVariable String id, @RequestBody User userDetails) {
        return service.update(id, userDetails);
    }
    // Create
    @PostMapping
    public User createUser(@RequestBody User user) {
        return service.create(user);
    }

    // Delete
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        service.delete(id);
    }
}
