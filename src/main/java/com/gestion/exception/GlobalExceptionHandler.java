package com.gestion.exception;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RessourceAccessDeniedException.class)
    public ResponseEntity<?> handleAccess(RessourceAccessDeniedException ex) {
        return ResponseEntity.status(403).body(Map.of(
            "error", ex.getMessage()
        ));
    }
}
