package com.Wojtek03.cart_service.exceptions;

import org.springframework.http.HttpStatus;

public class ProductNotFound extends CartServiceException{
    public ProductNotFound(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}