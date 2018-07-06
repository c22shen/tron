package com.xiao.tron.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class MyInterceptor implements HandlerInterceptor {
    
    static Logger logger = Logger.getLogger(MyInterceptor.class);
    
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object 
            handler) 
            throws Exception {
        
        logger.info("preHandle Method: " + httpServletRequest.getMethod());
        logger.info("preHandle Scheme: " + httpServletRequest.getScheme());
        logger.info("Before handling the request");
        
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        logger.info("After handling the request");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("After rendering the view");
    }
}
