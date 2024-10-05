package com.Wojtek03.cart_service.mapper;

import com.Wojtek03.cart_service.dto.CartDto;
import com.Wojtek03.cart_service.dto.CartItemDto;
import com.Wojtek03.cart_service.entity.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItemDto toDto(CartItem cartItem);
    CartItem toEntity(CartItemDto cartItemDto);
}
