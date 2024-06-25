package com.lomatech.cms.user.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class UserDto implements Serializable {

    private long id;
    private String userId;
    private String userName;
    private String password;
    private Boolean isEnabled = false;
}
