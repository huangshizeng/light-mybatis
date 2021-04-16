package com.huang.mybatis.binding;

import com.huang.mybatis.session.SqlSession;

import java.lang.reflect.Proxy;

/**
 * @author: hsz
 * @date: 2021/4/14 16:16
 * @description:
 */

public class MapperProxyFactory<T> {

    private final Class<T> mapperInterface;

    public MapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    protected T newInstance(MapperProxy<T> mapperProxy) {
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, mapperProxy);
    }

    public T newInstance(SqlSession sqlSession) {
        final MapperProxy<T> mapperProxy = new MapperProxy<>(sqlSession, mapperInterface);
        return newInstance(mapperProxy);
    }
}
