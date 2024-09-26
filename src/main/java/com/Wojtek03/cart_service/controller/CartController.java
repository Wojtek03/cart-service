package com.Wojtek03.cart_service.controller;

import com.Wojtek03.cart_service.dto.CartDto;
import com.Wojtek03.cart_service.dto.CartItemDto;
import com.Wojtek03.cart_service.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> createCart() {
        CartDto cartDto = cartService.createCart();
        return new ResponseEntity<>(cartDto, HttpStatus.CREATED);
    }

    @PostMapping("/{cartId}/products/{productId}")
    public ResponseEntity<CartDto> addProductToCart(@PathVariable("cartId") Long cartId,
                                                    @PathVariable("productId") Long productId) {
        CartDto updatedCart = cartService.addProductToCart(cartId, productId);
        return new ResponseEntity<>(updatedCart, HttpStatus.OK);
    }

    @GetMapping("/{cartId}/items")
    public ResponseEntity<List<CartItemDto>> getCartItems(@PathVariable("cartId") Long cartId) {
        List<CartItemDto> cartItems = cartService.getCartItems(cartId);
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }
}