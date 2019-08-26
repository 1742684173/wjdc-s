package com.aloogn.wjdc.redis.service;

import com.aloogn.wjdc.redis.service.exception.RedisException;

import java.util.List;

public interface RedisService<H,K,V> {
    //增
    public void add(K key,String value) throws RedisException;
    public void addObj(H objectKey,K key,V object) throws RedisException;
    //删
    public void delete(K key) throws RedisException;
    public void delete(List<K> listKeys) throws RedisException;
    public void deletObj(H objecyKey,K key) throws RedisException;
    //改
    public void update(K key,String value) throws RedisException;
    public void updateObj(H objectKey,K key,V object) throws RedisException;
    
    /**
     * 重名名key，如果newKey已经存在，则newKey的原值被覆盖
     *
     * @param oldKey
     * @param newKey
     */
    //public void renameKey(H oldKey, K newKey);
    
    //查
    public String get(K key) throws RedisException;
    public V getObj(H objectKey,K key) throws RedisException;
}
