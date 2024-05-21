package com.wwlei.xbqlmz.domain.component.service;

import com.alibaba.fastjson2.JSON;
import com.wwlei.xbqlmz.domain.component.config.redis.RedisPool;
import com.wwlei.xbqlmz.domain.component.service.Interface.RedisService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.params.SetParams;

import javax.xml.ws.Holder;
import java.util.List;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
@Getter
public final class RedisServiceImp implements RedisService {

    private final RedisPool redis;

    private static final int MAX_REDIS_CACHE_TIME = 5 * 60;
    public static final SetParams PARAMS = new SetParams().ex(MAX_REDIS_CACHE_TIME);

    @Override
    public <T> T readFromRedis(String key, Class<T> clazz) {
        Holder<T> holder = new Holder<>(null);
        redis.execute(jedis -> {
            String text = jedis.get(key);
            if (nonNull(text)) {
                if (clazz.equals(String.class)) {
                    holder.value = (T) text;
                } else {
                    holder.value = JSON.parseObject(text, clazz);
                }
            }
        });
        return holder.value;
    }

    @Override
    public <T> void writeToRedis(String key, T model) {
        redis.execute(jedis -> {
            String text;
            if (model instanceof String) {
                text = (String) model;
            } else {
                text = JSON.toJSONString(model);
            }
            String result = jedis.set(key, text, PARAMS);
            if (!"OK".equals(result)) {
                log.error("redis set error");
            }
        });

    }

    @Override
    public List<String> batchReadFromRedis(String... keys) {
        return (List<String>) redis.executeWithCallback(jedis -> jedis.mget(keys));
    }

    @Override
    public void batchWriteToRedis(String... kvs) {
        redis.execute(jedis -> {
            String result = jedis.mset(kvs);
            if (!"OK".equals(result)) {
                log.error("redis mset error");
            }
        });
    }

    @Override
    public Boolean exists(String key) {
        Holder<Boolean> holder = new Holder<>(false);
        redis.execute(jedis -> {
            holder.value = jedis.exists(key);
        });
        return holder.value;
    }

    @Override
    public void delete(String key) {
        redis.execute(jedis -> jedis.del(key));
    }

    @Override
    public void deleteKeys(String... keys) {
        redis.execute(jedis -> jedis.del(keys));
    }
}
