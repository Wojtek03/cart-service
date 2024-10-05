package com.Wojtek03.cart_service.exceptions.handler;

import com.Wojtek03.cart_service.exceptions.CartNotFoundException;
import com.Wojtek03.cart_service.exceptions.ProductAlreadyInCartException;
import com.Wojtek03.cart_service.exceptions.ProductNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFound.class)
    public ResponseEntity<String> handleProductNotFound(ProductNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<String> handleCartNotFound(CartNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ProductAlreadyInCartException.class)
    public ResponseEntity<String> handleProductAlreadyInCart(ProductAlreadyInCartException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + ex.getMessage());
    }
}