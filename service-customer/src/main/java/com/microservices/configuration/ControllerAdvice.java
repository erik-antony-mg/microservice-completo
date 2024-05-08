package com.microservices.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?>validationArgument(MethodArgumentNotValidException ex){

        List<FieldError> fieldErrors=ex.getBindingResult().getFieldErrors();
        Map<String,Object> errores=new HashMap<>();
        errores.put("status", HttpStatus.BAD_REQUEST);
        for (FieldError error : fieldErrors) {
            errores.put(error.getField(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(errores,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<?>sqlValidation(SQLIntegrityConstraintViolationException ex){
        Map<String, Object> response = new HashMap<>();
        response.put("message", "error en la Base de Datos");
        response.put("error", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
