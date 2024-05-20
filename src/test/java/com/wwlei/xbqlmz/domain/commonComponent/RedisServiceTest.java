package com.wwlei.xbqlmz.domain.commonComponent;

import com.wwlei.xbqlmz.domain.commonComponent.config.redis.RedisPool;
import com.wwlei.xbqlmz.domain.commonComponent.service.RedisServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public final class RedisServiceTest {
    private RedisServiceImp redisService;
    @Mock
    private JedisPool jedisPool;
    @Mock
    private Jedis jedis;


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
}

