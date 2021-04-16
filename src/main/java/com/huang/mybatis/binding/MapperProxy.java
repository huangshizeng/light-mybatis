package com.huang.mybatis.binding;

import com.huang.mybatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author: hsz
 * @date: 2021/4/15 10:49
 * @description:
 */

public class MapperProxy<T> implements InvocationHandler {

    private final SqlSession sqlSession;
    private final Class<T> mapperInterface;

    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    /**
     * 执行代理对象，开始执行SQL
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        try {
            // 如果是Object的默认方法，直接反射调用
            if (Object.class.equals(method.getDeclaringClass())) {
                return method.invoke(this, args);
            } else {
                return new MapperMethod(mapperInterface, method, sqlSession.getConfiguration()).execute(sqlSession, args);
            }
        } catch (Throwable t) {
            t.printStackTrace();
            throw new RuntimeException();
        }
    }


}
