package com.panghu.flashsale.service.impl;

import com.panghu.flashsale.dao.UserDao;
import com.panghu.flashsale.domain.User;
import com.panghu.flashsale.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author: 胖虎
 * @date: 2019/6/17 9:25
 **/
@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User findById(int id) {
        return userDao.findById(id);
    }

    @Override
    public void insert(User user) {
        userDao.insert(user);
    }
}
