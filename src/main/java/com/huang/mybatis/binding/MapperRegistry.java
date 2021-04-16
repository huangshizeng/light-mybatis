package com.huang.mybatis.binding;

import com.huang.mybatis.session.SqlSession;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: hsz
 * @date: 2021/4/13 19:08
 * @description:
 */

public class MapperRegistry {

    /**
     * 存放mapper映射，key是mapper接口
     */
    private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<>();

    /**
     * 注册代理工厂
     *
     * @param type
     */
    public <T> void addMapper(Class<T> type) {
        this.knownMappers.put(type, new MapperProxyFactory<>(type));
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) knownMappers.get(type);
        return mapperProxyFactory.newInstance(sqlSession);
    }
}
