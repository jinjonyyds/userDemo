package com.rothurtech.user_demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "users")
public class User {
    @Id
    private String id; // MongoDB uses String for Objectid

    @NotBlank(message = "Name is required")
    private String name;

    @Min(value = 1, message = "Age must be >= 1")
    @Max(value = 100, message = "Age must be <= 100")
    private int age;

    @PositiveOrZero(message = "Salary must be >= 0")
    private double salary;
}
