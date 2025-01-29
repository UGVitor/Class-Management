package com.kvy.demogerenciamentoaulas.exception;

public class TurnoUniqueViolationException extends RuntimeException{
    public TurnoUniqueViolationException(String message) {
        super(message);
    }
}
