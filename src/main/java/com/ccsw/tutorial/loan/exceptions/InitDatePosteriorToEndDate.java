package com.ccsw.tutorial.loan.exceptions;

public class InitDatePosteriorToEndDate extends Exception {

    private static final long serialVersionUID = 1L;

    public InitDatePosteriorToEndDate(String message) {
        super(message);
    }
}
