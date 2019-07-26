package com.jt;

import com.jt.mapper.ItemDescMapper;
import com.jt.pojo.ItemDesc;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class TestRedis {
    /**
     * 1.Spring整合redis入门案例
     */
    @Test
    public void testRedis1(){
        String host = "192.168.87.128";
        int port = 6379;
        Jedis jedis = new Jedis(host,port);
        jedis.set("1903","1903HelloWorld");
        System.out.println(jedis.get("1903"));
        // 设定数据超时时间
        jedis.expire("1903", 20);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("1903还能存活:" + jedis.ttl("1903"));
    }
    /**
     * 2.简化操作数据超时用法
     */
    @Test
    public void testRedis2(){
        Jedis jedis = new Jedis("192.168.87.128",6379);
        jedis.setex("1903",20,"丽霞小仙女!");
        System.out.println(jedis.get("1903"));
        System.out.println(jedis.ttl("1903"));
    }
    /**
     * 3.锁机制用法
     * 	 实际用法: 保证set数据时如果这个key已经存在
     * 			   不允许修改.
     * 	 业务场景.
     * 		小明: set("jimian","8点")
     * 		小张: set("jimian","5点")
     */
    @Test
    public void testRedis3(){
        Jedis jedis = new Jedis("192.168.87.128",6379);
        Long flag1 = jedis.setnx("1903","8点和丽霞约会");
        Long flag2 = jedis.setnx("1903", "5点和小红约会");
        System.out.println(flag1+":::"+flag2);
        System.out.println(jedis.get("1903"));
    }
    /**
     * 死锁
     * 		1.setnx("yue","今晚8点") //加锁
     * 		2.jedis.del("yue");	//减锁
     * 		3.setnx("yue","今晚9点半") //加锁
     * 避免死锁:添加key的超时时间
     * 锁机制优化
     */
    @Test
    public void testRedis4(){
        Jedis jedis = new Jedis("192.168.87.128", 6379);
        String result1 = jedis.set("yue", "8点和丽霞约会", "NX", "EX", 20);
//        int i = 1 / 0;
        jedis.del("yue");
        String result2 = jedis.set("yue", "5点和小明约会", "NX", "EX", 20);
        System.out.println(jedis.get("yue"));
        System.out.println(result1);
        System.out.println(result2);

    }


    /**
     * 2.hash在工作中出场率低
     */
    @Test
    public void testHash1(){
        Jedis jedis = new Jedis("192.168.87.128", 6379);
        jedis.hset("user", "id", "122");
        jedis.hset("user", "name", "tomcat");
        jedis.hset("user", "age", "12");
        System.out.println(jedis.hget("user", "name"));
        System.out.println(jedis.hgetAll("user"));
    }

    /**
     * 3.list集合
     */
    @Test
    public void testList1(){
        Jedis jedis = new Jedis("192.168.87.128", 6379);
        jedis.lpush("list", "1","2","3");
        jedis.lpush("list1", "1,2,3");
        String rpop = jedis.rpop("list");
        String rpop1 = jedis.rpop("list1");
        System.out.println("数据："+rpop);
        System.out.println("数据："+rpop1);
    }

    /**
     * 4.测试事务控制
     */
    @Test
    public void testTransaction(){
        Jedis jedis = new Jedis("192.168.87.128", 6379);
        Transaction transaction = jedis.multi();    // 1.开启事务
        try {
            transaction.set("aa", "aaaa");
            transaction.set("bb", "bbbb");
//            int i = 1 / 0;                        // 实现报错
            transaction.exec();                     // 2.提交事务
        } catch (Exception e) {
            e.printStackTrace();
            transaction.discard();
        }
    }

    /**
     * 5.springboot整合redis实际操作代码
     * 业务需求:
     *     查询itemDesc数据,之后缓存处理
     *     步骤:
     *      1.先查询缓存中是否有itemDesc数据
     *          null    查询数据库,将数据保存到缓存中
     *          !null   获取数据直接返回
     *      2.将数据保存到缓存中
     *      问题:
     *          一般使用redis时都采用String类型操作,
     *          但是从数据库获取的数据都是对象Object
     *          String - json - Object类型转换
     */
    @Autowired
    private Jedis jedis;
    @Autowired
    private ItemDescMapper itemDescMapper;
    @Test
    public void testRedisItemDesc(){
        String key = "100";
        String result = jedis.get(key);
        ItemDesc itemDesc = itemDescMapper.selectById(key);
    }
}
