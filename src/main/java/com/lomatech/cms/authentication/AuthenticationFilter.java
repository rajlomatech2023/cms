package com.lomatech.cms.authentication;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lomatech.cms.user.UserService;
import com.lomatech.cms.user.dto.UserDto;
import com.lomatech.cms.user.model.UserLoginRequestModel;
import com.lomatech.cms.user.utils.AppProperties;
import com.lomatech.cms.user.utils.SecurityContstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private UserService userService;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try{
            UserLoginRequestModel userLoginRequestModel = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequestModel.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequestModel.getPassword(), userLoginRequestModel.getPassword(),
                    new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws
            IOException, ServletException {
        String userName = ((User)authResult.getPrincipal()).getUsername();
        String token = Jwts.builder().setSubject(userName).
                setExpiration(new Date(System.currentTimeMillis() + SecurityContstants.EXPIRATION_TIME))
                        .signWith(SignatureAlgorithm.HS512, appProperties.getToken()).compact();

        UserDto userDto = userService.getUserByUserName(userName);

        response.addHeader(SecurityContstants.HEADER_STRING, SecurityContstants.TOKEN_PREFIX + token);
        response.addHeader("UserId", userDto.getUserId());
    }
}
