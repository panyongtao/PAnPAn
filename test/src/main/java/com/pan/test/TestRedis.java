package com.pan.test;

import com.pan.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
public class TestRedis {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public  void contextLoads() {
        System.out.println(redisTemplate);
        redisTemplate.opsForValue().set("myKey", "myValue");
        redisTemplate.opsForValue().set("pan", "潘潘");
        System.out.println(redisTemplate.opsForValue().get("myKey"));
        System.out.println(redisTemplate.opsForValue().get("pan"));
    }
}
