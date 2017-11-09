package com.jusfoun.hookah.crowd.config.cache;

import com.jusfoun.hookah.crowd.config.cache.serializer.JacksonJsonRedisSerializer;
import com.jusfoun.hookah.crowd.config.cache.serializer.RedisSerializer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import redis.clients.jedis.JedisCluster;

import java.util.Set;
import java.util.concurrent.Callable;

import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.notNull;
import static org.springframework.util.ObjectUtils.nullSafeEquals;

/**
 * RedisCache
 *
 * @author LinQ
 * @version 2015-12-15
 */
@SuppressWarnings("unchecked")
public class RedisCache implements Cache {
    private final JedisCluster cluster;
    private final RedisCacheMetadata cacheMetadata;
    private final CacheValueAccessor cacheValueAccessor;

    @SuppressWarnings("unchecked")
    public RedisCache(String name, JedisCluster jedisCluster, int expiration) {
        hasText(name, "non-empty cache name is required");
        this.cacheMetadata = new RedisCacheMetadata(name);
        this.cacheMetadata.setDefaultExpiration(expiration);

        this.cluster = jedisCluster;
        this.cacheValueAccessor = new CacheValueAccessor(new JacksonJsonRedisSerializer(Object.class));
    }

    @Override
    public String getName() {
        return cacheMetadata.getCacheName();
    }

    @Override
    public Object getNativeCache() {
        return cluster;
    }

    @Override
    public ValueWrapper get(Object key) {
        return get(new RedisCacheKey(key).usePrefix(cacheMetadata.getCacheName()));
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        ValueWrapper wrapper = get(key);
        return wrapper == null ? null : (T) wrapper.get();
    }

    @Override
    public <T> T get(Object o, Callable<T> callable) {
        return null;
    }

    public RedisCacheElement get(final RedisCacheKey cacheKey) {

        notNull(cacheKey, "CacheKey must not be null!");

        String resultValue = (String) execute(new AbstractRedisCacheCallback<String>(cacheMetadata,
                new StringRedisCacheElement(new RedisCacheElement(cacheKey, null),
                        cacheValueAccessor)) {
            @Override
            public String doInRedis(StringRedisCacheElement element, JedisCluster cluster) throws DataAccessException {
                return cluster.get(element.getKeyString());
            }
        });

        if (resultValue == null) {
            return null;
        }

        return new RedisCacheElement(cacheKey, cacheValueAccessor.deserializeIfNecessary(resultValue));
    }

    @Override
    public void put(Object key, Object value) {
        put(new RedisCacheElement(new RedisCacheKey(key).usePrefix(cacheMetadata.getCacheName()), value)
                .expireAfter(cacheMetadata.getDefaultExpiration()));
    }

    public void put(RedisCacheElement element) {

        notNull(element, "Element must not be null!");

        execute(new RedisCachePutCallback(cacheMetadata, new StringRedisCacheElement(element, cacheValueAccessor)));
    }

    private <T> T execute(RedisCallback<T> action) {
        return action.doInRedis(cluster);
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        return putIfAbsent(new RedisCacheElement(new RedisCacheKey(key).usePrefix(cacheMetadata.getCacheName()), value)
                .expireAfter(cacheMetadata.getDefaultExpiration()));
    }

    public ValueWrapper putIfAbsent(RedisCacheElement element) {

        notNull(element, "Element must not be null!");

        return toWrapper(cacheValueAccessor.deserializeIfNecessary(
                (String) execute(new RedisCachePutIfAbsentCallback(cacheMetadata,
                        new StringRedisCacheElement(element, cacheValueAccessor)))));
    }

    private ValueWrapper toWrapper(Object value) {
        return (value != null ? new SimpleValueWrapper(value) : null);
    }

    @Override
    public void evict(Object key) {
        evict(new RedisCacheElement(new RedisCacheKey(key).usePrefix(cacheMetadata.getCacheName()), null));
    }

    public void evict(final RedisCacheElement element) {

        notNull(element, "Element must not be null!");
        execute(new RedisCacheEvictCallback(cacheMetadata, new StringRedisCacheElement(element, cacheValueAccessor)));
    }

