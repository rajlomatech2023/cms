package com.lomatech.cms.user;

import com.lomatech.cms.user.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {

    UserDto getUserByUserName(String userName);
    UserDto createUser(UserDto userDTO);
    UserDto getUserByUserId(String userId);
    UserDto updateUser(String userId, UserDto userDTO);
    void deleteUser(String userId);
    List<UserDto> getUsers(int page, int limit);
}
