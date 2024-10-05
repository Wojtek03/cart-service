package com.Wojtek03.cart_service.exceptions;

import org.springframework.http.HttpStatus;

public class ProductAlreadyInCartException extends CartServiceException{
    public ProductAlreadyInCartException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
