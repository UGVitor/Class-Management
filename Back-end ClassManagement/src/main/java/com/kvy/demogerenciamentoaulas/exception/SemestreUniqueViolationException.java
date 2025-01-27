package com.kvy.demogerenciamentoaulas.exception;

public class SemestreUniqueViolationException extends RuntimeException {
    public SemestreUniqueViolationException(String message) {
        super(message);
    }
}
