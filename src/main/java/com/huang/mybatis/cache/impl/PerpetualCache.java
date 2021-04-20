package com.huang.mybatis.cache.impl;

import com.huang.mybatis.cache.Cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: hsz
 * @date: 2021/4/20 13:39
 * @description: 最终缓存数据的对象
 */

public class PerpetualCache implements Cache {

    private final String id;

    private final Map<Object, Object> cache = new HashMap<>();

    public PerpetualCache(String id) {
        this.id = id;
    }

    /**
     * @return 缓存对象的唯一id
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * 添加缓存数据
     *
     * @param key   缓存的key
     * @param value 缓存的value
     */
    @Override
    public void putObject(Object key, Object value) {
        cache.put(key, value);
    }

    /**
     * 获取缓存
     *
     * @param key 缓存的key
     * @return key对应的缓存数据
     */
    @Override
    public Object getObject(Object key) {
        return cache.get(key);
    }

    /**
     * 删除缓存
     *
     * @param key 要删除缓存的key
     * @return 删除的缓存
     */
    @Override
    public Object removeObject(Object key) {
        return cache.remove(key);
    }

    /**
     * 清空缓存
     */
    @Override
    public void clear() {
        cache.clear();
    }

    /**
     * @return 缓存大小
     */
    @Override
    public int getSize() {
        return cache.size();
    }
}
