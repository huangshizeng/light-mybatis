package com.huang.mybatis.builder;

import com.huang.mybatis.exception.BuilderException;
import com.huang.mybatis.session.Configuration;
import com.huang.mybatis.type.TypeAliasRegistry;
import lombok.Getter;

/**
 * @author: hsz
 * @date: 2021/4/14 09:38
 * @description:
 */

public abstract class BaseBuilder {

    @Getter
    protected final Configuration configuration;
    protected final TypeAliasRegistry typeAliasRegistry;

    public BaseBuilder(Configuration configuration) {
        this.configuration = configuration;
        this.typeAliasRegistry = configuration.getTypeAliasRegistry();
    }

    protected <T> Class<? extends T> resolveClass(String alias) {
        if (alias == null) {
            return null;
        }
        try {
            return resolveAlias(alias);
        } catch (Exception e) {
            throw new BuilderException("Error resolving class. Cause: " + e, e);
        }
    }

    protected <T> Class<? extends T> resolveAlias(String alias) {
        return typeAliasRegistry.resolveAlias(alias);
    }
}
