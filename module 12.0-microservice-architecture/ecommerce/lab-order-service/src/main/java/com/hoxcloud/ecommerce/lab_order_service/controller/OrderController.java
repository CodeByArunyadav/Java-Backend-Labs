package com.hoxcloud.ecommerce.lab_order_service.controller;

import com.hoxcloud.ecommerce.lab_order_service.DTO.OrdersDTO;
import com.hoxcloud.ecommerce.lab_order_service.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("helloOrder")
    public String getHelloMsgService()
    {
        return"Hello I am from order Service";
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

    @PostMapping("bookOrder")

    public String createOrder(@RequestBody OrdersDTO ordersDTO)
    {
        return orderService.createOrder(ordersDTO);
    }


    @PostMapping("cancelledOrder")

    public String cancelledOrder(@RequestBody OrdersDTO ordersDTO)
    {
        return orderService.cancelledOrder(ordersDTO);
    }

}
