package com.hoxcloud.ecommerce.lab_order_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients // 2. Enable it here
public class LabOrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LabOrderServiceApplication.class, args);
	}

}
