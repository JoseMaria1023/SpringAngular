package com.jve.proyecto.exception;

public class ErrorUsuarioNotFoundException extends RuntimeException {
    public ErrorUsuarioNotFoundException(String errormessage) {
        super(errormessage);
    }
}