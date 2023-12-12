package com.myfood.exceptions;

public class EmailNotSentException extends RuntimeException {
    public EmailNotSentException(String msg, Throwable err) {
        super(msg, err);
    }
}
