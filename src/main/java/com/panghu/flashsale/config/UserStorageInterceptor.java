package com.panghu.flashsale.config;

import com.panghu.flashsale.access.UserHolder;
import com.panghu.flashsale.domain.User;
import com.panghu.flashsale.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: 胖虎
 * @date: 2019/7/14 22:24
 **/
@Component
public class UserStorageInterceptor extends HandlerInterceptorAdapter {

    private final UserService userService;

    public UserStorageInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod){
            if (UserHolder.get() != null){
                return true;
            }
            saveUserIntoHolder(request, response);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //防止重用线程时，变量冲突
        UserHolder.remove();
    }

    private void saveUserIntoHolder(HttpServletRequest request, HttpServletResponse response){
        String paramToken = null;
        if (request != null) {
            paramToken = request.getParameter(UserService.COOKIE_NAME_TOKEN);
        }
        String cookieToken = null;
        if (request != null) {
            cookieToken = getCookieValue(request, UserService.COOKIE_NAME_TOKEN);
        }

        if (StringUtils.isEmpty(paramToken) && StringUtils.isEmpty(cookieToken)){
            return;
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        User user = userService.getByToken(response, token);
        UserHolder.set(user);
    }

    private String getCookieValue(HttpServletRequest request, String cookieName) {
        if(request == null){
            return null;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0){
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName) ){
                return cookie.getValue();
            }
        }
        return null;
    }
}
