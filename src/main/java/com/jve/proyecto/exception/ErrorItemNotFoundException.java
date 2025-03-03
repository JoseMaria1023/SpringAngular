package com.jve.proyecto.exception;

public class ErrorItemNotFoundException extends RuntimeException {
    public ErrorItemNotFoundException (String errormessage) {
        super(errormessage);
    }
}
