package com.huang.mybatis.executor;

import com.huang.mybatis.binding.BoundSql;
import com.huang.mybatis.cache.CacheKey;
import com.huang.mybatis.cache.impl.PerpetualCache;
import com.huang.mybatis.mapping.MappedStatement;
import com.huang.mybatis.session.Configuration;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author: hsz
 * @date: 2021/4/15 10:37
 * @description:
 */

public abstract class BaseExecutor implements Executor {

    protected Configuration configuration;

    protected PerpetualCache localCache;

    public BaseExecutor(Configuration configuration) {
        this.configuration = configuration;
        this.localCache = new PerpetualCache("localCache");
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Map<String, Object> parameter) throws SQLException {
        BoundSql boundSql = new BoundSql(ms.getConfiguration(), ms.getSql(), parameter);
        // 根据StatementId，使用的参数，sql来生成一个缓存的key
        CacheKey key = createCacheKey(ms, boundSql);
        return query(ms, parameter, key, boundSql);
    }

    @Override
    public CacheKey createCacheKey(MappedStatement ms, BoundSql boundSql) {
        CacheKey key = new CacheKey();
        key.setStatementId(ms.getId());
        key.setSql(boundSql.getSql());
        key.setParameterMap(boundSql.getParameterMap());
        key.setParameterMappings(boundSql.getParameterMappings());
        return key;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E> List<E> query(MappedStatement ms, Map<String, Object> parameter, CacheKey key, BoundSql boundSql) {
        // 从一级缓存中查询
        List<E> list = (List<E>) localCache.getObject(key);
        // 如果一级缓存中查询到，则直接返回，否则到数据库中查询
        if (list != null) {
            System.out.println("一级缓存命中");
            return list;
        }
        return queryFromDatabase(ms, parameter, key, boundSql);
    }

    private <E> List<E> queryFromDatabase(MappedStatement ms, Map<String, Object> parameter, CacheKey key, BoundSql boundSql) {
        System.out.println("去数据库查询。。。");
        List<E> list = doQuery(ms, parameter, boundSql);
        localCache.putObject(key, list);
        return list;
    }

    protected abstract <E> List<E> doQuery(MappedStatement ms, Map<String, Object> parameter, BoundSql boundSql);

    @Override
    public int update(MappedStatement ms, Map<String, Object> parameter, BoundSql boundSql) {
        return doUpdate(ms, parameter, boundSql);
    }

    protected abstract int doUpdate(MappedStatement ms, Map<String, Object> parameter, BoundSql boundSql);
}
