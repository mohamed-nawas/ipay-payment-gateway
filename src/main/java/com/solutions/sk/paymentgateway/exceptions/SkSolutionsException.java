package com.solutions.sk.paymentgateway.exceptions;

public class SkSolutionsException extends RuntimeException {

    public SkSolutionsException(String message) {
        super(message);
    }

    public SkSolutionsException(String message, Throwable error) {
        super(message, error);
    }
}
