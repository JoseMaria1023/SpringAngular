package com.jve.proyecto.exception;

public class ErrorUsuarioEncontradoException extends RuntimeException {
    public ErrorUsuarioEncontradoException(String errormessage) {
        super(errormessage);
    }
}