package com.lomatech.cms.authentication;

import com.lomatech.cms.user.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;
import java.util.stream.Collectors;

public class CustomUserDetailsService implements UserDetailsService {

    private static final Map<String, Users> users = new HashMap<>();

    public CustomUserDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder){
        users.put("USER", createUserDetails("cmsuser", bCryptPasswordEncoder.encode("cmsuser"), true, "USER"));
        users.put("ADMIN", createUserDetails("cmsadmin", bCryptPasswordEncoder.encode("cmsadmin"), true, "ADMIN"));
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(users.get(username))
                .orElseThrow(()-> new UsernameNotFoundException("user "+username+" does not exists"));
    }

    private Users createUserDetails(String userName, String password, boolean isEnabled, String...roles) {
        return Users.userDetailsBuilder()
                .withUserName(userName)
                .withPassword(password)
                .withGrantedAuthorities(Arrays.stream(roles).map(SimpleGrantedAuthority::new).collect(Collectors.toList()))
                .withAccountNonExpired(isEnabled);
    }
}
