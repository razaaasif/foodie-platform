package com.foodie.userservice.exception;

/**
 * Created on 29/05/25.
 *
 * @author : aasif.raza
 */
public class DuplicateUserException extends RuntimeException{
    public DuplicateUserException(String message){
        super(message);
    }
}
