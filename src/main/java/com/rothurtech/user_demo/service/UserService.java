package com.rothurtech.user_demo.service;

import com.rothurtech.user_demo.entity.User;
import com.rothurtech.user_demo.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }
    public User create(User user) {
        return repo.save(user);
    }

    public List<User> getAll() {
        return repo.findAll();
    }

    public User getUserById(String id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("user not found with id " + id));
    }

    public User update(String id, User req) {
        User existing = getUserById(id);
        existing.setName(req.getName());
        existing.setAge(req.getAge());
        existing.setSalary(req.getSalary());
        return repo.save(existing);
    }
    public void delete(String id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("User not found with id " + id);

        }
        repo.deleteById(id);
    }
}
