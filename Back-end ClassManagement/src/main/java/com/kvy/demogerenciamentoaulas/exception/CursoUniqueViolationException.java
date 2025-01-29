package com.kvy.demogerenciamentoaulas.exception;

public class CursoUniqueViolationException extends RuntimeException{
    public CursoUniqueViolationException(String message) {
        super(message);
    }
}
