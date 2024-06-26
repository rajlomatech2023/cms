package com.lomatech.cms.controller;

import com.lomatech.cms.responses.LoginResponse;
import com.lomatech.cms.service.JwtService;
import com.lomatech.cms.service.UserService;
import com.lomatech.cms.user.entity.Users;
import com.lomatech.cms.user.dto.LoginUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/addUser")
    public ResponseEntity<String> register(@RequestBody Users newUser) {
        return userService.createUser(newUser) ? ResponseEntity.ok("User registered Successfully"): ResponseEntity.ok("Failed to Register User");
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody Users user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));

        if(authentication.isAuthenticated()) {
            return ResponseEntity.ok(jwtService.generateToken(user.getUserName()));
        }else {
            return ResponseEntity.ok("Invalid user details");
        }
    }
}