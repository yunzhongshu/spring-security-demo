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

    /**
     * GrantedAuthority的解释:
     * Besides the principal, another important method provided by Authentication is getAuthorities().
     * This method provides an array of GrantedAuthority objects.
     * A GrantedAuthority is, not surprisingly, an authority that is granted to the principal.
     * Such authorities are usually "roles", such as ROLE_ADMINISTRATOR or ROLE_HR_SUPERVISOR.
     * These roles are later on configured for web authorization, method authorization and domain object authorization.
     * Other parts of Spring Security are capable of interpreting these authorities, and expect them to be present.
     * GrantedAuthority objects are usually loaded by the UserDetailsService.
     * Usually the GrantedAuthority objects are application-wide permissions.
     * They are not specific to a given domain object. Thus, you wouldn’t likely have a GrantedAuthority to represent
     * a permission to Employee object number 54, because if there are thousands of such authorities you would
     * quickly run out of memory (or, at the very least, cause the application to take a long time to authenticate a user).
     * Of course, Spring Security is expressly designed to handle this common requirement,
     * but you’d instead use the project’s domain object security capabilities for this purpose.
     *
     *　意思是GrantedAuthority的解释通常都是角色。当然也可以是资源
     * @return
     */
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
