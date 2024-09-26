package com.Wojtek03.cart_service.dto;

import com.Wojtek03.cart_service.entity.CartItem;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private Long id;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
