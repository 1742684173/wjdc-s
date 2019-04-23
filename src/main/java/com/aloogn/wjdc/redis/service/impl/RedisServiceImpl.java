package com.aloogn.wjdc.redis.service.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.aloogn.wjdc.redis.service.RedisService;

import javax.annotation.Resource;
import java.util.List;
import java.util.logging.Logger;

@Component("myRedisTakes")
public class RedisServiceImpl implements RedisService<String,String,Object>{
	
    @Resource(name="redisTemplate")
    private RedisTemplate redisTemplate;

    private Logger logger = Logger.getLogger(String.valueOf(RedisServiceImpl.class));
    
    public void add(String key, String value) {
        if(redisTemplate==null){
            logger.warning("redisTemplate 实例化失败");
            return;
        }else{
           redisTemplate.opsForValue().set(key,value);
        }
    }

    public void addObj(String objectKey, String key, Object object) {
        if(redisTemplate==null){
            logger.warning("redisTemplate 实例化失败");
            return;
        }else{
            redisTemplate.opsForHash().put(objectKey,key,object);
        }
    }

    public void delete(String key) {
    	if(redisTemplate==null){
            logger.warning("redisTemplate 实例化失败");
            return;
        }else{
            redisTemplate.delete(key);
        }
    }

    public void delete(List<String> listKeys) {
    	if(redisTemplate==null){
            logger.warning("redisTemplate 实例化失败");
            return;
        }else{
            redisTemplate.delete(listKeys);
        }
    }

    public void deletObj(String objecyKey, String key) {
    	if(redisTemplate==null){
            logger.warning("redisTemplate 实例化失败");
            return;
        }else{
            redisTemplate.opsForHash().delete(objecyKey,key);
        }
    }

    public void update(String key, String value) {

    }

    public void updateObj(String objectKey, String key, Object object) {

    }

    public String get(String key) {
        String value = (String) redisTemplate.opsForValue().get(key);
        return value;
    }

    public Object getObj(String objectKey, String key) {
    	Object object = (Object) redisTemplate.opsForHash().get(objectKey,key);
        return object;
    }
}