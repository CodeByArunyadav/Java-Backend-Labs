package com.hoxcloud.ecommerce.lab_order_service.DTO;

import java.util.List;

public record InventoryOrderRequest(
        Long orderId,
        List<InventoryItemRequest> items
) {}
