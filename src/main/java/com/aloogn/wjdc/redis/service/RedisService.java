package com.aloogn.wjdc.redis.service;

import java.util.List;

public interface RedisService<H,K,V> {
    //增
    public void add(K key,String value);
    public void addObj(H objectKey,K key,V object);
    //删
    public void delete(K key);
    public void delete(List<K> listKeys);
    public void deletObj(H objecyKey,K key);
    //改
    public void update(K key,String value);
    public void updateObj(H objectKey,K key,V object);
    
    /**
     * 重名名key，如果newKey已经存在，则newKey的原值被覆盖
     *
     * @param oldKey
     * @param newKey
     */
    //public void renameKey(H oldKey, K newKey);
    
    //查
    public String get(K key);
    public V getObj(H objectKey,K key);
}
