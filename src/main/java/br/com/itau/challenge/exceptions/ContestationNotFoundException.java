package br.com.itau.challenge.exceptions;

public class ContestationNotFoundException extends RuntimeException {
    private static final long SerialVersionUID = 1L;

    public ContestationNotFoundException(String message) {
        super(message);
    }
}
