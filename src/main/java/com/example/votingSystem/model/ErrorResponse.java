package com.example.votingSystem.model;

import com.example.votingSystem.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
    private String errorCode;
    private String message;
    private Status status;
}
