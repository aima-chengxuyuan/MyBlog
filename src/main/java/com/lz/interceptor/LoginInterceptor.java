package com.lz.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author lize
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if (request.getSession().getAttribute("user") == null) {//如果没有登录，就拦截以admin开头的路径，防止普通用户进入管理页面
            response.sendRedirect("/admin");//重定向到登录页面
            return false;
        }
        return true;
    }
}
