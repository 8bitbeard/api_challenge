package br.com.itau.challenge.exceptions;

public class UserDoesNotHaveCardException extends RuntimeException {
    private static final long SerialVersionUID = 1L;

    public UserDoesNotHaveCardException(String message) {
        super(message);
    }
}