package com.panghu.flashsale.controller;

import com.panghu.flashsale.service.UserService;
import org.springframework.stereotype.Controller;

/**
 * @author: 胖虎
 * @date: 2019/6/17 8:35
 **/
@Controller
public class DemoController {

    private final UserService userService;

    public DemoController(UserService userService) {
        this.userService = userService;
    }



}
