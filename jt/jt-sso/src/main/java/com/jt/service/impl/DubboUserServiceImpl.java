package com.jt.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.util.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import redis.clients.jedis.JedisCluster;

import java.util.Date;

/**
 * 我是生产者的实现类
 */
@Service(timeout = 3000)
public class DubboUserServiceImpl implements DubboUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public void saveUser(User user) {
        //1.密码加密
        String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        //2.封装数据
        user.setEmail(user.getPhone())
            .setPassword(md5Pass)
            .setCreated(new Date())
            .setUpdated(user.getCreated());
        userMapper.insert(user);
    }


    /**
     * 1.用户信息校验
     * 		密码加密处理之后查询数据库
     * 2.校验用户数据.
     * 3.将数据保存到redis中.
     */
    @Override
    public String doLogin(User user) {
        String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Pass);
        //将对象中不为null的属性当做where条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
        User userDB = userMapper.selectOne(queryWrapper);
        String token = null;
        if(userDB != null){
            //1.将用户数据保存到redis中 生产key
            String tokenTemp = "JT_TICKET_" + System.currentTimeMillis() + user.getUsername();
            tokenTemp = DigestUtils.md5DigestAsHex(tokenTemp.getBytes());
            //2.生成vlaue数据 userJSON
            //为了安全 需要将数据进行脱敏处理
            userDB.setPassword("2123123你猜的对吗?");
            String userDBJson = ObjectMapperUtil.toJson(userDB);
            jedisCluster.setex(tokenTemp, 7*24*3600, userDBJson);
            token = tokenTemp;
        }
        return token;
    }
}
