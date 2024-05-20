package com.wwlei.xbqlmz.domain.commonComponent.config.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;



@Slf4j
@Service
@RequiredArgsConstructor
public class RedisPool {

    private final JedisPool jedisPool;

    /***
     * execute
     *
     * @param caller
     */
    public void execute(CallWithJedis caller) {
        try (Jedis jedis = jedisPool.getResource()) {
            caller.call(jedis);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /***
     * executeWithCallback
     *
     * @param caller
     * @return
     */
    public Object executeWithCallback(CallbackWithJedis caller) {
        try (Jedis jedis = jedisPool.getResource()) {
            return caller.call(jedis);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
