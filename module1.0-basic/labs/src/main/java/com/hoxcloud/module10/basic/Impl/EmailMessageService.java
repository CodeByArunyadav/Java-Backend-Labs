package com.hoxcloud.module10.basic.Impl;

import org.springframework.stereotype.Component;

import com.hoxcloud.module10.basic.MessageService;

@Component
public class EmailMessageService implements MessageService {

	public void sendMessage(String message) {
		System.out.println( "Sending Email message : " + message);
	}

}
