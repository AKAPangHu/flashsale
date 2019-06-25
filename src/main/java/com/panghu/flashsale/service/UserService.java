package com.panghu.flashsale.service;

import com.panghu.flashsale.domain.User;
import com.panghu.flashsale.result.CodeMsg;
import com.panghu.flashsale.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;

/**
 * @author: 胖虎
 * @date: 2019/6/17 9:24
 **/
public interface UserService {

    String COOKIE_NAME_TOKEN = "token";

    User findById(long id);

    boolean login( LoginVo loginVo, HttpServletResponse resp);

    User findByToken(String token);
}