    @Override
    public void clear() {
        execute(new RedisCacheCleanByKeysCallback(cacheMetadata));
    }

    static abstract class AbstractRedisCacheCallback<T> implements RedisCallback {
        private long WAIT_FOR_LOCK_TIMEOUT = 300;
        private final RedisCacheMetadata cacheMetadata;
        private final StringRedisCacheElement element;

        public AbstractRedisCacheCallback(RedisCacheMetadata cacheMetadata, StringRedisCacheElement element) {
            this.cacheMetadata = cacheMetadata;
            this.element = element;
        }

        @Override
        public T doInRedis(JedisCluster cluster) throws DataAccessException {
            return doInRedis(element, cluster);
        }

        public abstract T doInRedis(StringRedisCacheElement element, JedisCluster cluster) throws DataAccessException;

        protected void processKeyExpiration(RedisCacheElement element, JedisCluster cluster) {
            cluster.expire(element.getKeyString(), element.getTimeToLive());
        }

        protected void maintainKnownKeys(RedisCacheElement element, JedisCluster cluster) {

            cluster.zadd(cacheMetadata.getSetOfKnownKeysKey(), 0, element.getKeyString());

            if (!element.isEternal()) {
                cluster.expire(cacheMetadata.getSetOfKnownKeysKey(), element.getTimeToLive());
            }
        }

        protected void cleanKnownKeys(RedisCacheElement element, JedisCluster cluster) {

            cluster.zrem(cacheMetadata.getSetOfKnownKeysKey(), element.getKeyString());
        }

        protected boolean waitForLock(JedisCluster cluster) {

            boolean retry;
            boolean foundLock = false;
            do {
                retry = false;
                if (cluster.exists(cacheMetadata.getCacheLockKey())) {
                    foundLock = true;
                    try {
                        Thread.sleep(WAIT_FOR_LOCK_TIMEOUT);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    retry = true;
                }
            } while (retry);

            return foundLock;
        }
    }

    static abstract class LockingRedisCacheCallback<T> implements RedisCallback<T> {

        private final RedisCacheMetadata metadata;

        public LockingRedisCacheCallback(RedisCacheMetadata metadata) {
            this.metadata = metadata;
        }

        @Override
        public T doInRedis(JedisCluster cluster) throws DataAccessException {

            if (cluster.exists(metadata.getCacheLockKey())) {
                return null;
            }

            try {
                cluster.set(metadata.getCacheLockKey(), metadata.getCacheLockKey());
                return doInLock(cluster);
            } finally {
                cluster.del(metadata.getCacheLockKey());
            }
        }

        public abstract T doInLock(JedisCluster cluster);
    }

    static class RedisCacheCleanByKeysCallback extends LockingRedisCacheCallback<Void> {
        private static final int PAGE_SIZE = 128;
        private final RedisCacheMetadata metadata;

        public RedisCacheCleanByKeysCallback(RedisCacheMetadata metadata) {
            super(metadata);
            this.metadata = metadata;
        }

        @Override
        public Void doInLock(JedisCluster cluster) {

            int offset = 0;
            boolean finished;

            do {
                // need to paginate the keys
                Set<String> keys = cluster.zrange(metadata.getSetOfKnownKeysKey(), (offset) * PAGE_SIZE, (offset + 1)
                        * PAGE_SIZE - 1);
                finished = keys.size() < PAGE_SIZE;
                offset++;
                if (!keys.isEmpty()) {      // 没有批量操作,只能一个个删除
                    for (String key : keys) {
                        cluster.del(key);
                    }
                }
            } while (!finished);

            cluster.del(metadata.getSetOfKnownKeysKey());
            return null;
        }
    }

    static class RedisCachePutCallback extends AbstractRedisCacheCallback<Void> {

        public RedisCachePutCallback(RedisCacheMetadata cacheMetadata, StringRedisCacheElement element) {
            super(cacheMetadata, element);
        }

