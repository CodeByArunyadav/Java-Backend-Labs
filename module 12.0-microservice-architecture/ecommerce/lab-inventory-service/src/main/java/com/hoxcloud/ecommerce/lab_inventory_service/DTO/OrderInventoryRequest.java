package com.hoxcloud.ecommerce.lab_inventory_service.DTO;

import java.util.List;
    public record OrderInventoryRequest(
            Long orderId,
            List<InventoryItemRequest> items
    ) {}

