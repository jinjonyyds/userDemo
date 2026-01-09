package com.rothurtech.user_demo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiError {
    private Instant timeStamp;
    private HttpStatus status;
    private String message;
}
