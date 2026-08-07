package com.hoxcloud.lab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class LoginResponseToken {
    private long id;
    private String acessToken;
    private String refereshToken;

}
