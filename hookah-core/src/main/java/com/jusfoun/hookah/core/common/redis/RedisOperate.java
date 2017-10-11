package com.jusfoun.hookah.core.common.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author huang lei
 * @date 2017/4/18 下午4:05
 * @desc 操作redis时真正使用的类
 * 具体连接方式由spring注入的客户端类型决定
 */
public class RedisOperate implements IJedisClient {

    IJedisClient jedisClient;

    public IJedisClient getJedisClient() {
        return jedisClient;
    }

    public void setJedisClient(IJedisClient jedisClient) {
        this.jedisClient = jedisClient;
    }

    @Override
    public String get(String key) {
        return jedisClient.get(key);
    }

    @Override
    public Object getObject(String key) {
        return jedisClient.getObject(key);
    }

    @Override
    public String set(String key, String value, int cacheSeconds) {
        return jedisClient.set(key, value, cacheSeconds);
    }

    @Override
    public String setObject(String key, Object value, int cacheSeconds) {
        return jedisClient.setObject(key, value, cacheSeconds);
    }

    @Override
    public List<String> getList(String key) {
        return jedisClient.getList(key);
    }

    @Override
    public List<Object> getObjectList(String key) {
        return jedisClient.getObjectList(key);
    }

    @Override
    public long setList(String key, List<String> value, int cacheSeconds) {
        return jedisClient.setList(key, value, cacheSeconds);
    }

    @Override
    public long setObjectList(String key, List<Object> value, int cacheSeconds) {
        return jedisClient.setObjectList(key, value, cacheSeconds);
    }

    @Override
    public long listAdd(String key, String... value) {
        return jedisClient.listAdd(key, value);
    }

    @Override
    public long listObjectAdd(String key, Object... value) {
        return jedisClient.listObjectAdd(key, value);
    }

    @Override
    public Set<String> getSet(String key) {
        return jedisClient.getSet(key);
    }

    @Override
    public Set<Object> getObjectSet(String key) {
        return jedisClient.getObjectSet(key);
    }

    @Override
    public long setSet(String key, Set<String> value, int cacheSeconds) {
        return jedisClient.setSet(key, value, cacheSeconds);
    }

    @Override
    public long setObjectSet(String key, Set<Object> value, int cacheSeconds) {
        return jedisClient.setObjectSet(key, value, cacheSeconds);
    }

    @Override
    public long setSetAdd(String key, String... value) {
        return jedisClient.setSetAdd(key, value);
    }

    @Override
    public long setSetObjectAdd(String key, Object... value) {
        return jedisClient.setSetObjectAdd(key, value);
    }

    @Override
    public Map<String, String> getMap(String key) {
        return jedisClient.getMap(key);
    }

    @Override
    public Map<String, Object> getObjectMap(String key) {
        return jedisClient.getObjectMap(key);
    }

    @Override
    public String setMap(String key, Map<String, String> value, int cacheSeconds) {
        return jedisClient.setMap(key, value, cacheSeconds);
    }

    @Override
    public String setObjectMap(String key, Map<String, Object> value,
                               int cacheSeconds) {
        // TODO Auto-generated method stub
        return jedisClient.setObjectMap(key, value, cacheSeconds);
    }

    @Override
    public String mapPut(String key, Map<String, String> value) {
        // TODO Auto-generated method stub
        return jedisClient.mapPut(key, value);
    }

    @Override
    public String mapObjectPut(String key, Map<String, Object> value) {
        // TODO Auto-generated method stub
        return jedisClient.mapObjectPut(key, value);
    }

    @Override
    public long mapRemove(String key, String mapKey) {
        // TODO Auto-generated method stub
        return jedisClient.mapRemove(key, mapKey);
    }

    @Override
    public long mapObjectRemove(String key, String mapKey) {
        return jedisClient.mapObjectRemove(key, mapKey);
    }

    @Override
    public boolean mapExists(String key, String mapKey) {
        return jedisClient.mapExists(key, mapKey);
    }

    @Override
    public boolean mapObjectExists(String key, String mapKey) {
        return jedisClient.mapObjectExists(key, mapKey);
    }

    @Override
    public long del(String key) {
        return jedisClient.del(key);
    }

    @Override
    public long delObject(String key) {
        return jedisClient.delObject(key);
    }

    @Override
    public boolean exists(String key) {
        return jedisClient.exists(key);
    }

    @Override
    public boolean existsObject(String key) {
        return jedisClient.existsObject(key);
    }

    @Override
    public String incr(String key) {
        return jedisClient.incr(key);
    }

    @Override
    public String hset(String key, String field, String value) {
        return jedisClient.hset(key, field, value);
    }

}
