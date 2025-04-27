package org.example;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;

public class RedisUtil {

    // Redis服务器地址
    private static final String REDIS_HOST = "192.168.13.203";
    // Redis服务器端口
    private static final int REDIS_PORT = 6379;
    // Redis连接池
    private static final JedisPool jedisPool;

    static {
        // 配置连接池
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(100);
        poolConfig.setMaxIdle(20);
        poolConfig.setMinIdle(5);
        poolConfig.setTestOnBorrow(true);

        // 创建连接池
        jedisPool = new JedisPool(poolConfig, REDIS_HOST, REDIS_PORT);
    }

    // 获取Jedis实例
    private static Jedis getJedis() {
        return jedisPool.getResource();
    }

    // 关闭Jedis实例
    private static void closeJedis(Jedis jedis) {
        if (jedis!= null) {
            jedis.close();
        }
    }

    // 字符串操作：设置键值对
    public static void setString(String key, String value) {
        Jedis jedis = getJedis();
        try {
            jedis.select(10);
            jedis.set(key, value);
        } finally {
            closeJedis(jedis);
        }
    }

    // 字符串操作：设置键值对及过期时间
    public static void setex(String key, String value, int seconds) {
        Jedis jedis = getJedis();
        try {
            jedis.select(10);
            jedis.set(key, value);
            setExpire(key, 60);
        } finally {
            closeJedis(jedis);
        }
    }

    // 字符串操作：获取键对应的值
    public static String getString(String key) {
        Jedis jedis = getJedis();
        try {
            jedis.select(10);
            return jedis.get(key);
        } finally {
            closeJedis(jedis);
        }
    }

    // 哈希操作：设置哈希表中的键值对
    public static void setHash(String key, Map<String, String> hashMap) {
        Jedis jedis = getJedis();
        try {
            jedis.select(10);
            jedis.hmset(key, hashMap);
        } finally {
            closeJedis(jedis);
        }
    }

    // 哈希操作：获取哈希表中指定键的值
    public static String getHashValue(String key, String field) {
        Jedis jedis = getJedis();
        try {
            jedis.select(10);
            return jedis.hget(key, field);
        } finally {
            closeJedis(jedis);
        }
    }

    // 列表操作：将元素添加到列表头部
    public static long pushListHead(String key, String value) {
        Jedis jedis = getJedis();
        try {
            jedis.select(10);
            return jedis.lpush(key, value);
        } finally {
            closeJedis(jedis);
        }
    }

    // 列表操作：将元素添加到列表尾部
    public static long pushListTail(String key, String value) {
        Jedis jedis = getJedis();
        try {
            jedis.select(10);
            return jedis.rpush(key, value);
        } finally {
            closeJedis(jedis);
        }
    }

    // 列表操作：获取列表指定位置的元素
    public static String getListElement(String key, long index) {
        Jedis jedis = getJedis();
        try {
            jedis.select(10);
            return jedis.lindex(key, index);
        } finally {
            closeJedis(jedis);
        }
    }

    // 设置过期时间
    public static long setExpire(String key, long index) {
        Jedis jedis = getJedis();
        try {
            jedis.select(10);
            return jedis.expire(key, 60);
        } finally {
            closeJedis(jedis);
        }
    }

    // 集合操作：向集合中添加元素
    public static long addToSet(String key, String... elements) {
        Jedis jedis = getJedis();
        try {
            jedis.select(10);
            return jedis.sadd(key, elements);
        } finally {
            closeJedis(jedis);
        }
    }

    // 集合操作：判断元素是否在集合中
    public static boolean isInSet(String key, String element) {
        Jedis jedis = getJedis();
        try {
            jedis.select(10);
            return jedis.sismember(key, element);
        } finally {
            closeJedis(jedis);
        }
    }

    // 有序集合操作：向有序集合中添加元素及分数
    public static long addToZSet(String key, String element, double score) {
        Jedis jedis = getJedis();
        try {
            jedis.select(10);
            return jedis.zadd(key, score, element);
        } finally {
            closeJedis(jedis);
        }
    }


}