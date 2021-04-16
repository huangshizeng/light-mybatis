package com.huang.mybatis.executor.result;

import com.huang.mybatis.mapping.MappedStatement;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: hsz
 * @date: 2021/4/16 11:19
 * @description:
 */

public class DefaultResultSetHandler implements ResultSetHandler {

    private final MappedStatement mappedStatement;

    public DefaultResultSetHandler(MappedStatement mappedStatement) {
        this.mappedStatement = mappedStatement;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E> List<E> handleResultSets(ResultSet resultSet) throws SQLException, IllegalAccessException, InstantiationException {
        if (resultSet == null) {
            return null;
        }
        List<E> result = new ArrayList<>();
        Class<?> resultTypeClass = mappedStatement.getResultTypeClass();
        // 目前只支持int，string和自定义对象类型
        while (resultSet.next()) {
            if (Integer.class.equals(resultTypeClass)) {
                result.add((E) Integer.valueOf(resultSet.getInt(1)));
            } else if (String.class.equals(resultTypeClass)) {
                result.add((E) resultSet.getString(1));
            } else {
                E entity = (E) resultTypeClass.newInstance();
                Field[] declaredFields = resultTypeClass.getDeclaredFields();
                for (Field field : declaredFields) {
                    field.setAccessible(true);
                    field.set(entity, resultSet.getObject(field.getName()));
                }
                result.add(entity);
            }
        }
        return result;
    }
}
