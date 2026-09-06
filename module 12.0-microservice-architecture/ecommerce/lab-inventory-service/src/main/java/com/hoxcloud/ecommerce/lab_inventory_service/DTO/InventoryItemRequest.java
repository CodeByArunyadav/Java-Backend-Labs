package com.hoxcloud.ecommerce.lab_inventory_service.DTO;
public record InventoryItemRequest(
        Long productId,
        Integer quantity
) {}