package com.kvy.demogerenciamentoaulas.exception;

public class PerfilUniqueViolationException extends RuntimeException{
    public PerfilUniqueViolationException(String message) {
        super(message);
    }
}
