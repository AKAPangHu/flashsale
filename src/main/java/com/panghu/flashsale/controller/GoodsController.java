package com.panghu.flashsale.controller;

import com.panghu.flashsale.domain.User;
import com.panghu.flashsale.redis.RedisService;
import com.panghu.flashsale.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

/**
 * @author: 胖虎
 * @date: 2019/6/25 19:28
 **/
@Controller
@RequestMapping("/goods")
public class GoodsController {

    private final RedisService redisService;

    private final UserService userService;

    @Autowired
    public GoodsController(RedisService redisService, UserService userService) {
        this.redisService = redisService;
        this.userService = userService;
    }

    @RequestMapping("/to_list")
    public String toLogin(Model model,
                        @CookieValue(value = UserService.COOKIE_NAME_TOKEN) String cookieToken){

        if (StringUtils.isEmpty(cookieToken)){
            return "login";
        }

        User user = userService.findByToken(cookieToken);
        model.addAttribute("user", user);
        return "goods_list";
    }


}
