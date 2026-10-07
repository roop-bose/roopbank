package com.rooptech.bankingapp.account.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String resource,String fieldName,String fieldValue) {
        super(String.format("%s  not found with given  %s : %s",resource,
                fieldName,fieldValue));
    }
}
