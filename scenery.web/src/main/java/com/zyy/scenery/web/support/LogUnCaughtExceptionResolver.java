/*
 * Copyright 2015 linglingqi Group Holding Ltd.
 */
package com.zyy.scenery.web.support;

import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 捕捉异常，打印log
 * 
 * @author zhangyunyun Aug 28, 2015 9:44:30 AM
 */
public class LogUnCaughtExceptionResolver implements HandlerExceptionResolver {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("unknowError");

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.web.servlet.HandlerExceptionResolver#resolveException(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {

        return new ModelAndView();
    }

}
