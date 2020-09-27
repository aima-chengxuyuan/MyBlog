package com.lz.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class RedisTest {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testString() {
        String key = "test2";
        String value = "测试保存string类型";

//        redisTemplate.opsForValue().set(key, value);
//        Object result = redisTemplate.opsForValue().get(key);

        stringRedisTemplate.opsForValue().set(key, value);
        String result = stringRedisTemplate.opsForValue().get(key);
        Assert.assertEquals(result, value);

    }

    @Test
    public void testList() {
        String blogId = "1";
        stringRedisTemplate.opsForList().rightPush(blogId, "comment1");
        stringRedisTemplate.opsForList().rightPush(blogId, "comment2");
        stringRedisTemplate.opsForList().rightPush(blogId, "comment3");
        stringRedisTemplate.opsForList().rightPush(blogId, "comment4");

//        String s = stringRedisTemplate.opsForList().leftPop(blogId);
        List<String> range = stringRedisTemplate.opsForList().range(blogId, 0, 1);

        Assert.assertEquals(range.get(0), "comment1");
        System.out.println(range);
        Assert.assertEquals(range.size(), 2);
    }

}

