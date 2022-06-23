package br.com.itau.challenge.exceptions;

public class ForbiddenException extends RuntimeException {
    private static final long SerialVersionUID = 1L;

    public ForbiddenException(String message) {
        super(message);
    }
}
