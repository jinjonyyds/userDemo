package com.rothurtech.user_demo.service;

import com.rothurtech.user_demo.entity.User;
import com.rothurtech.user_demo.exception.UserAgeInvalidException;
import com.rothurtech.user_demo.exception.UserNotFoundException;
import com.rothurtech.user_demo.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }
    @Transactional
    public User create(User user) {
        validateAge(user.getAge());
        return repo.save(user);
    }

    public List<User> getAll() {
        return repo.findAll();
    }

    public User getUserById(String id) {
        return repo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    public User update(String id, User req) {
        validateAge(req.getAge());
        User existing = getUserById(id);
        existing.setName(req.getName());
        existing.setAge(req.getAge());
        existing.setSalary(req.getSalary());
        return repo.save(existing);
    }
    public void delete(String id) {
        if (!repo.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        repo.deleteById(id);
    }
    private void validateAge(int age) {
        if (age < 1 || age > 100) {
            throw new UserAgeInvalidException(age);
        }
    }
}
