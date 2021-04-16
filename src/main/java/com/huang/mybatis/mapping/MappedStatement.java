package com.huang.mybatis.mapping;

import com.huang.mybatis.session.Configuration;
import lombok.Getter;

/**
 * @author: hsz
 * @date: 2021/4/14 16:18
 * @description:
 */

@Getter
public final class MappedStatement {

    private Configuration configuration;

    private String id;

    private String sql;

    private Class<?> parameterTypeClass;
    /**
     * 返回类型
     */
    private Class<?> resultTypeClass;

    /**
     * sqlCommandType对应select/update/insert等
     */
    private SqlCommandType sqlCommandType;

    public static class Builder {

        private MappedStatement mappedStatement = new MappedStatement();

        public Builder(Configuration configuration, String id, String sql, SqlCommandType sqlCommandType) {
            mappedStatement.configuration = configuration;
            mappedStatement.id = id;
            mappedStatement.sql = sql;
            mappedStatement.sqlCommandType = sqlCommandType;
        }

        public Builder parameterTypeClass(Class<?> parameterTypeClass) {
            mappedStatement.parameterTypeClass = parameterTypeClass;
            return this;
        }

        public Builder resultTypeClass(Class<?> resultTypeClass) {
            mappedStatement.resultTypeClass = resultTypeClass;
            return this;
        }

        public MappedStatement build() {
            return mappedStatement;
        }
    }
}
