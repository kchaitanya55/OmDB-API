package com.omdb.api.exception.util;

public class UnAuthorizedBasicUser extends Exception{
    public UnAuthorizedBasicUser(String message){
        super(message);
    }
}
