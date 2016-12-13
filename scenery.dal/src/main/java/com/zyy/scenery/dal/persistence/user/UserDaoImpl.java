/**
 * }
 * Copyright 2016
 * Copyright 2016 linglingqi Group Holding Ltd. All Rights Reserved
 */
package com.zyy.scenery.dal.persistence.user;

import com.zyy.scenery.dal.domain.user.Role;
import com.zyy.scenery.dal.domain.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserDaoImpl.java
 * zhangyunyun      16-12-13 */
@Repository("userDao")
public class UserDaoImpl implements UserDao {

    @Override
    public User queryUserByUserName(String userName) {
        return null;
    }

    @Override
    public List<Role> queryRolesByUserId(Long userId) {
        return null;
    }
}
