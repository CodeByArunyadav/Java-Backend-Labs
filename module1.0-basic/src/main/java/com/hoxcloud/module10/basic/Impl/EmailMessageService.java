package com.hoxcloud.module10.basic.Impl;

import com.hoxcloud.module10.basic.MessageService;

public class EmailMessageService implements MessageService {

	public void sendMessage(String message) {
		System.out.println( "Sending Email message : " + message);
	}

}
