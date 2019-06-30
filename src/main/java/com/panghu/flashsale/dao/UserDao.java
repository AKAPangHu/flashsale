package com.panghu.flashsale.dao;

import com.panghu.flashsale.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @author: 胖虎
 * @date: 2019/6/17 8:12
 **/
@Mapper
@Repository
public interface UserDao {
    @Select("select * from user where id = #{id}")
    User findById(@Param("id") long id);

    @Update("update user set password = #{password} where id = #{id}")
    void updatePassword(User toBeUpdate);
}
