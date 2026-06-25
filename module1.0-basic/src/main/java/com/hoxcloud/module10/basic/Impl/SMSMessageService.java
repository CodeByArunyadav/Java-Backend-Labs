package com.hoxcloud.module10.basic.Impl;

import com.hoxcloud.module10.basic.MessageService;

public class SMSMessageService implements MessageService {

	public void sendMessage(String message) {
		System.out.println( "Sending Email message : " + message);
	}

	 
}
