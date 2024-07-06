package com.iishanto.kikhabo.common.exception;

public class NoPictureProvidedException extends IllegalArgumentException {
    public NoPictureProvidedException(){
        super("No picture found in the argument");
    }
}
