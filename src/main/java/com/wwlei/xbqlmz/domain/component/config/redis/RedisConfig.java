package com.wwlei.xbqlmz.domain.component.config.redis;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.Nullable;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;

@Data
@Configuration
@ConfigurationProperties(prefix = "redis")
public class RedisConfig {
    private String host;
    @Min(0) @Max(65535)
    private int port;
    private String password;
    @Positive
    private int timeout;
    @Positive
    private int poolMaxTotal;
    @Positive
    private int poolMaxIdle;
    @Nullable
    private int poolMaxWait;
    @Min(0)
    private int database;

    /**
     * create JedisPool instance。
     *
     * @return JedisPool。
     */
    @Bean
    protected JedisPool jedisPool() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(this.getPoolMaxIdle());
        poolConfig.setMaxTotal(this.getPoolMaxTotal());
        poolConfig.setMaxWaitMillis(this.getPoolMaxWait() * 1000L);

        // https://github.com/redis/jedis/issues/2781#issuecomment-1092744636
        poolConfig.setJmxEnabled(false);

        return new JedisPool(poolConfig, this.getHost(), this.getPort(),
                this.getTimeout() * 1000, this.getPassword(), this.getDatabase());
    }
}
