package com.kvy.demogerenciamentoaulas.exception;

public class DiaSemanaUniqueViolationException extends RuntimeException {
    public DiaSemanaUniqueViolationException(String message) {
        super(message);
    }
}
