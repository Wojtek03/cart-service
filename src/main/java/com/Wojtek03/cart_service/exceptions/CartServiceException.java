package com.Wojtek03.cart_service.exceptions;

import org.springframework.http.HttpStatus;

public class CartServiceException extends RuntimeException{
    private final HttpStatus httpStatus;

    public CartServiceException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
