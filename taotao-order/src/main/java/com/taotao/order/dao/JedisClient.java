package com.taotao.order.dao;

/**
 * 为了统一jedispool 和jediscluster
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/9
 * Time: 15:56
 */
public interface JedisClient {
    String get(String key);
    String set(String key, String value);
    String hget(String hkey, String key);
    long hset(String hkey, String key, String value);
    long incr(String key);
    //设置过期时间
    long expire(String key, int second);
    //查看key是否过期
    long ttl(String key);
    long delete(String key);
    long hdelete(String hkey, String key);
}
