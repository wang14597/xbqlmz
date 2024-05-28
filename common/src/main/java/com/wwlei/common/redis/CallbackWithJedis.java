package com.wwlei.common.redis;

import redis.clients.jedis.Jedis;

@FunctionalInterface
public interface CallbackWithJedis {

    /***
     * call
     *
     * @param jedis
     * @return
     */
    Object call(Jedis jedis);

}
