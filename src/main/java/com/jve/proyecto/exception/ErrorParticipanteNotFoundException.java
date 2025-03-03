package com.jve.proyecto.exception;

public class ErrorParticipanteNotFoundException extends RuntimeException {
    public ErrorParticipanteNotFoundException (String errormessage) {
        super(errormessage);
    }
}
