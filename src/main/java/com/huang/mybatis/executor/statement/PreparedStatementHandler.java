package com.huang.mybatis.executor.statement;

import cn.hutool.core.util.ObjectUtil;
import com.huang.mybatis.binding.BoundSql;
import com.huang.mybatis.binding.ParameterMapping;
import com.huang.mybatis.executor.Executor;
import com.huang.mybatis.executor.result.DefaultResultSetHandler;
import com.huang.mybatis.executor.result.ResultSetHandler;
import com.huang.mybatis.mapping.MappedStatement;
import com.huang.mybatis.session.Configuration;

import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * @author: hsz
 * @date: 2021/4/16 10:07
 * @description:
 */

public class PreparedStatementHandler implements StatementHandler {

    protected final Configuration configuration;
    protected final ResultSetHandler resultSetHandler;
//    protected final ParameterHandler parameterHandler;

    protected final Executor executor;
    protected final MappedStatement mappedStatement;

    protected BoundSql boundSql;

    public PreparedStatementHandler(Configuration configuration, MappedStatement mappedStatement, Executor executor, BoundSql boundSql) {
        this.configuration = configuration;
        this.mappedStatement = mappedStatement;
        this.executor = executor;
        this.boundSql = boundSql;
        this.resultSetHandler = new DefaultResultSetHandler(mappedStatement);
    }

    @Override
    public Statement prepare(Connection connection) throws SQLException {
        return connection.prepareStatement(boundSql.getSql());
    }

    @Override
    public void parameterize(Statement stmt) throws SQLException {
        PreparedStatement ps = (PreparedStatement) stmt;
        Map<String, Object> parameterMap = boundSql.getParameterMap();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        for (int i = 0; i < parameterMappings.size(); i++) {
            String property = parameterMappings.get(i).getProperty();
            Object value = parameterMap.get(property);
            if (ObjectUtil.isEmpty(value)) {
                throw new RuntimeException("无法为" + property + "找到匹配的参数值");
            }
            ps.setObject(i + 1, value);
        }
    }

    @Override
    public <E> List<E> query(Statement stmt) throws SQLException, InstantiationException, IllegalAccessException {
        PreparedStatement ps = (PreparedStatement) stmt;
        ResultSet resultSet = ps.executeQuery();
        // 处理结果集
        return resultSetHandler.handleResultSets(resultSet);
    }

    @Override
    public int update(Statement stmt) throws SQLException {
        PreparedStatement ps = (PreparedStatement) stmt;
        return ps.executeUpdate();
    }
}
