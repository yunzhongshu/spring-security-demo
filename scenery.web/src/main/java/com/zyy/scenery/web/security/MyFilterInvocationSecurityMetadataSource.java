/**
 * }
 * Copyright 2016
 * Copyright 2016 linglingqi Group Holding Ltd. All Rights Reserved
 */
package com.zyy.scenery.web.security;

import com.zyy.scenery.biz.user.FunctionService;
import com.zyy.scenery.biz.user.RoleService;
import com.zyy.scenery.dal.user.domain.Role;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * MyFilterInvocationSecurityMetadataSource.java
 * zhangyunyun      16-11-16 */
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource, InitializingBean {

    @Autowired
    private RoleService roleService;
    @Autowired
    private FunctionService functionService;

    private Map<RequestMatcher, Collection<ConfigAttribute>> requestMap;

    @Override
    public void afterPropertiesSet() throws Exception {

        loadMetaDataInfo();
    }

    /**
     * 缓存所有资源－分配的角色　的关系映射
     * 调用该访法可以刷新配置
     */
    public void loadMetaDataInfo(){

        requestMap = new HashMap<>();

        List<String> functionList = functionService.queryAllFunctions();
        for(String functionUrl: functionList){

            Set<ConfigAttribute> allAttributes = new HashSet<>();
            List<Role> roleList = functionService.queryRolesByFunctions(functionUrl);
            for(Role role: roleList) {
                allAttributes.add(new SecurityConfig(role.getName()));
            }
            requestMap.put(new AntPathRequestMatcher(functionUrl), allAttributes);

        }


    }

    /**
     * 获取受保护资源所分配的所有配置属性(角色)
     * @param o　受保护的web资源　FilterInvocation
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {

        HttpServletRequest request = ((FilterInvocation)o).getRequest();
        for(Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap.entrySet()) {
            if(entry.getKey().matches(request)) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * 获取所有配置的属性(角色)
     * @return
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> set = new HashSet<>();
        List<Role> roles = roleService.queryAllRoles();
        for(Role role : roles) {
            set.add(new SecurityConfig(role.getName()));
        }
        return set;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
