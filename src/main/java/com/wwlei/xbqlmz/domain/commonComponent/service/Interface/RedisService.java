package com.wwlei.xbqlmz.domain.commonComponent.service.Interface;

import java.util.List;

public interface RedisService {

    <T> T readFromRedis(String key, Class<T> clazz);

    <T> List<T> readListFromRedis(String key, Class<T> clazz);

    <T> void writeToRedis(String key, T model);

    <T> List<T> batchReadFromRedis(String... keys);

    void batchWriteToRedis(String... var1);

    Boolean exists(String key);

    void delete(String key);

    void batchWriteHashToRedis(String... var1);

    void deleteKeys(String... var1);
}
