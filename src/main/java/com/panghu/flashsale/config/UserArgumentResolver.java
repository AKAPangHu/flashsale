package com.panghu.flashsale.config;

import com.panghu.flashsale.access.UserHolder;
import com.panghu.flashsale.domain.User;
import com.panghu.flashsale.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: 胖虎
 * @date: 2019/6/26 15:37
 **/
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {


    private UserService userService;

    @Autowired
    public UserArgumentResolver(UserService userService) {
        this.userService = userService;
    }


    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> parameterType = methodParameter.getParameterType();
        return parameterType == User.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {

        return UserHolder.get();
    }


}
