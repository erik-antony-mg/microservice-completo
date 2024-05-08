package com.microservices.serviceauth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exceptionLogin(Exception ex){
        Map<String,Object> error=new HashMap<>();
        if (ex instanceof BadCredentialsException){
            error.put("status", HttpStatus.UNAUTHORIZED.value());
            error.put("error",ex.getMessage());
            error.put("custom message","verifique su email o contraseña ..!");
            return  new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
        }
        if (ex instanceof AccessDeniedException) {
            error.put("status", HttpStatus.FORBIDDEN);
            error.put("error",ex.getMessage());
            error.put("custom message","acceso denegado");
            return  new ResponseEntity<>(error,HttpStatus.FORBIDDEN);
        }
        if (ex instanceof JwtExceptionCustom){
            error.put("status", HttpStatus.FORBIDDEN);
            error.put("error",ex.getMessage());
            return  new ResponseEntity<>(error,HttpStatus.FORBIDDEN);
        }
        return null;
    }

}
