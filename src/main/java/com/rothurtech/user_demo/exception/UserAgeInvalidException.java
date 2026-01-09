package com.rothurtech.user_demo.exception;

public class UserAgeInvalidException extends RuntimeException{
    public UserAgeInvalidException(int age) {
        super("Invalid age: " + age + ". Age must be between 1 and 100");
    }
}
