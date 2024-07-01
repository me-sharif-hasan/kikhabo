package com.iishanto.kikhabo.common.exception.user;

public class UserLoginFailureException extends RuntimeException{
    public UserLoginFailureException(){
        super("User login failed. Please check your credentials");
    }
    public UserLoginFailureException(String message){
        super(message);
    }
}
