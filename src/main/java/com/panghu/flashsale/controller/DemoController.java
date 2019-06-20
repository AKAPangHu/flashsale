package com.panghu.flashsale.controller;

import com.panghu.flashsale.redis.RedisService;
import com.panghu.flashsale.redis.UserKey;
import com.panghu.flashsale.result.Result;
import com.panghu.flashsale.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: 胖虎
 * @date: 2019/6/17 8:35
 **/
@Controller
public class DemoController {

    private final UserService userService;
    private final RedisService redisService;

    public DemoController(UserService userService, RedisService redisService) {
        this.userService = userService;
        this.redisService = redisService;
    }


    @ResponseBody
    @RequestMapping("/redis/get")
    public Result<String> redisGet(){
        boolean b = redisService.set(UserKey.getById(), "111", "NMSL");
        String s = redisService.get(UserKey.getById(), "111", String.class);

        return Result.success(s);
    }

}
