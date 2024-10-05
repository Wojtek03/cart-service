package com.Wojtek03.cart_service.exceptions;

import org.springframework.http.HttpStatus;

public class CartNotFoundException extends CartServiceException{
    public CartNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
