package com.ruoyi.common.redis.util;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

/**
 * Redis工具类
 */
@Component
public class RedisUtils
{
    @Autowired
    private RedisTemplate<String, Object>   redisTemplate;

    @Resource(name = "stringRedisTemplate")
    private ValueOperations<String, String> valueOperations;

    /**  默认过期时长，单位：秒 */
    public final static long                DEFAULT_EXPIRE = 60 * 60 * 24;

    /**  不设置过期时长 */
    public final static long                NOT_EXPIRE     = -1;

    /**
     * 插入缓存默认时间
     * @param key 键
     * @param value 值
     * @author zmr
     */
    public void set(String key, Object value)
    {
        set(key, value, DEFAULT_EXPIRE);
    }

    /**
     * 插入缓存
     * @param key 键
     * @param value 值
     * @param expire 过期时间(s)
     * @author zmr
     */
    public void set(String key, Object value, long expire)
    {
        valueOperations.set(key, toJson(value));
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    /**
     * 返回字符串结果
     * @param key 键
     * @return
     * @author zmr
     */
    public String get(String key)
    {
        return valueOperations.get(key);
    }

    /**
     * 返回指定类型结果
     * @param key 键
     * @param clazz 类型class
     * @return
     * @author zmr
     */
    public <T> T get(String key, Class<T> clazz)
    {
        //["system:user:resetPwd","system:post:list","monitor:operlog:export","system:post:view","system:dept:remove","system:districts:add","system:menu:list","system:dict:edit","monitor:logininfor:remove","monitor:job:list","system:dict:view","system:user:add","system:notice:remove","system:user:export","system:role:remove","monitor:job:edit","monitor:logininfor:view","system:menu:view","system:dict:list","monitor:online:forceLogout","monitor:online:view","system:districts:edit","system:oss:config","system:notice:list","system:districts:remove","system:notice:view","tool:gen:view","system:notice:edit","monitor:online:list","system:post:edit","monitor:job:add","system:districts:list","monitor:logininfor:list","monitor:job:view","tool:gen:list","system:dict:export","system:post:remove","system:config:edit","system:config:view","tool:swagger:view","system:user:remove","system:menu:add","system:config:list","system:role:list","monitor:job:detail","system:user:import","system:dict:remove","system:role:view","system:oss:add","system:user:edit","system:post:export","system:config:export","system:role:edit","monitor:operlog:view","system:dept:list","monitor:server:view","system:dept:view","monitor:data:view","monitor:operlog:remove","system:districts:view","monitor:operlog:list","system:role:add","system:menu:remove","system:dict:add","system:oss:remove","monitor:logininfor:export","system:dept:edit","system:post:add","monitor:job:changeStatus","system:user:view","system:user:list","system:notice:add","monitor:job:remove","system:role:export","system:config:add","monitor:operlog:detail","monitor:online:batchForceLogout","tool:gen:code","monitor:job:export","system:menu:edit","system:dept:add","system:config:remove"]
        String value = valueOperations.get(key);
        return value == null ? null : fromJson(value, clazz);
    }

    /**
     * 删除缓存
     * @param key 键
     * @author zmr
     */
    public void delete(String key)
    {
        redisTemplate.delete(key);
    }

    /**
     * Object转成JSON数据
     */
    private String toJson(Object object)
    {
        if (object instanceof Integer || object instanceof Long || object instanceof Float || object instanceof Double
                || object instanceof Boolean || object instanceof String)
        {
            return String.valueOf(object);
        }
        return JSON.toJSONString(object);
    }

    /**
     * JSON数据，转成Object
     */
    private <T> T fromJson(String json, Class<T> clazz)
    {
        return JSON.parseObject(json, clazz);
    }
}