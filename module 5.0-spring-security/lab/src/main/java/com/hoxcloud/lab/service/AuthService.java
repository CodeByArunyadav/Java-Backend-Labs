package com.hoxcloud.lab.service;

import com.hoxcloud.lab.dto.LoginDto;
import com.hoxcloud.lab.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    public String login(LoginDto loginDto) {
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword()));
        UserEntity user=(UserEntity) authentication.getPrincipal();
        assert user != null;
        return jwtService.generateToken(user);
    }

}
