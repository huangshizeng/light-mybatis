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
     */
    @Override
    public <T> T selectOne(String statement) {
        return null;
    }

    /**
     * 查询单条
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

    @Override
    public <E> List<E> selectList(String statement) {
        return null;
    }

    @Override
    public <E> List<E> selectList(String statement, Map<String, Object> parameters) {
        MappedStatement mappedStatement = configuration.getMappedStatement(statement);
        try {
            return executor.query(mappedStatement, parameters);
        } catch (Exception ex) {
            throw new RuntimeException("Error querying database.  Cause: " + ex, ex);
        }
    }

    @Override
    public int insert(String statement, Map<String, Object> parameters) {
        return update(statement, parameters);
    }

    @Override
    public int update(String statement, Map<String, Object> parameters) {
        MappedStatement mappedStatement = configuration.getMappedStatement(statement);
        try {
            return executor.update(mappedStatement, parameters);
        } catch (Exception ex) {
            throw new RuntimeException("Error updating database.  Cause: " + ex, ex);
        }
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return configuration.getMapper(type, this);
    }

    @Override
    public Configuration getConfiguration() {
        return this.configuration;
    }

    @Override
    public void close() throws IOException {

    }
}
