package com.taotao.rest.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/9
 * Time: 14:40
 */
public class JedisTest {

    //测试jedis单机版
//    @Test
    public void testJedisSingle(){
        //创建一个jedis对象
        Jedis jedis = new Jedis("192.168.149.128",6379);
        //调用jedis对象方法，方法名称和redis命令一致
        jedis.set("key1","jedis test");
        String s = jedis.get("key1");
        System.out.println(s);
        //调用完关闭jedis
        jedis.close();
    }

    //使用连接池
//    @Test
    public void testJedisPool(){
        //创建连接池
        JedisPool jedisPool = new JedisPool("192.168.149.128",6379);
        //从连接池取对象
        Jedis jedis = jedisPool.getResource();
        String s = jedis.get("key1");
        System.out.println(s);
        jedis.close();
        jedisPool.close();
    }

    //测试集群
    //@Test
    public void testJedisCluster(){
        HashSet<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.149.128",7001));
        nodes.add(new HostAndPort("192.168.149.128",7002));
        nodes.add(new HostAndPort("192.168.149.128",7003));
        nodes.add(new HostAndPort("192.168.149.128",7004));
        nodes.add(new HostAndPort("192.168.149.128",7005));
        nodes.add(new HostAndPort("192.168.149.128",7006));

        //添加节点
        JedisCluster jedisCluster = new JedisCluster(nodes);
        jedisCluster.set("key1","cluster");
        String s = jedisCluster.get("key1");
        System.out.println(s);
        jedisCluster.close();
    }

    //@Test
    public void springJedisSingle(){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-jedis.xml");
        JedisPool jedisPool = (JedisPool) context.getBean("redisClient");
        Jedis jedis = jedisPool.getResource();
        String s = jedis.get("key1");
        System.out.println(s);
        jedisPool.close();
        jedis.close();
    }

    //@Test
    public void springJedisCluster(){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-jedis.xml");
        JedisCluster jedisCluster = (JedisCluster) context.getBean("redisClient");
        String s = jedisCluster.get("key1");
        System.out.println(s);
        jedisCluster.close();
    }
}
