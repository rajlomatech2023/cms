package com.lomatech.cms.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("users")
public class Users {
    private Long id;
    private String userName;
    private String password;

}
