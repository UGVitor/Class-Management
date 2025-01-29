package com.kvy.demogerenciamentoaulas.exception;


public class SalaUniqueViolationException extends RuntimeException{
    public SalaUniqueViolationException(String message) {
        super(message);
    }
}
