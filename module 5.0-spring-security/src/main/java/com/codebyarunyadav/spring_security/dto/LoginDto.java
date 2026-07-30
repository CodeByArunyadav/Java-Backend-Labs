package com.codebyarunyadav.spring_security.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String email;
    private String password;
}
