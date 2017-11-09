package com.jusfoun.hookah.crowd.config.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisCluster;

@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public CacheManager cacheManager(JedisCluster jedisCluster) {
        RedisCacheManager cacheManager = new RedisCacheManager(jedisCluster);
        cacheManager.setDefaultExpiration(5 * 60);
        return cacheManager;
    }
}