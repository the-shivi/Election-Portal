package com.example.votingSystem.exception;

import com.example.votingSystem.enums.Status;
import com.example.votingSystem.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String defaultMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Validation failed");

        ErrorResponse errorResponse = new ErrorResponse("VALIDATION_ERROR",defaultMessage, Status.FAILED);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnAuthorisedException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredential(UnAuthorisedException ex) {
        ErrorResponse error = new ErrorResponse(ex.getErrorCode(), ex.getMessage(), Status.FAILED);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(ElectionException.class)
    public ResponseEntity<ErrorResponse> handleAdminElectionException(ElectionException ex) {
        ErrorResponse error = new ErrorResponse(ex.getErrorCode(), ex.getMessage(), Status.FAILED);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}

