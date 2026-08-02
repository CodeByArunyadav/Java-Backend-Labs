package com.hoxcloud.module10.basic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

	@Autowired
	@Qualifier("sms")
	public MessageService SMSmessageService;

	@Autowired
	@Qualifier("email")
	public MessageService EmailmessageService;

	@Test
	void contextLoads() {
	}

	@Test
	public void  sMSMessageServiceTest()
	{
		SMSmessageService.sendMessage("Hello this is first record to be sms ");
	}

	@Test
	public void emailMessageServiceTest()
	{
		EmailmessageService.sendMessage("Hello this is email message sent");
	}
}
