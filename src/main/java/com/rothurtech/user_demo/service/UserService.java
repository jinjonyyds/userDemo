package com.rothurtech.user_demo.service;

import com.rothurtech.user_demo.entity.User;
import com.rothurtech.user_demo.exception.UserAgeInvalidException;
import com.rothurtech.user_demo.exception.UserNotFoundException;
import com.rothurtech.user_demo.repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @CacheEvict(value = "users", allEntries = true) //create data then remove all original data from cache
    public User create(User user) {
        validateAge(user.getAge());
        return repo.save(user);
    }

    public List<User> getAll() {
        return repo.findAll();
    }

    @Cacheable(value = "users", key = "#id") //search and cache date
    public User getUserById(String id) {
        System.out.println("Getting " + id + " from database");
        return repo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    @CachePut(value = "users", key = "#id") // update database and update cache
    public User update(String id, User req) {
        validateAge(req.getAge());
        User existing = getUserById(id);
        existing.setName(req.getName());
        existing.setAge(req.getAge());
        existing.setSalary(req.getSalary());
        return repo.save(existing);
    }
    @CacheEvict(value = "user", key = "#id") //delete database and delete cache
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

    @Async("taskExecutor")
    public CompletableFuture<String> processUser(String userId) throws InterruptedException {
        System.out.println("Start processing user " + userId + " in thread " + Thread.currentThread().getName());
        Thread.sleep(3000);
        System.out.println("Finished processing user " + userId);
        return CompletableFuture.completedFuture("User " + userId + " processed");
    }
}
