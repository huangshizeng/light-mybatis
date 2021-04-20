package com.huang.mybatis.cache;

/**
 * @author: hsz
 * @date: 2021/4/20 13:33
 * @description: 缓存接口
 */

public interface Cache {

    /**
     * @return 缓存对象的唯一id
     */
    String getId();

    /**
     * 添加缓存数据
     *
     * @param key   缓存的key
     * @param value 缓存的value
     */
    void putObject(Object key, Object value);

    /**
     * 获取缓存
     *
     * @param key 缓存的key
     * @return key对应的缓存数据
     */
    Object getObject(Object key);

    /**
     * 删除缓存
     *
     * @param key 要删除缓存的key
     * @return 删除的缓存
     */
    Object removeObject(Object key);

    /**
     * 清空缓存
     */
    void clear();

    /**
     * @return 缓存大小
     */
    int getSize();
}
