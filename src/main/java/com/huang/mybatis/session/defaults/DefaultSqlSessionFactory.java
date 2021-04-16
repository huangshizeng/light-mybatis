package com.huang.mybatis.session.defaults;

import com.huang.mybatis.executor.Executor;
import com.huang.mybatis.executor.SimpleExecutor;
import com.huang.mybatis.session.Configuration;
import com.huang.mybatis.session.SqlSession;
import com.huang.mybatis.session.SqlSessionFactory;

/**
 * @author: hsz
 * @date: 2021/4/14 09:13
 * @description:
 */

public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        final Executor executor = new SimpleExecutor(configuration);
        return new DefaultSqlSession(configuration, executor);
    }
}
