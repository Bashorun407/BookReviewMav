package com.akinnova.BookReviewMav.exception;

public class ApiException extends RuntimeException{
    public ApiException(String message){
        super(message);
    }
}
