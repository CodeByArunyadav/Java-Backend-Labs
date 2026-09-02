package com.hoxcloud.ecommerce.lab_order_service.DTO;

import lombok.Getter;
import lombok.Setter;

    @Getter
    @Setter
    public class OrderItemDTO {

        private Long id;
        private Long productId;
        private Integer quantity;
    }

