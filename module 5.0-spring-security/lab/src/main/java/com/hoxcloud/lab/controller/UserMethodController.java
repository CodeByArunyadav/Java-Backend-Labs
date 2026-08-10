package com.hoxcloud.lab.controller;

import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/api/users")
public class UserMethodController {

    @GetMapping
    public String getUsers() {
        return "User data fetched successfully";
    }

    @PostMapping
    public String createUser() {
        return "User created successfully";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        return "User with id " + id + " deleted successfully";
    }
}
