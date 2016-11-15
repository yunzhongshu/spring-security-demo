/**
 * }
 * Copyright 2016
 * Copyright 2016 linglingqi Group Holding Ltd. All Rights Reserved
 */
package com.zyy.scenery.web.security;

import com.zyy.scenery.biz.user.dto.UserDTO;
import com.zyy.scenery.dal.user.domain.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * MyUserDetails.java
 * zhangyunyun      16-11-15 */
public class MyUserDetails implements UserDetails {

    private UserDTO userDTO;

    public MyUserDetails(UserDTO userDTO){
        this.userDTO = userDTO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<Role> roleList = userDTO.getRoles();

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        for(Role role : roleList){

            authorities.add(new SimpleGrantedAuthority(role.getName()));

        }

        return authorities;

    }

    @Override
    public String getPassword() {
        return userDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return userDTO.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return userDTO.getEnabled();
    }
}
