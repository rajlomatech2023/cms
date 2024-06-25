package com.lomatech.cms.authentication;

import com.lomatech.cms.user.Users;
import com.lomatech.cms.user.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;
import java.util.stream.Collectors;

public class CustomUserDetailsService implements UserDetailsService {

    @Resource
    private UserMapper userRepository;

    private static final Map<String, Users> users = new HashMap<>();

    public CustomUserDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder){
        users.put("USER", createUserDetails("cmsuser", bCryptPasswordEncoder.encode("cmsuser"), true, "USER"));
        users.put("ADMIN", createUserDetails("cmsadmin", bCryptPasswordEncoder.encode("cmsadmin"), true, "ADMIN"));
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);

        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getPassword() {
                return user.getPassword();
            }

            @Override
            public String getUsername() {
                return user.getUserName();
            }
        };

       // return Optional.ofNullable(userDetails)
       //         .orElseThrow(()-> new UsernameNotFoundException("user "+username+" does not exists"));
    }

    private Users createUserDetails(String userName, String password, boolean isEnabled, String...roles) {
        return new Users(userName,
                password,
                Arrays.stream(roles).map(SimpleGrantedAuthority::new).collect(Collectors.toList()),
                isEnabled);
    }
}
