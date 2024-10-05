package com.Wojtek03.cart_service.fallback;

import com.Wojtek03.cart_service.client.ProductServiceClient;
import com.Wojtek03.cart_service.dto.ProductDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductServiceClientFallback implements ProductServiceClient {
    BigDecimal price = new BigDecimal(123.456);

    @Override
    public ProductDto getProductById(Long id) {
        return new ProductDto(id, "Fallback Product", price, "type");
    }
}
