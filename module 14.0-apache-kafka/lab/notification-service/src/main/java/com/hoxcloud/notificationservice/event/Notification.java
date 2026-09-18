package com.hoxcloud.notificationservice.event;

import lombok.Data;

@Data
public class Notification {

    private Long id;
    private String email;
    private String phone;
    private String status;

}
