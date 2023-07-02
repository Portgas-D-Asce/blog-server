package com.devil.blog.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.alibaba.fastjson.JSON;
import com.devil.blog.entity.Error;

public class ApiHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
            throws Exception {
        // TODO Auto-generated method stub
        if(req.getMethod().equals("GET")) {
            return true;
        }

        String token = req.getHeader("token");
        if(token == null || !token.equals("1234567890")) {
            res.setCharacterEncoding("UTF-8");
            res.setContentType("application/json;charset=UTF-8");
            Error error = new Error(100, "token is invalid.", "token is null or invalid.");
            res.setStatus(401);
            res.getWriter().write(JSON.toJSONString(error));
            return false;
            
        }

        return true;
    }
}
