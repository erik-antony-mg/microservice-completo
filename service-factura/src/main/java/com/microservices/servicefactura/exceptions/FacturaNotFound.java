package com.microservices.servicefactura.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FacturaNotFound extends RuntimeException{
    public FacturaNotFound() {
    }

    public FacturaNotFound(String message) {
        super(message);
    }
}
