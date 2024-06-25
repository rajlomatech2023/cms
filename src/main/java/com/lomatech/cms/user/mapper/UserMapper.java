package com.lomatech.cms.user.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends SuperMapper<UserEntity> {

    UserEntity findByUserId(String userId);
    UserEntity findByUsername(String username);
}
