package com.lomatech.cms.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.lomatech.cms.user.dto.UserDto;
import com.lomatech.cms.user.UserService;
import com.lomatech.cms.user.mapper.UserMapper;
import com.lomatech.cms.user.utils.Utils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bcryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto userDTO)
    {
        UserEntity userEntityByEmail = userRepository.findByUsername(userDTO.getUserName());
        if (userEntityByEmail != null)
        {
            throw new UsernameNotFoundException("Record already exists");
        }

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDTO, userEntity);

        String publicUserId = utils.generateUserId(20);
        userEntity.setUserId(publicUserId);
        userEntity.setPassword(bcryptPasswordEncoder.encode(userDTO.getPassword()));

        int storedUserEntity = userRepository.insert(userEntity);

        UserDto returnUserDTO = new UserDto();
        BeanUtils.copyProperties(storedUserEntity, returnUserDTO);

        return returnUserDTO;
    }

    @Override
    public UserDto getUserByUserName(String userName)
    {
        UserEntity userEntity = userRepository.findByUsername(userName);
        if (userEntity == null)
        {
            throw new UsernameNotFoundException(userName);
        }
        UserDto returnUserDTO = new UserDto();
        BeanUtils.copyProperties(userEntity, returnUserDTO);
        return returnUserDTO;
    }

    /*@Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException
    {
        UserEntity userEntity = userRepository.findByUsername(userName);
        if (userEntity == null)
        {
            throw new UsernameNotFoundException(userName);
        }
        return new Users(userEntity.getUserName(), userEntity.getPassword(), new ArrayList<>(), true);
    }*/

    @Override
    public UserDto getUserByUserId(String userId)
    {
        UserDto returnUserDTO = new UserDto();
        UserEntity userEntityByUserId = userRepository.findByUserId(userId);

        if (userEntityByUserId == null)
        {
            throw new UsernameNotFoundException(userId);
        }

        BeanUtils.copyProperties(userEntityByUserId, returnUserDTO);

        return returnUserDTO;
    }

    @Override
    public UserDto updateUser(String userId, UserDto userDTO)
    {
        UserDto returnUserDTO = new UserDto();
        UserEntity userEntityByUserId = userRepository.findByUserId(userId);

        if (userEntityByUserId == null)
        {
            throw new UsernameNotFoundException("User " + userId + " does not exist");
        }

        userEntityByUserId.setUserName(userDTO.getUserName());
        userEntityByUserId.setIsEnabled(Boolean.TRUE);
        userEntityByUserId
                .setPassword(bcryptPasswordEncoder.encode(userDTO.getPassword()));

        int updatedUserEntity = userRepository.update(new LambdaUpdateWrapper<>(userEntityByUserId));

        BeanUtils.copyProperties(updatedUserEntity, returnUserDTO);

        return returnUserDTO;
    }

    @Override
    public void deleteUser(String userId)
    {
        UserEntity userEntityByUserId = userRepository.findByUserId(userId);

        if (userEntityByUserId == null)
        {
            throw new UsernameNotFoundException("user " + userId + " does not exist");
        }
        userRepository.deleteById(userEntityByUserId);

    }

    @Override
    public List<UserDto> getUsers(int page, int limit)
    {
        List<UserDto> userDTOList = new ArrayList<UserDto>();

        if(page>0)
        {
            page = page-1;
        }

        //Pageable pageable= PageRequest.of(page, limit);
        List<UserEntity> userEntityList = userRepository.selectList(new LambdaQueryWrapper<>(UserEntity.class));

        for (UserEntity userEntity : userEntityList)
        {
            UserDto userDTO = new UserDto();
            BeanUtils.copyProperties(userEntity, userDTO);
            userDTOList.add(userDTO);
        }

        return userDTOList;
    }

}
