package com.huang.mybatis.session;

import com.huang.mybatis.builder.xml.XmlConfigBuilder;
import com.huang.mybatis.session.defaults.DefaultSqlSessionFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author: hsz
 * @date: 2021/4/13 16:11
 * @description:
 */

public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream inputStream) {
        try {
            XmlConfigBuilder parser = new XmlConfigBuilder(inputStream);
            return build(parser.parse());
        } catch (Exception e) {
            throw new RuntimeException("Error building SqlSession.", e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
            }
        }
    }

    public SqlSessionFactory build(Configuration config) {
        return new DefaultSqlSessionFactory(config);
    }
}
