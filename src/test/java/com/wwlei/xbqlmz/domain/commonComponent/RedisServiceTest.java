package com.wwlei.xbqlmz.domain.commonComponent;

import com.alibaba.fastjson2.JSON;
import com.wwlei.xbqlmz.domain.commonComponent.config.redis.RedisPool;
import com.wwlei.xbqlmz.domain.commonComponent.service.RedisServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public final class RedisServiceTest {
    private RedisServiceImp redisService;
    @Mock
    private JedisPool jedisPool;
    @Mock
    private Jedis jedis;

    public static class RedisTestObject {
        private final String value;

        public RedisTestObject(String value) {
            this.value = value;
        }
    }


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(jedisPool.getResource()).thenReturn(jedis);
        redisService = new RedisServiceImp(new RedisPool(jedisPool));
    }

    @Test
    void readFromRedisReturnsNullWhenKeyDoesNotExist() {
        String key = "nonExistingKey";
        when(jedis.get(key)).thenReturn(null);

        Object result = redisService.readFromRedis(key, Object.class);

        assertNull(result);
        verify(jedis).get(key);
    }

    @Test
    void readFromRedisReturnsValueWhenKeyExist() {
        String key = "existingKey";
        when(jedis.get(key)).thenReturn("existingKey");

        Object result = redisService.readFromRedis(key, String.class);

        assertEquals(result, key);
        verify(jedis).get(key);
    }

    @Test
    void readFromRedisReturnsValueWhenKeyExistAndValueIsObject() {
        String key = "existingKey";
        RedisTestObject value = new RedisTestObject("value");
        when(jedis.get(key)).thenReturn(JSON.toJSONString(value));

        RedisTestObject result = redisService.readFromRedis(key, RedisTestObject.class);

        assertEquals(JSON.toJSONString(result), JSON.toJSONString(value));
        verify(jedis).get(key);
    }

    @Test
    void writeToRedisWhenKeyIsString() {
        String key = "key";
        String value = "value";
        redisService.writeToRedis(key, value);

        verify(jedis).set(eq(key), eq(value), eq(RedisServiceImp.PARAMS));
    }

    @Test
    void writeToRedisWhenKeyIsObject() {
        String key = "key";
        RedisTestObject value = new RedisTestObject("value");
        redisService.writeToRedis(key, value);

        verify(jedis).set(eq(key), eq(JSON.toJSONString(value)), eq(RedisServiceImp.PARAMS));
    }
}

