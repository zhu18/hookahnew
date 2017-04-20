package com.jusfoun.hookah.console.server.config.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.JedisCluster;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RedisCacheManager
 *
 * @author LinQ
 * @version 2015-12-15
 */
public class RedisCacheManager extends AbstractCacheManager {

    private final JedisCluster jedisCluster;

    private int defaultExpiration = 0;
    private Map<String, Integer> expires = null;

    private Set<String> configuredCacheNames;

    public RedisCacheManager(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }

    public RedisCacheManager(JedisCluster jedisCluster, Collection<String> cacheNames) {
        this.jedisCluster = jedisCluster;
        setCacheNames(cacheNames);
    }

    @Override
    protected Collection<? extends Cache> loadCaches() {

        Assert.notNull(this.jedisCluster, "A jedisCluster is required in order to interact with data store");
        return addConfiguredCachesIfNecessary(Collections.<Cache>emptyList());
    }

    protected Collection<? extends Cache> addConfiguredCachesIfNecessary(Collection<? extends Cache> caches) {

        Assert.notNull(caches, "Caches must not be null!");

        Collection<Cache> result = new ArrayList<>(caches);

        for (String cacheName : getCacheNames()) {

            boolean configuredCacheAlreadyPresent = false;

            for (Cache cache : caches) {

                if (cache.getName().equals(cacheName)) {
                    configuredCacheAlreadyPresent = true;
                    break;
                }
            }

            if (!configuredCacheAlreadyPresent) {
                result.add(getCache(cacheName));
            }
        }

        return result;
    }

    @Override
    protected Cache getMissingCache(String name) {
        int expiration = computeExpiration(name);
        return new RedisCache(name, jedisCluster, expiration);
    }

    protected int computeExpiration(String name) {
        Integer expiration = null;
        if (expires != null) {
            expiration = expires.get(name);
        }
        return (expiration != null ? expiration : defaultExpiration);
    }

    public void setCacheNames(Collection<String> cacheNames) {

        this.configuredCacheNames = CollectionUtils.isEmpty(cacheNames) ? Collections.<String> emptySet()
                : new HashSet<>(cacheNames);
    }

    @Override
    public void afterPropertiesSet() {

        if (!CollectionUtils.isEmpty(configuredCacheNames)) {

            for (String cacheName : configuredCacheNames) {
                addCache(getMissingCache(cacheName));
            }

            configuredCacheNames.clear();
        }

        super.afterPropertiesSet();
    }

    public void setExpires(Map<String, Integer> expires) {
        this.expires = (expires != null ? new ConcurrentHashMap<>(expires) : null);
    }

    public int getDefaultExpiration() {
        return defaultExpiration;
    }

    public void setDefaultExpiration(int defaultExpiration) {
        this.defaultExpiration = defaultExpiration;
    }
}
