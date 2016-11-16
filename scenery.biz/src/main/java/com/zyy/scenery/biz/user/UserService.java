/**
 * }
 * Copyright 2016
 * Copyright 2016 linglingqi Group Holding Ltd. All Rights Reserved
 */
package com.zyy.scenery.biz.user;

import com.zyy.scenery.biz.user.dto.UserDTO;
import com.zyy.scenery.dal.user.domain.Role;

import java.util.List;

/**
 * UserService.java
 * zhangyunyun      16-11-15 */
public interface UserService {

    public UserDTO queryUserDTOByUserName(String userName);

}
