package com.myfood.exceptions;

public class CreateTokenException extends RuntimeException {
    public CreateTokenException(String msg, Throwable err) {
        super(msg, err);
    }
}
