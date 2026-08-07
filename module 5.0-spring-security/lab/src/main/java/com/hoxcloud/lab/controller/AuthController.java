package com.hoxcloud.lab.controller;
import com.hoxcloud.lab.dto.*;
import com.hoxcloud.lab.service.AuthService;
import com.hoxcloud.lab.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController()
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto userSignUp) {
        UserDto userDto=userService.singUp(userSignUp);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        LoginResponseToken loginRefreshToken =authService.login(loginDto);
        Cookie cookie= new Cookie("refreshToken",loginRefreshToken.getRefereshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
       // cookie.setMaxAge(5*60);
        response.addCookie(cookie);
        return ResponseEntity.ok(loginRefreshToken.getAcessToken());
    }

    @PostMapping("/refresh")
    public ResponseEntity<String>refreshToken(HttpServletRequest request)
    {

        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthorizationServiceException("Refresh token not found"));
            LoginResponseToken loginResponseToken=authService.refreshTokenValidate(refreshToken);
        return ResponseEntity.ok().body(loginResponseToken.getAcessToken());
    }
}
