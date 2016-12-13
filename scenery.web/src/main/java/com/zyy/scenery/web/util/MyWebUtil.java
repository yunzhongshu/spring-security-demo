/**
 * }
 * Copyright 2016
 * Copyright 2016 linglingqi Group Holding Ltd. All Rights Reserved
 */
package com.zyy.scenery.web.util;

import com.zyy.scenery.biz.user.dto.UserDTO;
import com.zyy.scenery.web.security.MyUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;

/**
 * MyWebUtil.java
 * zhangyunyun      16-12-8 */
public class MyWebUtil {

    /**
     * 判断是否ajax请求
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

    public static UserDTO getCurrentLoginUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof MyUserDetails){
            return ((MyUserDetails) principal).getUserDTO();
        }

        return null;
    }

}
