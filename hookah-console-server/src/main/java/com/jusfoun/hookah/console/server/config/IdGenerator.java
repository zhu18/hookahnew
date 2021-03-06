package com.jusfoun.hookah.console.server.config;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huang lei
 * @date 2017/4/18 下午4:36
 * @desc
 */
public class IdGenerator {
    static final Logger logger = LoggerFactory.getLogger(IdGenerator.class);

    List<Pair<JedisPool, String>> jedisPoolList;
    int retryTimes;

    int index = 0;

    private IdGenerator() {

    }

    private IdGenerator(List<Pair<JedisPool, String>> jedisPoolList,
                        int retryTimes) {
        this.jedisPoolList = jedisPoolList;
        this.retryTimes = retryTimes;
    }

    static public IdGeneratorBuilder builder() {
        return new IdGeneratorBuilder();
    }

    static class IdGeneratorBuilder {
        List<Pair<JedisPool, String>> jedisPoolList = new ArrayList();
        int retryTimes = 5;

        public IdGeneratorBuilder addHost(GenericObjectPoolConfig poolConfig, String host, int port,int timeout,String password, String luaSha) {
            jedisPoolList.add(Pair.of(new JedisPool(poolConfig,host, port,timeout,password), luaSha));
            return this;
        }

        public IdGeneratorBuilder retryTimes(int retryTimes) {
            this.retryTimes = retryTimes;
            return this;
        }

        public IdGenerator build() {
            return new IdGenerator(jedisPoolList, retryTimes);
        }
    }

    public long next(String tab) {
        return next(tab, 0);
    }

    public long next(String tab, long shardId) {
        for (int i = 0; i < retryTimes; ++i) {
            Long id = innerNext(tab, shardId);
            if (id != null) {
                return id;
            }
        }
        throw new RuntimeException("Can not generate id!");
    }

    Long innerNext(String tab, long shardId) {
        index++;
        Pair<JedisPool, String> pair = jedisPoolList.get(index
            % jedisPoolList.size());
        JedisPool jedisPool = pair.getLeft();

        String luaSha = pair.getRight();
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            List<Long> result = (List<Long>) jedis.evalsha(luaSha, 2, tab, ""
                + shardId);
            long id = buildId(result.get(0), result.get(1), result.get(2),
                result.get(3));
            return id;
        } catch (JedisConnectionException e) {
            if (jedis != null) {
                jedis.close();
//                jedisPool.returnBrokenResource(jedis);
            }
            logger.error("generate id error!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
//                jedisPool.returnResource(jedis);
            }
        }
        return null;
    }

    public static long buildId(long second, long microSecond, long shardId,
                               long seq) {
        long miliSecond = (second * 1000 + microSecond / 1000);
        return (miliSecond << (12 + 10)) + (shardId << 10) + seq;
    }

    public static List<Long> parseId(long id) {
        long miliSecond = id >>> 22;
        // 2 ^ 12 = 0xFFF
        long shardId = (id & (0xFFF << 10)) >> 10;
        long seq = id & 0x3FF;

        List<Long> re = new ArrayList<Long>(4);
        re.add(miliSecond);
        re.add(shardId);
        re.add(seq);
        return re;
    }

    public static void main(String[] args) {
        String tab = "order";
        long userId = 123456789;
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(1024);
        poolConfig.setMaxIdle(200);
        poolConfig.setTestOnBorrow(false);
        IdGenerator idGenerator = IdGenerator.builder()
            .addHost(poolConfig, "192.168.15.90", 6379,0,"Yqn2ht4DYkMHlDvTY7CV", "c5809078fa6d652e0b0232d552a9d06d37fe819c")
//				.addHost(poolConfig, "192.168.15.90", 7001,10000,"Yqn2ht4DYkMHlDvTY7CV", "accb7a987d4fb0fd85c57dc5a609529f80ec3722")
//				.addHost(poolConfig, "192.168.15.90", 7002,10000,"Yqn2ht4DYkMHlDvTY7CV", "f55f781ca4a00a133728488e15a554c070b17255")
            .build();

        long id = idGenerator.next(tab, userId);

        System.out.println("id:" + id);
        List<Long> result = IdGenerator.parseId(id);

        System.out.println("miliSeconds:" + result.get(0) + ", partition:"
            + result.get(1) + ", seq:" + result.get(2));
    }
}
