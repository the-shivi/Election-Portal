package com.example.votingSystem.exception;

import com.example.votingSystem.enums.ErrorCodes;

public class UnAuthorisedException extends RuntimeException {
    private  String errorCode;

    public UnAuthorisedException(ErrorCodes errorCode, String message) {
        super(message);
        this.errorCode = String.valueOf(errorCode);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
