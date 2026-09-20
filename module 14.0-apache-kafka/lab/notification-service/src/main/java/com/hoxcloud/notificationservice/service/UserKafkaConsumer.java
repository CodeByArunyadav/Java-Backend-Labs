package com.hoxcloud.notificationservice.service;

import com.hoxcloud.event.UserCreatNotification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserKafkaConsumer {

    @KafkaListener(topics = "user-events")
    public void handleUserEventsTopic(UserNotification  userCreatNotification) {

        log.info("handleUserEventsTopic:  {}",  userCreatNotification);
    }

    @KafkaListener(topics = "user-message-events")
    public void handleUserMessageEventsTopic(String message) {

        log.info("user-message-eventsTopic1:  {}", message);
    }

    @KafkaListener(topics = "user-delete-events")
    public void handleUserDeleteEventsTopic(UserNotification  userDeletionNotification) {

        log.info("user-Delete-eventsTopic1:  {}", userDeletionNotification);
    }

}
