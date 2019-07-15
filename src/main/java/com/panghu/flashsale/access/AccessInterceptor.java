package com.panghu.flashsale.access;

import com.alibaba.fastjson.JSON;
import com.panghu.flashsale.domain.User;
import com.panghu.flashsale.redis.AccessKey;
import com.panghu.flashsale.redis.RedisService;
import com.panghu.flashsale.result.CodeMsg;
import com.panghu.flashsale.result.Result;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author: 胖虎
 * @date: 2019/7/14 18:37
 **/
@Component
public class AccessInterceptor extends HandlerInterceptorAdapter {

    private final RedisService redisService;

    public AccessInterceptor(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null){
                return true;
            }
            int maxCount = accessLimit.maxCount();
            int seconds = accessLimit.seconds();
            boolean needLogin = accessLimit.needLogin();
            User user = UserHolder.get();
            if (needLogin){
                if (user == null){
                    render(response, Result.error(CodeMsg.SESSION_ERROR));
                    return false;
                }
            }
            String key = request.getRequestURI();
            AccessKey accessKey = AccessKey.getAccessKeyWithExpire(seconds);
            Integer count = redisService.get(accessKey, key, Integer.class);
            if (count == null){
                redisService.set(accessKey, key, 1);
            }
            else if (count <= maxCount){
                redisService.incr(accessKey, key);
            }
            else{
                render(response, Result.error(CodeMsg.ACCESS_TOO_MANY));
                return false;
            }
        }
        return true;
    }

    private void render(HttpServletResponse response, Object o) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String str = JSON.toJSONString(o);
        out.write(str.getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }


}
