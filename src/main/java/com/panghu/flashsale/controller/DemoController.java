package com.panghu.flashsale.controller;

import com.panghu.flashsale.rabbitmq.MqSender;
import com.panghu.flashsale.redis.RedisService;
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

    private final MqSender mqSender;

    public DemoController(RedisService redisService, MqSender mqSender) {
        this.redisService = redisService;
        this.mqSender = mqSender;
    }


    @ResponseBody
    @RequestMapping("/redis/get")
    public Result<String> redisGet(){
        return Result.success("1");
    }

    @ResponseBody
    @RequestMapping("/mq")
    public Result<String> mqTest(){
        return Result.success("1");
    }

}
