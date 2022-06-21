package br.com.itau.challenge.exceptions;

public class UserAlreadyHaveCardException extends RuntimeException {
    private static final long SerialVersionUID = 1L;

    public UserAlreadyHaveCardException(String message) {
        super(message);
    }
}
