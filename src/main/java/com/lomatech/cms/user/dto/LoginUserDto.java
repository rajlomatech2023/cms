package com.lomatech.cms.user.dto;

public class LoginUserDto {
    private String userName;
    private String password;

    public String getUsername() {
        return userName;
    }

    public LoginUserDto setUsername(String email) {
        this.userName = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginUserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "LoginUserDto{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}