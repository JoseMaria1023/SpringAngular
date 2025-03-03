package com.jve.proyecto.exception;

public class ErrorPDFException extends RuntimeException {
    public ErrorPDFException(String errormessage) {
        super(errormessage);
    }
}