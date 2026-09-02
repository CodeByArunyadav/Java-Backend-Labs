package com.hoxcloud.ecommerce.lab_order_service.DTO;

import com.hoxcloud.ecommerce.lab_order_service.entity.OrderIteam;
import com.hoxcloud.ecommerce.lab_order_service.entity.OrderStatus;
import lombok.Data;

import java.util.List;

@Data
public class OrdersDTO {

    private Long id;
    private double total;
    private OrderStatus status;
    private List<OrderItemDTO> orderIteams;
}
