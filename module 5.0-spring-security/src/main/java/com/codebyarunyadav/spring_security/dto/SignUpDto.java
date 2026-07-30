package com.codebyarunyadav.spring_security.dto;

import lombok.Data;

@Data
public class SignUpDto {

    private String username;
    private String email;
    private String password;
    private String role;
}
