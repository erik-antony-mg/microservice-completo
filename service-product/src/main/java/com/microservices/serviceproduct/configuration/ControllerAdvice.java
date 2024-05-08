package com.microservices.serviceproduct.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> method(MethodArgumentNotValidException ex){

        List<FieldError> fieldErrors=ex.getBindingResult().getFieldErrors();
        Map<String,Object> errores=new HashMap<>();
        errores.put("status", HttpStatus.BAD_REQUEST);
        for (FieldError error : fieldErrors) {
            errores.put(error.getField(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(errores,HttpStatus.BAD_REQUEST);
    }

}
