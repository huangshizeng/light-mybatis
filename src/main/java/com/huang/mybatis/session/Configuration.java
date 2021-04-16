package com.huang.mybatis.session;

import com.huang.mybatis.binding.MapperRegistry;
import com.huang.mybatis.datasource.DataSource;
import com.huang.mybatis.mapping.MappedStatement;
import com.huang.mybatis.type.TypeAliasRegistry;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author: hsz
 * @date: 2021/4/13 16:13
 * @description:
 */

public class Configuration {

    @Getter
    @Setter
    private DataSource dataSource;

    protected final MapperRegistry mapperRegistry = new MapperRegistry();

    protected final Set<String> loadedResources = new HashSet<>();

    //    protected final TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry(this);
    @Getter
    protected final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();

    /**
     * 代表mapper文件中的每条sql， key是sql id
     */
    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();

    public void addLoadedResource(String resource) {
        loadedResources.add(resource);
    }

    public boolean isResourceLoaded(String resource) {
        return loadedResources.contains(resource);
    }

    public <T> void addMapper(Class<T> type) {
        mapperRegistry.addMapper(type);
    }

    public void addMappedStatement(MappedStatement statement) {
        mappedStatements.put(statement.getId(), statement);
    }

    public MappedStatement getMappedStatement(String statementId) {
        return mappedStatements.get(statementId);
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return mapperRegistry.getMapper(type, sqlSession);
    }
}
