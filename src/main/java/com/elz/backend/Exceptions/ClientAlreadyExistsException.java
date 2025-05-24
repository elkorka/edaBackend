package com.elz.backend.Exceptions;

public class ClientAlreadyExistsException extends Exception {
    public ClientAlreadyExistsException(String message) {
        super(message);
    }
}
