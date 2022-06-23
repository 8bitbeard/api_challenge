package br.com.itau.challenge.exceptions;

public class PurchaseNotFoundException extends RuntimeException {
    private static final long SerialVersionUID = 1L;

    public PurchaseNotFoundException(String message) {
        super(message);
    }
}
