package br.com.itau.challenge.exceptions;

public class UserNotFoundException extends RuntimeException {
    private static final long SerialVersionUID = 1L;

    public UserNotFoundException(String message) {
        super(message);
    }
}
