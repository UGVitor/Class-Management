package com.kvy.demogerenciamentoaulas.exception;

public class TurmaUniqueViolationException extends RuntimeException{
    public TurmaUniqueViolationException(String message) {
        super(message);
    }
}
