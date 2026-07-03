package com.hoxcloud.module10.basic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements org.springframework.boot.CommandLineRunner {

	@Autowired
	@Qualifier("emailMessageService")
	MessageService messageService;

	public static void main(String[] args) {
		System.out.println("1. Before Spring Boot");

		SpringApplication.run(Application.class, args);

		System.out.println("2. After Spring Boot");
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		messageService.sendMessage("Hello from SMS Service");
	}

}
