package com.wwlei.authservice.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class RedisServiceTests {

    @Autowired
    private RedisService redisService;

    @Test
    public void TestRedisWriteAndRead() {
        redisService.writeToRedis("test1", "value1");
        String string = redisService.readFromRedis("test1", String.class);
        Assertions.assertEquals("value1", string);
        redisService.delete("test1");
        String stringDeleted = redisService.readFromRedis("test1", String.class);
        Assertions.assertNull(stringDeleted);
    }

    @Test
    public void TestRedisBatchWriteAndRead() {
        redisService.batchWriteToRedis("test1", "value1","test2", "value2");
        List<String> strings = redisService.batchReadFromRedis("test1", "test2");
        Assertions.assertArrayEquals(List.of("value1","value2").toArray(), strings.toArray());
        redisService.deleteKeys("test1","test2");
        List<String> stringsDeleted = redisService.batchReadFromRedis("test1", "test2");
        for (String string:stringsDeleted) {
            Assertions.assertNull(string);
        }
    }
}
