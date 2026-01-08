package com.rothurtech.user_demo.controller;

import com.rothurtech.user_demo.entity.User;
import com.rothurtech.user_demo.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;


    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    // get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // get by Id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{id}")
    public User updateUser(@PathVariable String id, @RequestBody User userDetails) {
        userDetails.setId(id);
        return userRepository.save(userDetails);
    }

    // Delete
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userRepository.deleteById(id);
    }
}
