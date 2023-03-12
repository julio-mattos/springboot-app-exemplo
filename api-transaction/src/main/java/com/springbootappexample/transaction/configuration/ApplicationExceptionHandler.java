package com.springbootappexample.transaction.configuration;

import com.springbootappexample.transaction.exception.TransactionNotFoundException;
import com.springbootappexample.transaction.openapi.model.GeneralError;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<GeneralError> handleException(TransactionNotFoundException ex){
        GeneralError generalError = new GeneralError()
                .code(HttpStatus.NOT_FOUND.value())
                .message("Transaction not found!");
        return new ResponseEntity<>(generalError, HttpStatus.valueOf(generalError.getCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GeneralError> handleException(MethodArgumentNotValidException ex){
        GeneralError generalError = new GeneralError()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage());
        return new ResponseEntity<>(generalError, HttpStatus.valueOf(generalError.getCode()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<GeneralError> handleException(HttpRequestMethodNotSupportedException ex){
        GeneralError generalError = new GeneralError()
                .code(HttpStatus.METHOD_NOT_ALLOWED.value())
                .message(ex.getMessage());
        return new ResponseEntity<>(generalError, HttpStatus.valueOf(generalError.getCode()));
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<GeneralError> handleException(DuplicateKeyException ex){
        GeneralError generalError = new GeneralError()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Your Id Already exists!");
        return new ResponseEntity<>(generalError, HttpStatus.valueOf(generalError.getCode()));
    }
}
