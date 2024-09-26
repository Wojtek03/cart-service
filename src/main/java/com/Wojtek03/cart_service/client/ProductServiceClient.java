package com.Wojtek03.cart_service.client;

import com.Wojtek03.cart_service.dto.ProductDto;
import com.Wojtek03.cart_service.fallback.ProductServiceClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", configuration = FeignClientProperties.FeignClientConfiguration.class, fallback = ProductServiceClientFallback.class)
public interface ProductServiceClient {

    @GetMapping("/products/{id}")
    ProductDto getProductById(@PathVariable Long id);
}
