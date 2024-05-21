package com.wwlei.xbqlmz.domain.commonComponent;

import com.alibaba.fastjson2.JSON;
import com.wwlei.xbqlmz.domain.component.config.redis.RedisPool;
import com.wwlei.xbqlmz.domain.component.service.RedisServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

    @Test
    public void testBatchReadFromRedis() {
        // Arrange
        String[] keys = {"key1", "key2", "key3"};
        String value1 = "value1";
        String value2 = "value2";
        String value3 = "value3";
        List<String> expectedValues = Arrays.asList(value1, value2, value3);
        when(jedis.mget(keys)).thenReturn(Arrays.asList(value1, value2, value3));

        List<String> actualValues = redisService.batchReadFromRedis(keys);

        assertEquals(expectedValues, actualValues);
    }

    @Test
    public void testBatchWriteToRedis() {
        String[] kvs = {"key1", "value1", "key2", "value2"};
        when(jedis.mset(kvs)).thenReturn("OK");

        redisService.batchWriteToRedis(kvs);

        verify(jedis).mset(kvs);
    }

    @Test
    public void testBatchWriteToRedisWithError() {
        String[] kvs = {"key1", "value1", "key2", "value2"};
        when(jedis.mset(kvs)).thenReturn("ERROR");

        redisService.batchWriteToRedis(kvs);

        verify(jedis).mset(kvs);
    }

    @Test
    public void testExistsKeyExists() {
        String key = "testKey";
        when(jedis.exists(key)).thenReturn(true);

        Boolean result = redisService.exists(key);

        assertEquals(true, result);
    }

    @Test
    public void testExistsKeyNotExists() {
        String key = "nonExistentKey";
        when(jedis.exists(key)).thenReturn(false);

        Boolean result = redisService.exists(key);

        assertEquals(false, result);
    }


    @Test
    public void testDelete() {
        String key = "testKey";
        when(jedis.del(key)).thenReturn(1L);

        redisService.delete(key);

        verify(jedis).del(key);
    }

    @Test
    public void testDeleteKeys() {
        String[] keys = {"key1", "key2", "key3"};
        when(jedis.del(keys)).thenReturn(3L);

        redisService.deleteKeys(keys);

        verify(jedis, times(1)).del(keys);
    }
}
