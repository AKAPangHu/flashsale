package com.panghu.flashsale;

import com.panghu.flashsale.redis.RedisService;
import com.panghu.flashsale.redis.UserKey;
import com.panghu.flashsale.service.UserService;
import com.panghu.flashsale.utils.MD5Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
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

    @Autowired
    UserService userService;

    @Test
    public void testRedisService(){

//        System.out.println(redisService.set(UserKey.id(), "123", 12345));
//        System.out.println(redisService.decr(UserKey.id(), "123"));
//        System.out.println(redisService.incr(UserKey.id(), "123"));
//        System.out.println(redisService.exists(UserKey.id(), "123"));

    }

    @Test
    public void testUserService(){
        System.out.println(userService.findById(13843012345L));
    }

    @Test
    public void testMD5(){
        System.out.println(MD5Utils.handlePassword("52f898c27511518b951a737149783901", "1a2b3c4d"));
    }


}
