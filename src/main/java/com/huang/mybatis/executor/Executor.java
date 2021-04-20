package com.huang.mybatis.executor;

import com.huang.mybatis.binding.BoundSql;
import com.huang.mybatis.cache.CacheKey;
import com.huang.mybatis.mapping.MappedStatement;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author: hsz
 * @date: 2021/4/15 10:31
 * @description:
 */

public interface Executor {

    int update(MappedStatement ms, Map<String, Object> parameter) throws SQLException;

    <E> List<E> query(MappedStatement ms, Map<String, Object> parameter) throws SQLException;

    <E> List<E> query(MappedStatement ms, Map<String, Object> parameter, CacheKey key, BoundSql boundSql) throws SQLException;

    int update(MappedStatement ms, Map<String, Object> parameter, BoundSql boundSql) throws SQLException;

    CacheKey createCacheKey(MappedStatement ms, BoundSql boundSql);
}
