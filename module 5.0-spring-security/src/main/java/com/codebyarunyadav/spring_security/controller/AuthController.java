package com.codebyarunyadav.spring_security.controller;

import com.codebyarunyadav.spring_security.dto.LoginDto;
import com.codebyarunyadav.spring_security.dto.SignUpDto;
import com.codebyarunyadav.spring_security.dto.UserDto;
import com.codebyarunyadav.spring_security.service.AuthService;
import com.codebyarunyadav.spring_security.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
       String  userToken=authService.login(loginDto);
        Cookie cookie= new Cookie("accessToken",userToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(5*60);
        response.addCookie(cookie);
        return ResponseEntity.ok(userToken);
    }

}
