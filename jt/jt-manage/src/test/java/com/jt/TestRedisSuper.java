package com.jt;

import org.junit.Test;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestRedisSuper {

    /**
     * 测试redis集群
     */
    @Test
    public void testCluster(){
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.87.128", 7000));
        nodes.add(new HostAndPort("192.168.87.128", 7001));
        nodes.add(new HostAndPort("192.168.87.128", 7002));
        nodes.add(new HostAndPort("192.168.87.128", 7003));
        nodes.add(new HostAndPort("192.168.87.128", 7004));
        nodes.add(new HostAndPort("192.168.87.128", 7005));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        jedisCluster.set("1903", "集群搭建成功!!!");
        System.out.println(jedisCluster.get("1903"));
    }




















    @Test
    public void testSentinels(){
        Set<String> sentinels = new HashSet<>();
        sentinels.add("192.168.87.128:26379");
        JedisSentinelPool pool = new JedisSentinelPool("mymaster", sentinels);
        Jedis jedis = pool.getResource();
        jedis.set("1903", "哨兵测试！！！");
        System.out.println(jedis.get("1903"));
    }













    @Test
    public void testShards(){
        List<JedisShardInfo> shards = new ArrayList<>();
        String host = "192.168.87.128";
        shards.add(new JedisShardInfo(host, 6379));
        shards.add(new JedisShardInfo(host, 6380));
        shards.add(new JedisShardInfo(host, 6381));
        ShardedJedis shardedJedis = new ShardedJedis(shards);
        shardedJedis.set("1903", "分片测试！！！");
        System.out.println("获取数据："+shardedJedis.get("1903"));
    }



}
