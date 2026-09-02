package com.hoxcloud.ecommerce.lab_order_service.service;

import com.hoxcloud.ecommerce.lab_order_service.DTO.OrdersDTO;
import com.hoxcloud.ecommerce.lab_order_service.entity.Orders;
import com.hoxcloud.ecommerce.lab_order_service.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    public OrderService(OrderRepository orderRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    public OrdersDTO getOrderbyId(Long id) {
        Orders order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found: " + id));
        return modelMapper.map(order, OrdersDTO.class);
    }

    public List<OrdersDTO> getListOfOrders() {
        return orderRepository.findAll().stream().map(orders -> modelMapper.map(orders, OrdersDTO.class)).toList();
    }
}
