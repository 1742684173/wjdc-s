package com.aloogn.wjdc.redis.service.impl;

import com.aloogn.wjdc.redis.service.exception.RedisException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.aloogn.wjdc.redis.service.RedisService;

import javax.annotation.Resource;
import java.util.List;

@Component("myRedisTakes")
public class RedisServiceImpl implements RedisService<String,String,Object>{
    private static Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);
	
    @Resource(name="redisTemplate")
    private RedisTemplate redisTemplate;

    public void add(String key, String value) throws RedisException{
        try{
            redisTemplate.opsForValue().set(key,value);
        }catch (Exception e){
            logger.debug("--------add redis-----发生异常："+e.getCause().getMessage());
            throw new RedisException("新增失败");
        }

    }

    public void addObj(String objectKey, String key, Object object) throws RedisException {
        try{
            redisTemplate.opsForHash().put(objectKey,key,object);
        }catch (Exception e){
            logger.debug("--------addObj redis-----发生异常："+e.getCause().getMessage());
            throw new RedisException("新增失败");
        }

    }

    public void delete(String key) throws RedisException{
        try{
            redisTemplate.delete(key);
        }catch (Exception e){
            logger.debug("--------dedelete(String key) redis发生异常-----"+e.getCause().getMessage());
            throw new RedisException("删除失败");
        }
    }

    public void delete(List<String> listKeys) throws RedisException{
        try{
            redisTemplate.delete(listKeys);
        }catch (Exception e){
            logger.debug("--------delete(List<String> listKeys)发生异常-----"+e.getCause().getMessage());
            throw new RedisException("删除失败");
        }
    }

    public void deleteObj(String objecyKey, String key) throws RedisException {
        try{
            redisTemplate.opsForHash().delete(objecyKey,key);
        }catch (Exception e){
            logger.debug("--------deletObj(String objecyKey, String key)发生异常-----"+e.getCause().getMessage());
            throw new RedisException("删除失败");
        }
    }

    public void update(String key, String value) throws RedisException {

    }

    public void updateObj(String objectKey, String key, Object object) throws RedisException {

    }

    public String get(String key) throws RedisException {
        String value = null;
        try{
            value = (String) redisTemplate.opsForValue().get(key);
        }catch (Exception e){
            logger.error("--------get(String key)发生异常-----"+e.getCause().getMessage());
            throw new RedisException("查询失败");
        }

        return value;
    }

    public Object getObj(String objectKey, String key) throws RedisException {

        Object value = null;
        try{
            value = (Object) redisTemplate.opsForHash().get(objectKey,key);
        }catch (Exception e){
            logger.error("--------getObj(String objectKey, String key)发生异常-----"+e.getCause().getMessage());
            throw new RedisException("查询失败");
        }

        return value;
    }
}