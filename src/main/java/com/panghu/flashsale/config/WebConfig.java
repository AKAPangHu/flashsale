package com.panghu.flashsale.config;

import com.panghu.flashsale.access.AccessInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author: 胖虎
 * @date: 2019/6/26 15:33
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private UserArgumentResolver userArgumentResolver;

    private AccessInterceptor accessInterceptor;

    private UserStorageInterceptor userStorageInterceptor;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userStorageInterceptor);
        registry.addInterceptor(accessInterceptor);
    }


    @Autowired
    public void setUserArgumentResolver(UserArgumentResolver userArgumentResolver) {
        this.userArgumentResolver = userArgumentResolver;
    }

    @Autowired
    public void setAccessInterceptor(AccessInterceptor accessInterceptor) {
        this.accessInterceptor = accessInterceptor;
    }

    @Autowired
    public void setUserStorageInterceptor(UserStorageInterceptor userStorageInterceptor) {
        this.userStorageInterceptor = userStorageInterceptor;
    }
}
