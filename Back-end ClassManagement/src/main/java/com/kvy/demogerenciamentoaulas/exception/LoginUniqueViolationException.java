package com.kvy.demogerenciamentoaulas.exception;

public class LoginUniqueViolationException extends RuntimeException{

    public LoginUniqueViolationException(String message){
        super(message);

    }
}
