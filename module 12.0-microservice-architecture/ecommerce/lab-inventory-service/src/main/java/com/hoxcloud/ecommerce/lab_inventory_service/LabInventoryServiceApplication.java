package com.hoxcloud.ecommerce.lab_inventory_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients // 2. Enable it here
public class LabInventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LabInventoryServiceApplication.class, args);
	}

}
