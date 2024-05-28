package com.wwlei.common.config.redis;

import lombok.Getter;
import lombok.Setter;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Getter
@Setter
public class BaseRedisConfig {
    private String host;
    private int port;
    private String password;
    private int timeout;
    private int poolMaxTotal;
    private int poolMaxIdle;
    private int poolMaxWait;
    private int database;

    /**
     * create JedisPool instance。
     *
     * @return JedisPool。
     */
    public JedisPool jedisPool() {
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
