package com.codebyarunyadav.spring_security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {

    @GetMapping
    String getDefaultPage()
    {
        return "Welcome to default page";
    }

    @GetMapping("/admin")
    String getAdminProfile()
    {
        return "Welcome to Admin Profile Page";
    }

    @GetMapping("/user")
    String getUserProfile()
    {
        return "Welcome to User Profile Page";
    }
}
