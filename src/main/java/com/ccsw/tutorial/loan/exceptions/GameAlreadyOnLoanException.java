package com.ccsw.tutorial.loan.exceptions;

public class GameAlreadyOnLoanException extends Exception {
    public GameAlreadyOnLoanException(String message) {
        super(message);
    }
}
