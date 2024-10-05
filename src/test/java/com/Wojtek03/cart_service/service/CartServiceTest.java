package com.Wojtek03.cart_service.service;

import com.Wojtek03.cart_service.client.ProductServiceClient;
import com.Wojtek03.cart_service.dto.CartDto;
import com.Wojtek03.cart_service.dto.CartItemDto;
import com.Wojtek03.cart_service.dto.ProductDto;
import com.Wojtek03.cart_service.entity.Cart;
import com.Wojtek03.cart_service.entity.CartItem;
import com.Wojtek03.cart_service.exceptions.CartNotFoundException;
import com.Wojtek03.cart_service.exceptions.ProductAlreadyInCartException;
import com.Wojtek03.cart_service.exceptions.ProductNotFound;
import com.Wojtek03.cart_service.mapper.CartItemMapper;
import com.Wojtek03.cart_service.mapper.CartMapper;
import com.Wojtek03.cart_service.repository.CartItemRepository;
import com.Wojtek03.cart_service.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CartServiceTest {

    private CartService cartService;
    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;
    private ProductServiceClient productServiceClient;
    private CartMapper cartMapper;
    private CartItemMapper cartItemMapper;

    @BeforeEach
    void setup() {
        cartRepository = Mockito.mock(CartRepository.class);
        cartItemRepository = Mockito.mock(CartItemRepository.class);
        productServiceClient = Mockito.mock(ProductServiceClient.class);
        cartMapper = Mockito.mock(CartMapper.class);
        cartItemMapper = Mockito.mock(CartItemMapper.class);
        cartService = new CartService(productServiceClient, cartRepository, cartItemRepository, cartMapper, cartItemMapper);
    }

    @Test
    void createCart_ReturnsCartDto() {
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setStatus("active");
        cart.setCreatedAt(LocalDateTime.now());
        cart.setUpdatedAt(LocalDateTime.now());

        when(cartRepository.save(any(Cart.class))).thenReturn(cart);
        when(cartMapper.toDto(cart)).thenReturn(new CartDto());

        CartDto result = cartService.createCart();

        assertNotNull(result);
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void addProductToCart_ProductNotFound_ExceptionThrown() {
        Long cartId = 1L;
        Long productId = 1L;

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(new Cart()));
        when(productServiceClient.getProductById(productId)).thenThrow(new ProductNotFound("Product not found"));

        assertThrows(ProductNotFound.class, () -> {
            cartService.addProductToCart(cartId, productId);
        });

        verify(cartRepository, times(1)).findById(cartId);
    }

    @Test
    void addProductToCart_ProductAlreadyInCart_ExceptionThrown() {
        Long cartId = 1L;
        Long productId = 1L;

        Cart cart = new Cart();
        CartItem existingItem = new CartItem();
        existingItem.setProductId(productId);
        cart.setItems(Collections.singletonList(existingItem));

        ProductDto productDto = new ProductDto();
        productDto.setId(productId);
        productDto.setPrice(BigDecimal.valueOf(100.0));

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(productServiceClient.getProductById(productId)).thenReturn(productDto);

        assertThrows(ProductAlreadyInCartException.class, () -> {
            cartService.addProductToCart(cartId, productId);
        });

        verify(cartRepository, times(1)).findById(cartId);
    }

    @Test
    void getCartItems_CartExists_ReturnsCartItemDtoList() {
        Long cartId = 1L;
        CartItem cartItem = new CartItem();
        cartItem.setProductId(1L);
        cartItem.setPrice(BigDecimal.valueOf(100.0));
        cartItem.setQuantity(1);

        when(cartItemRepository.findByCartId(cartId)).thenReturn(Collections.singletonList(cartItem));
        when(cartItemMapper.toDto(cartItem)).thenReturn(new CartItemDto());

        List<CartItemDto> result = cartService.getCartItems(cartId);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(cartItemRepository, times(1)).findByCartId(cartId);
    }

    @Test
    void getCartById_CartNotFound_ExceptionThrown() {
        Long cartId = 1L;

        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        assertThrows(CartNotFoundException.class, () -> {
            cartService.getCartById(cartId);
        });
    }

    @Test
    void getCartById_CartExists_ReturnsCartDto() {
        Long cartId = 1L;
        Cart cart = new Cart();
        cart.setId(cartId);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartMapper.toDto(cart)).thenReturn(new CartDto());

        CartDto result = cartService.getCartById(cartId);

        assertNotNull(result);
        verify(cartRepository, times(1)).findById(cartId);
    }
}