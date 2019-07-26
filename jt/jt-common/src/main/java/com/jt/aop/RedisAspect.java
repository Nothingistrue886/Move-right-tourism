package com.jt.aop;

import com.jt.anno.Cache_Find;
import com.jt.enu.KEY_ENUM;
import com.jt.util.ObjectMapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisCluster;

@Component  //将对象交给spring容器管理
@Aspect     //标识切面  切面=切入点表达式+通知
@Slf4j
public class RedisAspect {
    //表示当spring容器启动时不会立即注入对象!
    @Autowired(required = false)
    private JedisCluster jedisCluster;

    /**
     * 规定:
     * 1.环绕通知必须使用ProceedingJoinPoint
     * 2.如果通知中有参数joinPoint.必须位于第一位
     *
     * @param joinPoint
     * @param cache_find
     * @return
     */

    //问题1:如何获取注解中的属性?
    //该切入点表达式 规定只能获取注解类型 用法名称必须匹配
    @Around(value = "@annotation(cache_find)")
    public Object around(ProceedingJoinPoint joinPoint, Cache_Find cache_find) {
        //1.动态获取key
        String key = getKey(joinPoint, cache_find);
        //2.从redis中获取数据
        String resultJson = jedisCluster.get(key);
        //3.判断数据是否有值
        Object resultData = null;
        try {
            if (StringUtils.isEmpty(resultJson)) {
                //3.1表示缓存中没有数据,则查询数据库(调用业务方法)
                resultData = joinPoint.proceed();
                //3.2将数据保存到缓存中
                String json = ObjectMapperUtil.toJson(resultData);
                //3.3判断当前数据是否有失效时间
                if (cache_find.seconds() == 0) {
                    jedisCluster.set(key, json);
                } else {
                    jedisCluster.setex(key, cache_find.seconds(), json);
                }
                System.out.println("aop查询数据库@A@");
            } else {
                //4.表示redis缓存中有数据
                Class returnType = getClass(joinPoint);
                resultData = ObjectMapperUtil.toObject(resultJson, returnType);
                System.out.println("aop查询缓存@A@");
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new RuntimeException(throwable);
        }
        return resultData;
    }


    /**
     * key的定义规则如下:
     * 1.如果用户使用AUTO.则自动生成KEY 方法名_第一个参数
     * 2.如果用户使用EMPTY,使用用户自己的key
     *
     * @param joinPoint
     * @param cache_find
     * @return
     */
    private String getKey(ProceedingJoinPoint joinPoint, Cache_Find cache_find) {
        //1.判断用户选择类型
        if (KEY_ENUM.EMPTY.equals(cache_find.keyType())) {
            return cache_find.key();
        }
        //2.表示用户动态生成key  findITemCat::0
        String methodName = joinPoint.getSignature().getName();
        String args0 = String.valueOf(joinPoint.getArgs()[0]);
        return methodName + "::" + args0;
    }

    /**
     * 表示获取方法对象的返回值类型
     *
     * @param joinPoint
     * @return
     */
    private Class getClass(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getReturnType();
    }
}
