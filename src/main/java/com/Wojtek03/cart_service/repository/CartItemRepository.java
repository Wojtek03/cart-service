package com.Wojtek03.cart_service.repository;

import com.Wojtek03.cart_service.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{
    List<CartItem> findByCartId(Long cartId);
}
