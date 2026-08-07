package com.hoxcloud.lab.service;

import com.hoxcloud.lab.dto.LoginDto;
import com.hoxcloud.lab.dto.LoginResponseToken;
import com.hoxcloud.lab.entity.UserEntity;
import com.hoxcloud.lab.entity.UserSession;
import com.hoxcloud.lab.repository.UserSessionRepository;
import io.jsonwebtoken.JwtException;
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
    private final UserService userService;
    private final UserSessionRepository userSessionRepository;

    public LoginResponseToken login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        UserEntity user = (UserEntity) authentication.getPrincipal();
        assert user != null;
        String refreshToken = jwtService.refreshToken(user);
        LoginResponseToken loginResponseToken = new LoginResponseToken(user.getId(), jwtService.generateToken(user), refreshToken);
        Long count = userSessionRepository.countByUserId(user.getEmail());
        if (count >= 3)
            userSessionRepository.findFirstByUserIdOrderByLastUsedAtAsc(user.getEmail()).ifPresent(userSession -> {
                userSessionRepository.delete(userSession);
            });

        userSessionRepository.save(UserSession.builder()
                .refreshToken(refreshToken)
                .userId(user.getEmail())
                .build());
        return loginResponseToken;
    }

    public LoginResponseToken refreshTokenValidate(String refreshToken) {
        String userEmail = jwtService.extractRfreshUserName(refreshToken);
        UserEntity user = userService.getUserByEmailId(userEmail);
        if (!userSessionRepository.existsByRefreshToken(refreshToken)) {
            throw new JwtException("No session found!!");
        }
        return new LoginResponseToken(user.getId(), jwtService.generateToken(user), refreshToken);

    }
}
