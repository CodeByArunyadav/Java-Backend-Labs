package com.hoxcloud.module10.basic.Impl;

import org.springframework.stereotype.Component;

import com.hoxcloud.module10.basic.MessageService;

@Component("sms")
public class SMSMessageService implements MessageService {

	public void sendMessage(String message) {
		System.out.println( "Sending SMS message : " + message);
	}

	 
}
