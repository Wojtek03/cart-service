package com.Wojtek03.cart_service.dto;

import com.Wojtek03.cart_service.entity.CartItem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartDto {
    private Long id;
    private String status;
    private List<CartItem> items;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
