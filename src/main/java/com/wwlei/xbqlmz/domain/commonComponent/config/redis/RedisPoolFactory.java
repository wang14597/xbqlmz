package com.wwlei.xbqlmz.domain.commonComponent.config.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import org.springframework.context.annotation.Bean;

/**
 * RedisPoolFactory 负责创建和管理 Redis 连接池。
 */
@Configuration
@RequiredArgsConstructor
public class RedisPoolFactory {

    private final RedisConfig redisConfig;

    /**
     * 创建 Jedis 连接池。
     *
     * @return Jedis 连接池实例
     */
    @Bean
    public JedisPool jedisPoolFactory() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
        poolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
        poolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait() * 1000L);
        return new JedisPool(poolConfig, redisConfig.getHost(), redisConfig.getPort(),
                redisConfig.getTimeout() * 1000, redisConfig.getPassword(), redisConfig.getDatabase());
    }
}
