package com.hoxcloud.ecommerce.lab_order_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class OrderIteam {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private long ProductId;
private Integer quantity;
@ManyToOne
@JoinColumn(name = "order_id")
private Orders orders;

}
