package com.huang.mybatis.executor;

import com.huang.mybatis.binding.BoundSql;
import com.huang.mybatis.executor.statement.PreparedStatementHandler;
import com.huang.mybatis.executor.statement.StatementHandler;
import com.huang.mybatis.mapping.MappedStatement;
import com.huang.mybatis.session.Configuration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

/**
 * @author: hsz
 * @date: 2021/4/15 10:37
 * @description:
 */

public class SimpleExecutor extends BaseExecutor {

    public SimpleExecutor(Configuration configuration) {
        super(configuration);
    }

    @Override
    public int update(MappedStatement ms, Map<String, Object> parameter) throws SQLException {
        return 0;
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Map<String, Object> parameter) throws SQLException {
        BoundSql boundSql = new BoundSql(ms.getConfiguration(), ms.getSql(), parameter);
        return query(ms, parameter, boundSql);
    }

    @Override
    protected <E> List<E> doQuery(MappedStatement ms, Map<String, Object> parameter, BoundSql boundSql) {
        Statement stmt = null;
        try {
            StatementHandler handler = new PreparedStatementHandler(configuration, ms, this, boundSql);
            // 创建Statement，预编译sql并设置参数
            stmt = prepareStatement(handler);
            // 最终执行sql并处理查询结果
            return handler.query(stmt);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            closeStatement(stmt);
        }
    }

    private Statement prepareStatement(StatementHandler handler) throws SQLException, ClassNotFoundException {
        // 获取连接
        Connection connection = configuration.getDataSource().getConnection();
        // 创建Statement
        Statement stmt = handler.prepare(connection);
        // 设置参数
        handler.parameterize(stmt);
        return stmt;
    }

    private void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                //
            }
        }
    }
}
