package com.hoxcloud.ecommerce.lab_order_service.DTO;
public record InventoryItemRequest(
        Long productId,
        Integer quantity
) {}