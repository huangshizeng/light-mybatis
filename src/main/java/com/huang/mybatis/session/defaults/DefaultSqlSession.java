package com.huang.mybatis.session.defaults;

import com.huang.mybatis.executor.Executor;
import com.huang.mybatis.mapping.MappedStatement;
import com.huang.mybatis.session.Configuration;
import com.huang.mybatis.session.SqlSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author: hsz
 * @date: 2021/4/15 10:34
 * @description:
 */

public class DefaultSqlSession implements SqlSession {

    private final Configuration configuration;
    private final Executor executor;

    public DefaultSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    /**
     * 查询单条
     *
     * @param statement statement id
     * @return Mapped object
     */
    @Override
    public <T> T selectOne(String statement) {
        return null;
    }

    /**
     * 查询单条
     *
     * @param statement  Unique identifier matching the statement to use.
     * @param parameters A parameter object to pass to the statement.
     * @return Mapped object
     */
    @Override
    public <T> T selectOne(String statement, Map<String, Object> parameters) {
        List<T> list = this.selectList(statement, parameters);
        if (list.size() == 1) {
            return list.get(0);
        } else if (list.size() > 1) {
            throw new RuntimeException("Expected one result (or null) to be returned by selectOne(), but found: " + list.size());
        } else {
            return null;
        }
    }

    /**
     * Retrieve a list of mapped objects from the statement key.
     *
     * @param statement Unique identifier matching the statement to use.
     * @return List of mapped object
     */
    @Override
    public <E> List<E> selectList(String statement) {
        return null;
    }

    /**
     * Retrieve a list of mapped objects from the statement key and parameter.
     *
     * @param statement  Unique identifier matching the statement to use.
     * @param parameters A parameter object to pass to the statement.
     * @return List of mapped object
     */
    @Override
    public <E> List<E> selectList(String statement, Map<String, Object> parameters) {
        MappedStatement mappedStatement = configuration.getMappedStatement(statement);
        try {
            return executor.query(mappedStatement, parameters);
        } catch (Exception ex) {
            throw new RuntimeException("Error querying database.  Cause: " + ex, ex);
        }
    }

    /**
     * Retrieves a mapper.
     *
     * @param type Mapper interface class
     * @return a mapper bound to this SqlSession
     */
    @Override
    public <T> T getMapper(Class<T> type) {
        return configuration.getMapper(type, this);
    }

    @Override
    public Configuration getConfiguration() {
        return this.configuration;
    }

    /**
     * Closes this stream and releases any system resources associated
     * with it. If the stream is already closed then invoking this
     * method has no effect.
     *
     * <p> As noted in {@link AutoCloseable#close()}, cases where the
     * close may fail require careful attention. It is strongly advised
     * to relinquish the underlying resources and to internally
     * <em>mark</em> the {@code Closeable} as closed, prior to throwing
     * the {@code IOException}.
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void close() throws IOException {

    }
}
