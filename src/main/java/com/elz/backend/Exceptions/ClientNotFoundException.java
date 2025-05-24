package com.elz.backend.Exceptions;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ClientNotFoundException extends Exception {
    public ClientNotFoundException(String message) {
        super(message);
    }
}
