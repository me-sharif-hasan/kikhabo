package com.iishanto.captionmaker.common.exception;

public class NoPictureProvidedException extends IllegalArgumentException {
    public NoPictureProvidedException(String message){
        super(message);
    }
    public NoPictureProvidedException(){
        super("No picture found in the argument");
    }
}
