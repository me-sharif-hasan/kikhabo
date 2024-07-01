package com.iishanto.kikhabo.common.exception.global;

public class GlobalServerException extends RuntimeException {
    public GlobalServerException(String message) {
        super(message);
    }
    public GlobalServerException(){
        super("Something went wrong");
    }
}