        @Override
        public Void doInRedis(StringRedisCacheElement element, JedisCluster cluster) throws DataAccessException {

            cluster.set(element.getKeyString(), element.get());
            processKeyExpiration(element, cluster);
            maintainKnownKeys(element, cluster);

            return null;
        }
    }

    static class RedisCachePutIfAbsentCallback extends AbstractRedisCacheCallback<String> {

        public RedisCachePutIfAbsentCallback(RedisCacheMetadata cacheMetadata, StringRedisCacheElement element) {
            super(cacheMetadata, element);
        }

        @Override
        public String doInRedis(StringRedisCacheElement element, JedisCluster cluster) throws DataAccessException {

            waitForLock(cluster);
            String resultValue = put(element, cluster);

            if (nullSafeEquals(element.get(), resultValue)) {
                processKeyExpiration(element, cluster);
                maintainKnownKeys(element, cluster);
            }

            return resultValue;
        }

        private String put(StringRedisCacheElement element, JedisCluster cluster) {

            Long valueSet = cluster.setnx(element.getKeyString(), element.get());
            if (valueSet == null || valueSet == 0) {
                return cluster.get(element.getKeyString());
            }

            return null;
        }
    }

    static class RedisCacheEvictCallback extends AbstractRedisCacheCallback<Void> {

        public RedisCacheEvictCallback(RedisCacheMetadata cacheMetadata, StringRedisCacheElement element) {
            super(cacheMetadata, element);
        }

        @Override
        public Void doInRedis(StringRedisCacheElement element, JedisCluster cluster) throws DataAccessException {

            cluster.del(element.getKeyString());
            cleanKnownKeys(element, cluster);
            return null;
        }
    }

    static class RedisCacheMetadata {
        private final String cacheName;
        private final String setOfKnownKeys;
        private final String cacheLockName;
        private int defaultExpiration = 0;

        public RedisCacheMetadata(String cacheName) {
            hasText(cacheName, "CacheName must not be null or empty!");
            this.cacheName = cacheName;
            this.setOfKnownKeys = cacheName + "~keys";
            this.cacheLockName = cacheName + "~lock";
        }

        public void setDefaultExpiration(int defaultExpiration) {
            this.defaultExpiration = defaultExpiration;
        }

        public String getCacheName() {
            return cacheName;
        }

        public String getSetOfKnownKeysKey() {
            return setOfKnownKeys;
        }

        public int getDefaultExpiration() {
            return defaultExpiration;
        }

        public String getCacheLockKey() {
            return cacheLockName;
        }
    }

    static class CacheValueAccessor {
        @SuppressWarnings("rawtypes")
        private final RedisSerializer valueSerializer;

        public CacheValueAccessor(RedisSerializer valueSerializer) {
            this.valueSerializer = valueSerializer;
        }

        @SuppressWarnings("unchecked")
        String convertToStringIfNecessary(Object value) {
            if (value == null) {
                return StringUtils.EMPTY;
            }

            if (valueSerializer == null && value instanceof String) {
                return (String) value;
            }

            if (valueSerializer == null) {
                return value.toString();
            }

            return valueSerializer.serialize(value);
        }

        Object deserializeIfNecessary(String value) {
            if (valueSerializer != null) {
                return valueSerializer.deserialize(value);
            }

            return value;
        }
    }

    static class StringRedisCacheElement extends RedisCacheElement {
        private String valueString;
        private RedisCacheElement element;

        public StringRedisCacheElement(RedisCacheElement element, CacheValueAccessor accessor) {
            super(element.getKey(), element.get());
            this.element = element;
            this.valueString = accessor.convertToStringIfNecessary(element.get());
        }

        @Override
        public RedisCacheKey getKey() {
            return element.getKey();
        }

        @Override
        public String getKeyString() {
            return element.getKeyString();
        }

        @Override
        public int getTimeToLive() {
            return element.getTimeToLive();
        }

        @Override
        public boolean isEternal() {
            return element.isEternal();
        }

        @Override
        public RedisCacheElement expireAfter(int seconds) {
            return element.expireAfter(seconds);
        }

        @Override
        public String get() {
            return valueString;
        }
    }
}
