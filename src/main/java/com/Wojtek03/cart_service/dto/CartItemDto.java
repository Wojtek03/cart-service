package com.Wojtek03.cart_service.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private Long id;
    @Column(name = "product_id", nullable = false)
    private Long productId;
    private int quantity;
    private BigDecimal price;
}
