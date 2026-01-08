package com.rothurtech.user_demo.repository;

import com.rothurtech.user_demo.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
