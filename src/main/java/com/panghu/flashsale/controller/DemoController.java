package com.panghu.flashsale.controller;

import com.panghu.flashsale.redis.RedisService;
import com.panghu.flashsale.redis.UserKey;
import com.panghu.flashsale.result.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: 胖虎
 * @date: 2019/6/17 8:35
 **/
@Controller
public class DemoController {

    private final RedisService redisService;

    public DemoController(RedisService redisService) {
        this.redisService = redisService;
    }


    @ResponseBody
    @RequestMapping("/redis/get")
    public Result<String> redisGet(){
        return Result.success("1");
    }

}
