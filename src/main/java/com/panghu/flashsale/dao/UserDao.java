package com.panghu.flashsale.dao;

import com.panghu.flashsale.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author: 胖虎
 * @date: 2019/6/17 8:12
 **/
@Mapper
@Repository
public interface UserDao {
    @Select("select * from user where id = #{id}")
    User findById(@Param("id") int id);

    @Insert("insert into user(id, name) values(#{id}, #{name})")
    void insert(User user);
}
