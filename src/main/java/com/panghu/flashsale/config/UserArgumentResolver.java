package com.panghu.flashsale.config;

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

        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);

        String paramToken = request.getParameter(UserService.COOKIE_NAME_TOKEN);
        String cookieToken = getCookieValue(request, UserService.COOKIE_NAME_TOKEN);

        if (StringUtils.isEmpty(paramToken) && StringUtils.isEmpty(cookieToken)){
            return null;
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;

        return userService.findByToken(response, token);
    }

    private String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName) ){
                return cookie.getValue();
            }
        }
        return null;
    }
}
