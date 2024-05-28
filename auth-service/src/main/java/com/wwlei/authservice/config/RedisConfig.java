package com.wwlei.authservice.config;

import com.wwlei.common.config.redis.BaseRedisConfig;
import com.wwlei.common.redis.RedisPool;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

@Configuration
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "redis")
public class RedisConfig extends BaseRedisConfig {

    /**
     * create JedisPool instance。
     *
     * @return JedisPool。
     */
    @Bean
    public RedisPool redisPool() {
        JedisPool jedisPool = super.jedisPool();
        return new RedisPool(jedisPool);
    }
}
