package com.sac_gestionale.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        
        Map<String, String> errors = new HashMap<>();
        
        // Cicliamo su tutti gli errori di validazione trovati da Spring
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String nomeCampo = error.getField();
            String messaggioErrore = error.getDefaultMessage();
            errors.put(nomeCampo, messaggioErrore);
        });
        
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Map<String, String> handleIllegalArgumentException(IllegalArgumentException ex) {
        
        Map<String, String> error = new HashMap<>();
        error.put("errore", ex.getMessage());
        
        return error;
    }
}