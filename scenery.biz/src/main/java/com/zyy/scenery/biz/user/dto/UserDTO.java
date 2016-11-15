/**
 * }
 * Copyright 2016
 * Copyright 2016 linglingqi Group Holding Ltd. All Rights Reserved
 */
package com.zyy.scenery.biz.user.dto;

import com.zyy.scenery.dal.user.domain.Role;
import com.zyy.scenery.dal.user.domain.User;

import java.util.List;

/**
 * UserDTO.java
 * zhangyunyun      16-11-15 */
public class UserDTO extends User {

    private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
