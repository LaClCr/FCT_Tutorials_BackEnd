package com.ccsw.tutorial.loan.exceptions;

public class ClientWithActiveLoanException extends Exception {

    public ClientWithActiveLoanException(String message) {
        super(message);
    }
}
