package com.hoxcloud.ecommerce.lab_order_service.controller;

import com.hoxcloud.ecommerce.lab_order_service.DTO.OrdersDTO;
import com.hoxcloud.ecommerce.lab_order_service.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(path = "{id}")

    public OrdersDTO getOrderById(@PathVariable Long id)
    {
        return orderService.getOrderbyId(id);
    }

    @GetMapping()

    public List<OrdersDTO> getListOfOrders()
    {
        return orderService.getListOfOrders();
    }

}
