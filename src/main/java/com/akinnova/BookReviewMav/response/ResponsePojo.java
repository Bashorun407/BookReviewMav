package com.akinnova.BookReviewMav.response;

import com.akinnova.BookReviewMav.enums.ResponseType;
import lombok.Data;

@Data
public class ResponsePojo <T>{
    boolean success;
    ResponseType responseType;
    String message;
    T data;

    //Constructor for single values and successful request
    public ResponsePojo(ResponseType responseType, String message, T data){
        this.success = true;
        this.responseType = responseType;
        this.message = message;
        this.data = data;

    }

    //Constructor for failed requests
    public ResponsePojo(ResponseType responseType, String message){
        this.success = false;
        this.responseType = responseType;
        this.message = message;
    }
}
