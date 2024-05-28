package com.wwlei.common.redis;

import com.alibaba.fastjson2.JSON;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.params.SetParams;

import javax.xml.ws.Holder;
import java.util.List;

import static java.util.Objects.nonNull;

@Slf4j
@Getter
public class RedisServiceImp implements RedisService {

    private final RedisPool redis;

    private static final int MAX_REDIS_CACHE_TIME = 5 * 60;
    public static final SetParams PARAMS = new SetParams().ex(MAX_REDIS_CACHE_TIME);

    public RedisServiceImp(RedisPool redis) {
        this.redis = redis;
    }

    /**
     * 从Redis中读取数据。
     *
     * @param key   从Redis中读取数据的键。
     * @param clazz 需要将读取的数据转换成的类型。
     * @return 从Redis中读取到的数据，类型为clazz参数指定的类型。
     */
    @Override
    public <T> T readFromRedis(String key, Class<T> clazz) {
        Holder<T> holder = new Holder<>(null);
        redis.execute(jedis -> {
            String text = jedis.get(key);
            if (nonNull(text)) {
                if (clazz.equals(String.class)) {
                    holder.value = (T) text;
                } else {
                    holder.value = JSON.parseObject(text, clazz);
                }
            }
        });
        return holder.value;
    }

    /**
     * 将给定的模型数据写入Redis。
     *
     * @param key   用于在Redis中标识数据的键。
     * @param model 要写入Redis的数据模型。可以是任意类型，但最终都会转换为字符串存储。
     *              <p>
     *              注意：此方法会将模型对象转换为JSON字符串（如果模型不是字符串类型）并存储在Redis中。
     */
    @Override
    public <T> void writeToRedis(String key, T model) {
        redis.execute(jedis -> {
            String text;
            if (model instanceof String) {
                text = (String) model;
            } else {
                text = JSON.toJSONString(model);
            }
            String result = jedis.set(key, text, PARAMS);
            if (!"OK".equals(result)) {
                log.error("redis set error");
            }
        });

    }

    /**
     * 批量从Redis读取值。
     *
     * @param keys 要读取的Redis键，可以是多个。
     * @return 返回一个包含对应键值的List<String>，
     * 如果某个键不存在，则返回null。
     */
    @Override
    public List<String> batchReadFromRedis(String... keys) {
        return (List<String>) redis.executeWithCallback(jedis -> jedis.mget(keys));
    }

    /**
     * 批量将键值对写入Redis。
     *
     * @param kvs 键值对数组，其中相邻的两个元素分别代表一个键和一个值。
     *            注意：该方法没有返回值，但会在写入失败时记录错误日志。
     */
    @Override
    public void batchWriteToRedis(String... kvs) {
        redis.execute(jedis -> {
            String result = jedis.mset(kvs);
            if (!"OK".equals(result)) {
                log.error("redis mset error");
            }
        });
    }

    /**
     * 检查指定的键是否存在于Redis中。
     *
     * @param key 需要检查的键。
     * @return 如果键存在，返回true；否则返回false。
     */
    @Override
    public Boolean exists(String key) {
        Holder<Boolean> holder = new Holder<>(false);
        redis.execute(jedis -> {
            holder.value = jedis.exists(key);
        });
        return holder.value;
    }

    /**
     * 从Redis中删除指定的键。
     *
     * @param key 需要删除的键的名称。
     *            该方法没有返回值，因为它操作的是Redis的存储，成功执行删除操作后不会返回任何结果。
     */
    @Override
    public void delete(String key) {
        redis.execute(jedis -> jedis.del(key));
    }

    /**
     * 删除指定的键。
     *
     * @param keys 要删除的一个或多个键，键可以是字符串类型。
     *             注意：此操作是不可逆的，一旦删除，数据将无法恢复。
     */
    @Override
    public void deleteKeys(String... keys) {
        redis.execute(jedis -> jedis.del(keys));
    }
}
