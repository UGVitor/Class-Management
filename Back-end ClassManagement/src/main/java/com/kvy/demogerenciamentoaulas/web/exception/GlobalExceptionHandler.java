package com.kvy.demogerenciamentoaulas.web.exception;

import com.kvy.demogerenciamentoaulas.exception.TurnoEntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TurnoEntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleTurnoEntityNotFoundException(
            TurnoEntityNotFoundException ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
                request,
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException(
            IllegalArgumentException ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
                request,
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorMessage> handleIllegalStateException(
            IllegalStateException ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
                request,
                HttpStatus.CONFLICT,
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }


}
