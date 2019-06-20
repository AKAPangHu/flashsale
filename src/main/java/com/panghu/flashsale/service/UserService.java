package com.panghu.flashsale.service;

import com.panghu.flashsale.domain.User;

/**
 * @author: 胖虎
 * @date: 2019/6/17 9:24
 **/
public interface UserService {
    User findById(int id);
    void insert(User user);
}
