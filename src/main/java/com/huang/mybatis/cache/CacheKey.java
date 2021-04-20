package com.huang.mybatis.cache;

import com.huang.mybatis.binding.ParameterMapping;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author: hsz
 * @date: 2021/4/20 14:09
 * @description:
 */

@Setter
@Getter
public class CacheKey {

    private String statementId;

    private String sql;

    private List<ParameterMapping> parameterMappings;

    private Map<String, Object> parameterMap;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CacheKey key = (CacheKey) o;
        if (!statementId.equals(key.getStatementId())) {
            return false;
        }
        if (!sql.equals(key.sql)) {
            return false;
        }

        for (ParameterMapping mapping : parameterMappings) {
            if (!parameterMap.get(mapping.getProperty()).equals(key.getParameterMap().get(mapping.getProperty()))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(statementId, sql);
    }
}
