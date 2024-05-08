package com.microservices.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomerNotFound extends RuntimeException{
    public CustomerNotFound() {
    }

    public CustomerNotFound(String message) {
        super(message);
    }

    public CustomerNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
