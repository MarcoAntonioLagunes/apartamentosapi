package com.demo.apartamentosapi.exceptions;

public class ReferentialIntegrityException extends RuntimeException {
    public ReferentialIntegrityException(String message) {
        super(message);
    }
}
