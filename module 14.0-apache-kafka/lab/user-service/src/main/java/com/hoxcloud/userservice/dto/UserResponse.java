package com.hoxcloud.userservice.dto;

import com.hoxcloud.userservice.entity.UserStatus;

public record UserResponse(
        Long id,
        String name,
        String email,
        String phone,
        UserStatus status
) {
}
