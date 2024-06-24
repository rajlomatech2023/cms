package com.lomatech.cms.authentication;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

public class AuthenticationService {

    private static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";
    private static final String AUTH_TOKEN = "cms-service";

    public static Authentication getAuthentication(HttpServletRequest httpServletRequest){
        String authToken = httpServletRequest.getHeader(AUTH_TOKEN_HEADER_NAME);
        if(authToken == null ||!authToken.equals(AUTH_TOKEN)){
            throw new BadCredentialsException("invalid authentication token");
        }
        return new ApiKeyAuthentication(authToken, AuthorityUtils.NO_AUTHORITIES);
    }
}
