package com.jeremy.advsearchemployee.exceptions;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
