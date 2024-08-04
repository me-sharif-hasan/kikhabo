package com.iishanto.kikhabo.common.exception.family;

public class FamilyMemberCreationException extends Exception{
    public FamilyMemberCreationException() {
        super("Family member creation failed");
    }
    public FamilyMemberCreationException(String message) {
        super(message);
    }
}
