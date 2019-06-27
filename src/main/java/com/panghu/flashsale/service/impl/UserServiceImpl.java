package com.panghu.flashsale.service.impl;

import com.panghu.flashsale.dao.UserDao;
import com.panghu.flashsale.domain.User;
import com.panghu.flashsale.exception.GlobalException;
import com.panghu.flashsale.redis.RedisService;
import com.panghu.flashsale.redis.UserKey;
import com.panghu.flashsale.result.CodeMsg;
import com.panghu.flashsale.service.UserService;
import com.panghu.flashsale.utils.MD5Utils;
import com.panghu.flashsale.utils.UUIDUtils;
import com.panghu.flashsale.vo.LoginVo;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: 胖虎
 * @date: 2019/6/17 9:25
 **/
@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RedisService redisService;

    @Autowired
    public UserServiceImpl(UserDao userDao, RedisService redisService) {
        this.userDao = userDao;
        this.redisService = redisService;
    }

    @Override
    public User findById(long id) {
        return userDao.findById(id);
    }

    @Override
    public User findByToken(HttpServletResponse resp, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        User user = redisService.get(UserKey.token, token, User.class);
        //更新tokenCookie
        addTokenCookie(resp, token, user);
        return user;
    }

    @Override
    public boolean login(LoginVo loginVo, HttpServletResponse resp) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String voCellphone = loginVo.getCellphone();
        String voPassword = loginVo.getPassword();

        User user = userDao.findById(Long.parseLong(voCellphone));
        //检验手机号是否存在
        if (user == null) {
            throw new GlobalException(CodeMsg.CELLPHONE_NOT_EXIST);
        }
        //检验密码是否正确
        String salt = user.getSalt();
        String calculatedPassword = MD5Utils.handlePassword(voPassword, salt);
        String dbPassword = user.getPassword();
        if (!calculatedPassword.equals(dbPassword)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }

        //生成token，缓存进redis
        String token = UUIDUtils.uuid();
        addTokenCookie(resp, token, user);
        return true;
    }

    private void addTokenCookie(HttpServletResponse resp, String token, User user) {
        redisService.set(UserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(UserKey.token.getExpireSeconds());
        cookie.setPath("/");
        resp.addCookie(cookie);
    }
}
