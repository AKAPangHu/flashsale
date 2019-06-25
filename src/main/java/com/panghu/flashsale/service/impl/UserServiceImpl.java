package com.panghu.flashsale.service.impl;

import com.panghu.flashsale.dao.UserDao;
import com.panghu.flashsale.domain.User;
import com.panghu.flashsale.exception.GlobalException;
import com.panghu.flashsale.result.CodeMsg;
import com.panghu.flashsale.service.UserService;
import com.panghu.flashsale.utils.MD5Utils;
import com.panghu.flashsale.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: 胖虎
 * @date: 2019/6/17 9:25
 **/
@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User findById(long id) {
        return userDao.findById(id);
    }

    @Override
    public boolean login(LoginVo loginVo) {
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
        return true;
    }
}
