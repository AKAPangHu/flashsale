package com.panghu.flashsale;

import com.panghu.flashsale.dao.UserDao;
import com.panghu.flashsale.redis.RedisService;
import com.panghu.flashsale.service.UserService;
import com.panghu.flashsale.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlashsaleApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    RedisService redisService;

    @Test
    public void testRedisService(){
        redisService.get("ss", String.class);
    }




}
