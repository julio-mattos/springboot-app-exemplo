package com.springbootappexample.transaction.exception;

public class TransactionNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public TransactionNotFoundException(){}

    public TransactionNotFoundException(String message){
        super(message);
    }

}
