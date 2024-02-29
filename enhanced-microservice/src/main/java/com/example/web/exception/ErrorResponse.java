package com.example.web.exception;

import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ErrorResponse implements Serializable {

    private String message;

    private HttpStatus statusCode;

    private LocalDateTime timestamp;

    private String path;

    public ErrorResponse(String message, HttpStatus statusCode, LocalDateTime timestamp, String path) {
        this.message = message;
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }
}
