package com.lomatech.cms.user.model;

import lombok.Data;

@Data
public class UserLoginRequestModel {

    private String userName;
    private String password;
}
