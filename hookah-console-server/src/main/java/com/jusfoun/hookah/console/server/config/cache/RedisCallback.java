package com.jusfoun.hookah.console.server.config.cache;

import redis.clients.jedis.JedisCluster;

/**
 * RedisCallback
 *
 * @author LinQ
 * @version 2015-12-17
 */
public interface RedisCallback<T> {
    T doInRedis(JedisCluster cluster) throws DataAccessException;
}
