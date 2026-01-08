package com.rothurtech.user_demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "users")
public class User {
    @Id
    private String id; // MongoDB uses String for Objectid
    private String name;
    private int age;
    private double salary;
}
