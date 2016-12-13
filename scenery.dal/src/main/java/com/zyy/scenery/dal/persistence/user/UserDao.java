/**
 * }
 * Copyright 2016
 * Copyright 2016 linglingqi Group Holding Ltd. All Rights Reserved
 */
package com.zyy.scenery.dal.persistence.user;

import com.zyy.scenery.dal.domain.user.Role;
import com.zyy.scenery.dal.domain.user.User;

import java.util.List;

/**
 * UserDao.java
 * zhangyunyun      16-12-13 */
public interface UserDao {
    User queryUserByUserName(String userName);

    List<Role> queryRolesByUserId(Long userId);
}
