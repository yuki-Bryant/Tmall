package com.taotao.rest.dao.impl;

import com.taotao.rest.dao.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/9
 * Time: 16:04
 */

public class jedisClientSingle implements JedisClient {

    @Autowired
    private JedisPool jedisPool;
    @Override
    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        String s = jedis.get(key);
        jedis.close();
        return s;
    }

    @Override
    public String set(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        String s = jedis.set(key,value);
        jedis.close();
        return s;
    }

    @Override
    public String hget(String hkey, String key) {
        Jedis jedis = jedisPool.getResource();
        String s = jedis.hget(hkey,key);
        jedis.close();
        return s;
    }

    @Override
    public long hset(String hkey, String key, String value) {
        Jedis jedis = jedisPool.getResource();
        long ll = jedis.hset(hkey,key,value);
        jedis.close();
        return ll;
    }

    @Override
    public long incr(String key) {
        Jedis jedis = jedisPool.getResource();
        long ll = jedis.incr(key);
        jedis.close();
        return ll;
    }

    @Override
    public long expire(String key, int second) {
        Jedis jedis = jedisPool.getResource();
        long ll = jedis.expire(key,second);
        jedis.close();
        return ll;
    }

    @Override
    public long ttl(String key) {
        Jedis jedis = jedisPool.getResource();
        long ll = jedis.ttl(key);
        jedis.close();
        return ll;
    }

    @Override
    public long delete(String key) {
        Jedis jedis = jedisPool.getResource();
        long ll = jedis.del(key);
        jedis.close();
        return ll;
    }

    @Override
    public long hdelete(String hkey, String key) {
        Jedis jedis = jedisPool.getResource();
        long ll = jedis.hdel(hkey,key);
        jedis.close();
        return ll;
    }
}
