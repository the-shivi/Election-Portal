package com.example.votingSystem.exception;

import com.example.votingSystem.enums.ErrorCodes;

public class ElectionException extends RuntimeException {
    private  String errorCode;

    public ElectionException(ErrorCodes errorCode, String message) {
        super(message);
        this.errorCode = String.valueOf(errorCode);
    }

    public String getErrorCode() {
        return errorCode;
    }
}

