package com.iishanto.kikhabo.common.exception.user;

public class UserRegistrationFailureException extends RuntimeException{
    public UserRegistrationFailureException(String message){
        super("User registration failed: %s".formatted(message));
    }
}
