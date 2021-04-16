package com.huang.mybatis.executor;

import com.huang.mybatis.binding.BoundSql;
import com.huang.mybatis.mapping.MappedStatement;
import com.huang.mybatis.session.Configuration;

import java.util.List;
import java.util.Map;

/**
 * @author: hsz
 * @date: 2021/4/15 10:37
 * @description:
 */

public abstract class BaseExecutor implements Executor {

    protected Configuration configuration;

    public BaseExecutor(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Map<String, Object> parameter, BoundSql boundSql) {
        return queryFromDatabase(ms, parameter, boundSql);
    }

    private <E> List<E> queryFromDatabase(MappedStatement ms, Map<String, Object> parameter, BoundSql boundSql) {
        return doQuery(ms, parameter, boundSql);
    }

    protected abstract <E> List<E> doQuery(MappedStatement ms, Map<String, Object> parameter, BoundSql boundSql);
}
