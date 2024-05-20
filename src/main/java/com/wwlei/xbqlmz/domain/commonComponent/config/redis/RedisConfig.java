package com.wwlei.xbqlmz.domain.commonComponent.config.redis;

import lombok.Data;
import org.springframework.lang.Nullable;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;

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
}
