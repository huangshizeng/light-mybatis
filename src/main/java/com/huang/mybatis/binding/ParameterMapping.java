package com.huang.mybatis.binding;

import cn.hutool.db.meta.JdbcType;
import com.huang.mybatis.session.Configuration;
import lombok.Getter;

/**
 * @author: hsz
 * @date: 2021/4/15 18:14
 * @description:
 */

@Getter
public class ParameterMapping {

    private Configuration configuration;

    private String property;
    private Class<?> javaType = Object.class;
    private JdbcType jdbcType;

    public static class Builder {

        private ParameterMapping parameterMapping = new ParameterMapping();

        public Builder(Configuration configuration) {
            parameterMapping.configuration = configuration;
        }

        public Builder property(String property) {
            parameterMapping.property = property;
            return this;
        }

        public Builder javaType(Class<?> javaType) {
            parameterMapping.javaType = javaType;
            return this;
        }

        public Builder jdbcType(JdbcType jdbcType) {
            parameterMapping.jdbcType = jdbcType;
            return this;
        }

        public ParameterMapping build() {
            return parameterMapping;
        }
    }
}
