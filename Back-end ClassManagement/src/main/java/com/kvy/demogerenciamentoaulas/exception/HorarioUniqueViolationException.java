package com.kvy.demogerenciamentoaulas.exception;

public class HorarioUniqueViolationException extends RuntimeException{
    public HorarioUniqueViolationException(String message) {
        super(message);
    }
}
