package br.com.itau.challenge.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    private static final long SerialVersionUID = 1L;

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
