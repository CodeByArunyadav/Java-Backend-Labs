package com.hoxcloud.userservice.event;

import com.hoxcloud.userservice.entity.UserStatus;
import lombok.Data;

@Data
public class UserNotification {
    Long id;
    String email;
    String phone;
    UserStatus status;
}
