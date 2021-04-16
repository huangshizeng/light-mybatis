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
     *
     * @param statement statement id
     * @return Mapped object
     */
    <T> T selectOne(String statement);

    /**
     * 查询单条
     *
     * @param <T>        the returned object type
     * @param statement  Unique identifier matching the statement to use.
     * @param parameters A parameter object to pass to the statement.
     * @return Mapped object
     */
    <T> T selectOne(String statement, Map<String, Object> parameters);

    /**
     * Retrieve a list of mapped objects from the statement key.
     *
     * @param <E>       the returned list element type
     * @param statement Unique identifier matching the statement to use.
     * @return List of mapped object
     */
    <E> List<E> selectList(String statement);

    /**
     * Retrieve a list of mapped objects from the statement key and parameter.
     *
     * @param <E>        the returned list element type
     * @param statement  Unique identifier matching the statement to use.
     * @param parameters A parameter object to pass to the statement.
     * @return List of mapped object
     */
    <E> List<E> selectList(String statement, Map<String, Object> parameters);

    /**
     * Retrieves a mapper.
     *
     * @param <T>  the mapper type
     * @param type Mapper interface class
     * @return a mapper bound to this SqlSession
     */
    <T> T getMapper(Class<T> type);

    Configuration getConfiguration();
}
