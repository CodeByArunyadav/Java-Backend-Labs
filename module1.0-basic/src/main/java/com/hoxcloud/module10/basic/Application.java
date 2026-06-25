package com.hoxcloud.module10.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hoxcloud.module10.basic.Impl.EmailMessageService;
import com.hoxcloud.module10.basic.Impl.SMSMessageService;

@SpringBootApplication
public class Application {
	
	
	public static void main(String[] args) {
		System.out.println("1. Before Spring Boot");
		
		SpringApplication.run(Application.class, args);
		
	    System.out.println("2. After Spring Boot");
	
	MessageService messageService = new SMSMessageService();
	MessageService messageServiceemail = new EmailMessageService();
    messageService.sendMessage("Hello from SMS Service");
	messageServiceemail.sendMessage("Hello this message for email");
	}

	
}
