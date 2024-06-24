package com.lomatech.cms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BasicController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello Api";
    }

    @GetMapping("/public")
    public String publicApi() {
        return "Public Api";
    }

    @GetMapping("/user")
    public String userApi() {
        return "User Api";
    }

    @GetMapping("/admin")
    public String adminApi() {
        return "Admin Api";
    }
}
