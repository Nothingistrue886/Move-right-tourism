package com.jt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

@Configuration  // 标识这是一个配置类
@PropertySource("classpath:/properties/redis.properties")
@Lazy
public class RedisConfig {

    @Value("${redis.nodes}")
    private String node;

    @Bean
    public JedisCluster jedisCluster(){
         Set<HostAndPort> nodes =  getNode();
        return new JedisCluster(nodes);
    }
    // Set集合表示不要重复数据
    private Set<HostAndPort> getNode() {
        Set<HostAndPort> nodeSet = new HashSet<>();
        String[] strNodes = node.split(",");
        for (String strNode : strNodes) {
            String[] nodes = strNode.split(":");
            String host = nodes[0];
            int port = Integer.parseInt(nodes[1]);
            nodeSet.add(new HostAndPort(host, port));
        }
        return nodeSet;
    }









//    @Value("${redis.sentinel.masterName}")
//    private String masterName;
//    @Value("${redis.sentinels}")
//    private String nodes;

    /**
     * 实现redis哨兵配置
     */
//    @Bean(name = "JedisSentinelPool")
//    @Scope("singleton") // 该对象是单例的
//    public JedisSentinelPool jedisSentinelPool(){
//        Set<String> sentinels = new HashSet<>();
//        sentinels.add(nodes);
//        JedisSentinelPool pool = new JedisSentinelPool(masterName, sentinels);
//        return pool;
//    }
//
//    //@Qualifier该注解表示指定bean赋值  用在方法中
//    @Bean
//    @Scope("prototype") // 该对象是多例的
//    public Jedis jedis(@Qualifier("JedisSentinelPool") JedisSentinelPool pool){
//        Jedis jedis = pool.getResource();
//        return jedis;
//    }


















    /**
     * 实现redis分片配置
     */
//    @Value("${redis.nodes}")
//    private String nodes;
//
//    @Bean
//     public ShardedJedis shardedJedis(){
//         List<JedisShardInfo> shards = new ArrayList<>();
//         String[] strNodes = nodes.split(","); // [node,node,node]
//        for (String strNode : strNodes) {
//            String[] node = strNode.split(":");// [ip,端口]
//            String host = node[0];
//            int port = Integer.parseInt(node[1]);
//            shards.add(new JedisShardInfo(host, port));
//        }
//         return new ShardedJedis(shards);
//     }







//    @Value("${redis.host}")
//    private String host;
//    @Value("${redis.port}")
//    private Integer port;
//    /**
//     * 回顾:
//     * 	1.xml配置文件 添加bean标签  (远古时期)
//     *  2.配置类的形式
//     * 配置:
//     * 		将jedis对象交给spring容器管理
//     *
//     * 利用properties配置文件为属性动态赋值.
//     *
//     */
//    @Bean   // <bean id="jedis" class="包名.jedis">
//    public Jedis jedis(){
//        return new Jedis(host, port);
//    }
}
