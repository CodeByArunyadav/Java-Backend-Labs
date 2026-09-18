package com.hoxcloud.notificationservice.service;

import com.hoxcloud.event.UserCreatNotification;
import com.hoxcloud.notificationservice.event.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserKafkaConsumer {

    @KafkaListener(topics = "user-events")
    public void handleUserEventsTopic(UserCreatNotification  userCreatNotification) {

        log.info("handleUserEventsTopic:  {}",  userCreatNotification);
    }

    @KafkaListener(topics = "user-message-events")
    public void handleUserMessageEventsTopic(String message) {

        log.info("user-message-eventsTopic1:  {}", message);
    }

}
