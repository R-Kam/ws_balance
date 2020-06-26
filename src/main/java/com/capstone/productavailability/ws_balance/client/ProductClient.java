package com.capstone.productavailability.ws_balance.client;

import com.capstone.productavailability.domain.product_availability_domain.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Products", url = "${productService.baseUrl}")
@Service
public interface ProductClient {
    @GetMapping("/getProduct/{productId}")
    ProductDTO getProduct(@PathVariable("productId") Integer productId);
}
