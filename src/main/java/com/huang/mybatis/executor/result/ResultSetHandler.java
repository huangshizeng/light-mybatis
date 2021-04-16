package com.huang.mybatis.executor.result;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author: hsz
 * @date: 2021/4/16 11:16
 * @description:
 */

public interface ResultSetHandler {

    <E> List<E> handleResultSets(ResultSet resultSet) throws SQLException, IllegalAccessException, InstantiationException;
}
