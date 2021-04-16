package com.huang.mybatis.binding;

import com.huang.mybatis.annotation.Param;
import com.huang.mybatis.mapping.MappedStatement;
import com.huang.mybatis.session.Configuration;
import com.huang.mybatis.session.SqlSession;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: hsz
 * @date: 2021/4/15 10:53
 * @description:
 */

public class MapperMethod {

    private final Method method;
    private final MappedStatement statement;

    public <T> MapperMethod(Class<T> mapperInterface, Method method, Configuration configuration) {
        this.method = method;
        statement = configuration.getMappedStatement(mapperInterface.getName() + "." + method.getName());
    }

    /**
     * 真正执行sql
     */
    public Object execute(SqlSession sqlSession, Object[] args) {
        Object result;
        // 解析参数，将参数封装成map
        Map<String, Object> parameters = convertArgsToParam(args);
        // sql类型
        switch (statement.getSqlCommandType()) {
            // 插入
            case INSERT: {
                result = rowCountResult(sqlSession.insert(statement.getId(), parameters));
                break;
            }
            // 更新
            case UPDATE: {
                result = rowCountResult(sqlSession.update(statement.getId(), parameters));
                break;
            }
            // 删除
            case DELETE: {
                result = rowCountResult(sqlSession.delete(statement.getId(), parameters));
                break;
            }
            // 查询，只支持单条查询和多条list查询
            case SELECT:
                // 判断返回值类型
                if (Collection.class.isAssignableFrom(method.getReturnType())) {
                    // 返回多条
                    // selectOne底层也是通过selectList，只是获取第一个结果
                    result = sqlSession.selectList(statement.getId(), parameters);
                } else {
                    // 返回单行
                    // selectOne底层也是通过selectList，只是获取第一个结果
                    result = sqlSession.selectOne(statement.getId(), parameters);
                }
                break;
            default:
                throw new RuntimeException("Unknown execution method for: " + statement.getSqlCommandType().name());
        }
        if (result == null && method.getReturnType().isPrimitive()) {
            throw new RuntimeException("Mapper method '" + statement.getId()
                    + " attempted to return null from a method with a primitive return type (" + method.getReturnType() + ").");
        }
        return result;
    }

    private Object rowCountResult(int rowCount) {
        Class<?> returnType = method.getReturnType();
        final Object result;
        if (void.class.equals(returnType)) {
            result = null;
        } else if (Integer.class.equals(returnType) || Integer.TYPE.equals(returnType)) {
            result = rowCount;
        } else if (Long.class.equals(returnType) || Long.TYPE.equals(returnType)) {
            result = (long) rowCount;
        } else if (Boolean.class.equals(returnType) || Boolean.TYPE.equals(returnType)) {
            result = rowCount > 0;
        } else {
            throw new RuntimeException("Mapper method '" + statement.getId() + "' has an unsupported return type: " + returnType);
        }
        return result;
    }

    private Map<String, Object> convertArgsToParam(Object[] args) {
        Parameter[] parameters = method.getParameters();
        Map<String, Object> params = new HashMap<>(parameters.length);
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].isAnnotationPresent(Param.class)) {
                params.put(parameters[i].getAnnotation(Param.class).value(), args[i]);
            } else {
                params.put("arg" + i, args[i]);
            }
        }
        return params;
    }
}
