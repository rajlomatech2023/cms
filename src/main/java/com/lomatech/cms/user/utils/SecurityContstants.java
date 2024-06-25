package com.lomatech.cms.user.utils;

import org.springframework.beans.factory.annotation.Autowired;

public class SecurityContstants {

    public static final long EXPIRATION_TIME=864000000; //10 Days
    public static final String TOKEN_PREFIX="Bearer ";
    public static final String HEADER_STRING="Authorization";
    public static final String SIGNUP_URL="/users";

    @Autowired
    private AppProperties appProperties;

    public String getTokenSecret()
    {
        return appProperties.getToken();
    }
}
