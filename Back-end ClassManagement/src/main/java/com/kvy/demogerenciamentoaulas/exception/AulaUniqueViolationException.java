package com.kvy.demogerenciamentoaulas.exception;

public class AulaUniqueViolationException extends RuntimeException {
    public AulaUniqueViolationException(String message) {
        super(message);
    }
}
