package com.huang.mybatis.executor.statement;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author: hsz
 * @date: 2021/4/16 10:04
 * @description:
 */

public interface StatementHandler {

    Statement prepare(Connection connection) throws SQLException;

    void parameterize(Statement stmt) throws SQLException;

    <E> List<E> query(Statement stmt) throws SQLException, InstantiationException, IllegalAccessException;

    int update(Statement stmt) throws SQLException;
}
