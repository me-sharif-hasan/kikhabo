package com.iishanto.captionmaker.common.exception;

public class NoPictureProvidedException extends IllegalArgumentException {
    public NoPictureProvidedException(){
        super("No picture found in the argument");
    }
}
