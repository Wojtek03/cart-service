package com.Wojtek03.cart_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.Wojtek03.cart_service.dto.CartDto;
import com.Wojtek03.cart_service.dto.CartItemDto;
import com.Wojtek03.cart_service.exceptions.CartNotFoundException;
import com.Wojtek03.cart_service.service.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createCart_ReturnsCartDto() throws Exception {
        CartDto cartDto = new CartDto();
        cartDto.setId(1L);
        cartDto.setStatus("active");

        when(cartService.createCart()).thenReturn(cartDto);

        mockMvc.perform(post("/carts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value("active"));
    }

    @Test
    public void addProductToCart_ReturnsUpdatedCartDto() throws Exception {
        CartDto updatedCart = new CartDto();
        updatedCart.setId(1L);
        updatedCart.setStatus("active");

        when(cartService.addProductToCart(1L, 1L)).thenReturn(updatedCart);

        mockMvc.perform(post("/carts/1/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value("active"));
    }

    @Test
    public void getCartItems_ReturnsListOfCartItems() throws Exception {
        CartItemDto item1 = new CartItemDto();
        item1.setProductId(1L);
        item1.setQuantity(2);

        CartItemDto item2 = new CartItemDto();
        item2.setProductId(2L);
        item2.setQuantity(1);

        List<CartItemDto> items = Arrays.asList(item1, item2);

        when(cartService.getCartItems(1L)).thenReturn(items);

        mockMvc.perform(get("/carts/1/items")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].productId").value(1L))
                .andExpect(jsonPath("$[0].quantity").value(2))
                .andExpect(jsonPath("$[1].productId").value(2L))
                .andExpect(jsonPath("$[1].quantity").value(1));
    }

    @Test
    public void getCartById_ReturnsCartDto() throws Exception {
        CartDto cartDto = new CartDto();
        cartDto.setId(1L);
        cartDto.setStatus("active");

        when(cartService.getCartById(1L)).thenReturn(cartDto);

        mockMvc.perform(get("/carts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value("active"));
    }

    @Test
    public void getCartById_CartNotFound_ReturnsNotFound() throws Exception {
        when(cartService.getCartById(999L)).thenThrow(new CartNotFoundException("Cart not found"));

        mockMvc.perform(get("/carts/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}