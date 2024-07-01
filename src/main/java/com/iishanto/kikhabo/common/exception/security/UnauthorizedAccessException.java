package com.iishanto.kikhabo.common.exception.security;


import org.springframework.security.core.AuthenticationException;

public class UnauthorizedAccessException extends AuthenticationException {
    public UnauthorizedAccessException(String message){
        super(message);
    }
    public UnauthorizedAccessException(){
        super("Unauthorised try to access the endpoint!");
    }
}
