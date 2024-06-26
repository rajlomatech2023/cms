package com.lomatech.cms.service;

import com.lomatech.cms.user.entity.Users;
import com.lomatech.cms.user.mapper.UserMapper;
import com.lomatech.cms.user.model.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserMapper userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> retrievedUser = userRepository.findByUsername(username);

        return retrievedUser.map(UserPrinciple::new)
                .orElseThrow(()->new UsernameNotFoundException("user not found "+username));
    }

    public Boolean createUser(Users user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.saveUser(user);
    }
}