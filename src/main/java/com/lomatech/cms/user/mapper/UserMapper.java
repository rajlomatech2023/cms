package com.lomatech.cms.user.mapper;

import com.lomatech.cms.user.entity.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO users(user_name, password) VALUES(#{userName}, #{password})")
    Boolean saveUser(Users user);

    @Select("select id, user_name, password from users where user_name = #{userName}")
    Optional<Users> findByUsername(@Param("userName") String userName);

    @Select("SELECT * FROM users")
    List<Users> findAllUsers();
}
