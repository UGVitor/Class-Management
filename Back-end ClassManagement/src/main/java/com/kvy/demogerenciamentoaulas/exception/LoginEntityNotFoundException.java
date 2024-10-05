package com.kvy.demogerenciamentoaulas.exception;

public class LoginEntityNotFoundException extends RuntimeException {
    public LoginEntityNotFoundException(String message) {
        super(message);
    }
}
