package com.wwlei.authservice.service;


import com.wwlei.common.redis.RedisPool;
import com.wwlei.common.redis.RedisServiceImp;
import org.springframework.stereotype.Service;

@Service
public class RedisService extends RedisServiceImp {

    public RedisService(RedisPool redisPool) {
        super(redisPool);
    }
}
