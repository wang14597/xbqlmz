package com.wwlei.common.redis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


@Slf4j
public class RedisPool {

    private final JedisPool jedisPool;

    public RedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

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
