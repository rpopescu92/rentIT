package com.rentIT.exception;

public class UserExistsException extends RuntimeException {

    public UserExistsException(String message) {
        super(message);
    }
}
