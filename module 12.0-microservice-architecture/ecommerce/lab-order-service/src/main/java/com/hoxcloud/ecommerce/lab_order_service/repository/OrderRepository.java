package com.hoxcloud.ecommerce.lab_order_service.repository;

import com.hoxcloud.ecommerce.lab_order_service.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {
}
