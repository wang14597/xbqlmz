package com.wwlei.xbqlmz.domain.commonComponent.service;

import com.alibaba.fastjson2.JSON;
import com.wwlei.xbqlmz.domain.commonComponent.config.redis.RedisPool;
import com.wwlei.xbqlmz.domain.commonComponent.service.Interface.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.ws.Holder;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public final class RedisServiceImp implements RedisService {

    private final RedisPool redis;
    @Override
    public <T> T readFromRedis(String key, Class<T> clazz) {
        Holder<T> holder = new Holder<>(null);
        redis.execute(jedis -> {
            String text = jedis.get(key);
            if (nonNull(text)) {
                holder.value= JSON.parseObject(text, clazz);
            }
        });
        return holder.value;
    }

    @Override
    public <T> List<T> readListFromRedis(String key, Class<T> clazz) {
        return null;
    }

    @Override
    public <T> void writeToRedis(String key, T model) {

    }

    @Override
    public <T> List<T> batchReadFromRedis(String... keys) {
        return null;
    }

    @Override
    public void batchWriteToRedis(String... var1) {

    }

    @Override
    public Boolean exists(String key) {
        return null;
    }

    @Override
    public void delete(String key) {

    }

    @Override
    public void batchWriteHashToRedis(String... var1) {

    }

    @Override
    public void deleteKeys(String... var1) {

    }
}
