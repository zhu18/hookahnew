package com.jusfoun.hookah.crowd.config.cache;

import org.springframework.cache.support.SimpleValueWrapper;

import static org.springframework.util.Assert.notNull;

/**
 * RedisCacheElement
 *
 * @author LinQ
 * @version 2015-12-17
 */
public class RedisCacheElement extends SimpleValueWrapper {
    private final RedisCacheKey cacheKey;
    private int timeToLive;
    /**
     * Create a new SimpleValueWrapper instance for exposing the given value.
     *
     * @param value the value to expose (may be {@code null})
     */
    public RedisCacheElement(RedisCacheKey cacheKey, Object value) {
        super(value);

        notNull(cacheKey, "CacheKey must not be null!");
        this.cacheKey = cacheKey;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public RedisCacheKey getKey() {
        return cacheKey;
    }

    public String getKeyString() {
        return cacheKey.getKey();
    }

    public int getTimeToLive() {
        return timeToLive;
    }

    public boolean isEternal() {
        return 0 == timeToLive;
    }

    public RedisCacheElement expireAfter(int seconds) {

        setTimeToLive(seconds);
        return this;
    }
}
