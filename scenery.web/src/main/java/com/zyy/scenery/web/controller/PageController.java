/**
 * }
 * Copyright 2016
 * Copyright 2016 linglingqi Group Holding Ltd. All Rights Reserved
 */
package com.zyy.scenery.web.controller;

import com.zyy.scenery.web.util.MyWebUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * PageController.java
 * zhangyunyun      16-12-8 */
@Controller
public class PageController {

    @RequestMapping("/index")
    public ModelAndView indexPage(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ModelMap map = new ModelMap();
        ModelAndView mv = new ModelAndView("index", map);
        mv.addObject("user", MyWebUtil.getCurrentLoginUser());
        return mv;
    }

    @RequestMapping("/login")
    public ModelAndView loginPage(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ModelMap map = new ModelMap();
        ModelAndView mv = new ModelAndView("login", map);
        String errType = request.getParameter("errType");
        if (errType != null) {
            if (errType.equals("disabled")) {
                mv.addObject("errMsg", "帐号不可用,请联系管理员!");
            } else if (errType.equals("authentication")) {
                mv.addObject("errMsg", "登录不成功");
            }
        }
        return mv;
    }



}
