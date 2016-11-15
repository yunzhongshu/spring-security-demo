/**
 * }
 * Copyright 2016
 * Copyright 2016 linglingqi Group Holding Ltd. All Rights Reserved
 */
package com.zyy.scenery.web.security;

import com.zyy.scenery.biz.user.UserService;
import com.zyy.scenery.biz.user.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;

/**
 * MyUserDetailServiceImpl.java
 * zhangyunyun      16-11-15 */
public class MyUserDetailServiceImpl implements UserDetailsService {

    @Resource
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        //注入查询用户的服务类查询用户的用户名、密码、可用状态、拥有的角色
        UserDTO userDTO = userService.queryUserDTOByUserName(userName);

        if(userDTO == null)
            throw new UsernameNotFoundException("用户名"+userName+"不存在!");

        return new MyUserDetails(userDTO);
    }
}
