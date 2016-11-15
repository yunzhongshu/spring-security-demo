/**
 * }
 * Copyright 2016
 * Copyright 2016 linglingqi Group Holding Ltd. All Rights Reserved
 */
package com.zyy.scenery.dal.user;

import com.zyy.scenery.dal.user.domain.Role;
import com.zyy.scenery.dal.user.domain.User;

import java.util.List;

/**
 * UserDao.java
 * zhangyunyun      16-11-15 */
public interface UserDao {

    public User queryUserByUserName(String userName);

    public List<Role> queryRolesByUserId(Long userId);
}
