package com.hoxcloud.userservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public NewTopic userEventsTopic() {
        return new NewTopic(
                "user-events",    // Topic name
                3,                     // Number of partitions
                (short) 1             // Replication factor
        );
    }

    @Bean
    public NewTopic createTopic()
    {
        return new NewTopic("user-message-events",    // Topic name
                3,                     // Number of partitions
                (short) 1 );
    }

    @Bean
    public NewTopic userDeleteEventsTopic() {
        return new NewTopic(
                "user-delete-events",    // Topic name
                3,                     // Number of partitions
                (short) 1             // Replication factor
        );
    }


}