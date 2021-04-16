package com.huang.mybatis.session;

import java.io.Closeable;
import java.util.List;
import java.util.Map;

/**
 * @author: hsz
 * @date: 2021/4/14 17:19
 * @description:
 */

public interface SqlSession extends Closeable {

    /**
     * 查询单条
     */
    <T> T selectOne(String statement);

    /**
     * 查询单条
     */
    <T> T selectOne(String statement, Map<String, Object> parameters);

    <E> List<E> selectList(String statement);

    <E> List<E> selectList(String statement, Map<String, Object> parameters);

    int insert(String statement, Map<String, Object> params);

    int update(String statement, Map<String, Object> params);

    int delete(String statement, Map<String, Object> parameters);

    <T> T getMapper(Class<T> type);

    Configuration getConfiguration();
}
