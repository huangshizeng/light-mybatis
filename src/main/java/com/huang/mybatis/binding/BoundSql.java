package com.huang.mybatis.binding;

import com.huang.mybatis.session.Configuration;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: hsz
 * @date: 2021/4/15 18:12
 * @description:
 */

@Getter
public class BoundSql {

    private String sql;
    private List<ParameterMapping> parameterMappings = new ArrayList<>();
    private Map<String, Object> parameterMap;
    private final Configuration configuration;

    /**
     * #{}正则匹配
     */
    private static Pattern param_pattern = Pattern.compile("#\\{([^\\{\\}]*)\\}");

    public BoundSql(Configuration configuration, String sql, Map<String, Object> parameterMap) {
        this.configuration = configuration;
        parseSql(sql);
        this.parameterMap = parameterMap;
    }

    private void parseSql(String sql) {
        sql = sql.trim();
        String[] strings = sql.split("#\\{");
        if (strings.length > 1) {
            for (int i = 1; i < strings.length; i++) {
                String s = strings[i];
                String var = s.substring(0, s.indexOf("}"));
                ParameterMapping parameterMapping = new ParameterMapping.Builder(configuration).property(var).build();
                parameterMappings.add(parameterMapping);
            }
        }
        Matcher matcher = param_pattern.matcher(sql);
        this.sql = matcher.replaceAll("?");
    }
}
