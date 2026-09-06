package com.hoxcloud.ecommerce.lab_order_service.client;

import com.hoxcloud.ecommerce.lab_order_service.DTO.InventoryOrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "lab-inventory-service")
public interface InventoryClient {

    @PostMapping("/inventory/products/reserve")
    String reserveStock(@RequestBody InventoryOrderRequest inventoryOrderRequest);

    @PostMapping("/inventory/products/release")
    String releaseStock(@RequestBody InventoryOrderRequest inventoryOrderRequest);

}
