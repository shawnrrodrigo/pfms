package com.example.pfms.exception.custom;

public class OperationNotAllowedException extends RuntimeException{
    private final int code;

    public OperationNotAllowedException(int code) {
        this.code = code;
    }

    public OperationNotAllowedException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
