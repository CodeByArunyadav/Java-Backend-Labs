package com.hoxcloud.ecommerce.lab_order_service.service;

import com.hoxcloud.ecommerce.lab_order_service.DTO.InventoryItemRequest;
import com.hoxcloud.ecommerce.lab_order_service.DTO.InventoryOrderRequest;
import com.hoxcloud.ecommerce.lab_order_service.DTO.OrdersDTO;
import com.hoxcloud.ecommerce.lab_order_service.client.InventoryClient;
import com.hoxcloud.ecommerce.lab_order_service.entity.Orders;
import com.hoxcloud.ecommerce.lab_order_service.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final InventoryClient inventoryClient;

    public OrderService(OrderRepository orderRepository, ModelMapper modelMapper, InventoryClient inventoryClient) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.inventoryClient = inventoryClient;
    }

    public OrdersDTO getOrderbyId(Long id) {
        Orders order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found: " + id));
        return modelMapper.map(order, OrdersDTO.class);
    }

    public List<OrdersDTO> getListOfOrders() {
        return orderRepository.findAll().stream().map(orders -> modelMapper.map(orders, OrdersDTO.class)).toList();
    }

    public String createOrder(OrdersDTO ordersDTO) {
        InventoryOrderRequest request =
                new InventoryOrderRequest(
                        null,
                        ordersDTO.getOrderIteams()
                                .stream()
                                .map(item -> new InventoryItemRequest(
                                        item.getProductId(),
                                        item.getQuantity()
                                ))
                                .toList()
                );

        // First check/reserve inventory
        inventoryClient.reserveStock(request);

        // Then create order
        Orders orders = modelMapper.map(ordersDTO, Orders.class);

        orderRepository.save(orders);

        return "Order created successfully";
    }


    public String cancelledOrder(OrdersDTO ordersDTO) {
        InventoryOrderRequest request =
                new InventoryOrderRequest(
                        null,
                        ordersDTO.getOrderIteams()
                                .stream()
                                .map(item -> new InventoryItemRequest(
                                        item.getProductId(),
                                        item.getQuantity()
                                ))
                                .toList()
                );

        // First check/reserve inventory
        inventoryClient.releaseStock(request);

        // Then create order
        Orders orders = modelMapper.map(ordersDTO, Orders.class);

        orderRepository.save(orders);

        return "Order created successfully";
    }
}
