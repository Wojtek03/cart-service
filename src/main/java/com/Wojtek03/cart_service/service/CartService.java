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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductServiceClient productServiceClient;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;

    public CartDto createCart() {
        Cart cart = new Cart();
        cart.setStatus("active");
        cart.setCreatedAt(LocalDateTime.now());
        cart.setUpdatedAt(LocalDateTime.now());
        Cart savedCart = cartRepository.save(cart);
        return cartMapper.toDto(savedCart);
    }

    public CartDto addProductToCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with ID " + cartId));

        ProductDto product;
        try {
            product = productServiceClient.getProductById(productId);
        } catch (Exception e) {
            throw new ProductNotFound("Product not found with ID " + productId);
        }

        cart.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    throw new ProductAlreadyInCartException("Product with ID " + productId + " is already in the cart");
                });

        CartItem cartItem = new CartItem();
        cartItem.setProductId(product.getId());
        cartItem.setPrice(product.getPrice());
        cartItem.setQuantity(1);
        cartItem.setCart(cart);

        cartItemRepository.save(cartItem);

        cart.setUpdatedAt(LocalDateTime.now());
        Cart updatedCart = cartRepository.save(cart);

        return cartMapper.toDto(updatedCart);
    }

    public List<CartItemDto> getCartItems(Long cartId) {
        return cartItemRepository.findByCartId(cartId)
                .stream()
                .map(cartItemMapper::toDto)
                .collect(Collectors.toList());
    }

    public CartDto getCartById(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + cartId));

        return cartMapper.toDto(cart);
    }
}