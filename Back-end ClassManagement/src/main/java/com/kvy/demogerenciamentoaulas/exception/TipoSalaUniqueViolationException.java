package com.kvy.demogerenciamentoaulas.exception;

public class TipoSalaUniqueViolationException extends RuntimeException{
    public TipoSalaUniqueViolationException(String message) {
        super(message);
    }
}
