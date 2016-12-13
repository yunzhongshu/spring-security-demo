/**
 * }
 * Copyright 2016
 * Copyright 2016 linglingqi Group Holding Ltd. All Rights Reserved
 */
package com.zyy.scenery.biz.user;

import com.zyy.scenery.biz.user.dto.UserDTO;
import com.zyy.scenery.dal.persistence.user.UserDao;
import com.zyy.scenery.dal.domain.user.Role;
import com.zyy.scenery.dal.domain.user.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * UserServiceImpl.java
 * zhangyunyun      16-11-15 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public UserDTO queryUserDTOByUserName(String userName) {

        User user = userDao.queryUserByUserName(userName);

        if(user != null){

            List<Role> roles = userDao.queryRolesByUserId(user.getId());

            UserDTO userDTO = new UserDTO();

            try {
                BeanUtils.copyProperties(user, userDTO);
            }catch ( Exception e){
                e.printStackTrace();
            }

            userDTO.setRoles(roles);

            return userDTO;
        }


        return null;
    }
}
