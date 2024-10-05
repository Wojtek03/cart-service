package com.Wojtek03.cart_service.mapper;

import com.Wojtek03.cart_service.dto.CartDto;
import com.Wojtek03.cart_service.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    CartDto toDto(Cart cart);
    Cart toEntity(CartDto cartDto);
}
