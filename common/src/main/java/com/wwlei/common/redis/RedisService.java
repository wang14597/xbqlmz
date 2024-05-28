package com.wwlei.common.redis;

import java.util.List;

public interface RedisService {

    <T> T readFromRedis(String key, Class<T> clazz);


    <T> void writeToRedis(String key, T model);

    List<String> batchReadFromRedis(String... keys);

    void batchWriteToRedis(String... var1);

    Boolean exists(String key);

    void delete(String key);

    void deleteKeys(String... var1);
}
