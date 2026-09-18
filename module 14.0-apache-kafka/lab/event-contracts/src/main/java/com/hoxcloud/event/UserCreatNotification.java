package com.hoxcloud.event;

import lombok.Data;

@Data
public class UserCreatNotification {

    private Long id;
    private String email;
    private String phone;
    private String status;

}
