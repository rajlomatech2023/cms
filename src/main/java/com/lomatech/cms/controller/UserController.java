package com.lomatech.cms.controller;

import com.lomatech.cms.responses.LoginResponse;
import com.lomatech.cms.service.JwtService;
import com.lomatech.cms.service.UserService;
import com.lomatech.cms.user.entity.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService){
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/addUser")
    public ResponseEntity<String> register(@RequestBody Users newUser) {
        return Boolean.TRUE.equals(userService.createUser(newUser)) ?
                ResponseEntity.ok("User registered Successfully"): ResponseEntity.ok("Failed to Register User");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody Users user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));

        if(authentication.isAuthenticated()) {
            return ResponseEntity.ok(new LoginResponse().setToken(jwtService.generateToken(user.getUserName())));
        }else {
            return ResponseEntity.ok(new LoginResponse().setToken("Invalid user details"));
        }
    }
}