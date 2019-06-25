package com.panghu.flashsale.controller;

import com.panghu.flashsale.result.CodeMsg;
import com.panghu.flashsale.result.Result;
import com.panghu.flashsale.service.UserService;
import com.panghu.flashsale.utils.ValidatorUtils;
import com.panghu.flashsale.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author: 胖虎
 * @date: 2019/6/22 21:24
 **/
@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(method = RequestMethod.GET)
    public String login(){
        return "login";
    }


    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(@Valid LoginVo loginVo, HttpServletResponse resp){
        userService.login(loginVo, resp);
        return Result.success(true);
    }
}
