package com.wwlei.xbqlmz.domain.component.config.redis;

import redis.clients.jedis.Jedis;

@FunctionalInterface
public interface CallWithJedis {

    /***
     * call
     *
     * @param jedis
     */
    void call(Jedis jedis);

}
