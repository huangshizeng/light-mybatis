package com.huang.mybatis.builder.xml;

import com.huang.mybatis.builder.BaseBuilder;
import com.huang.mybatis.mapping.MappedStatement;
import com.huang.mybatis.mapping.SqlCommandType;
import com.huang.mybatis.session.Configuration;
import org.dom4j.Element;

import java.util.Locale;

/**
 * @author: hsz
 * @date: 2021/4/14 14:53
 * @description:
 */

public class XMLStatementBuilder extends BaseBuilder {

    private Element context;
    private String namespace;

    public XMLStatementBuilder(Configuration configuration, Element context, String namespace) {
        super(configuration);
        this.context = context;
        this.namespace = namespace;
    }

    public void parseStatement() {
        try {
            // sql id
            String id = context.attributeValue("id");
            // 节点名称，比如select，update，delete
            String nodeName = context.getName();
            // 根据节点名称判断sql类型
            SqlCommandType sqlCommandType = SqlCommandType.valueOf(nodeName.toUpperCase(Locale.ENGLISH));
            // 参数类型
            String parameterType = context.attributeValue("parameterType");
            // 将参数类型别名转为Class
            Class<?> parameterTypeClass = resolveClass(parameterType);
            String sql = context.getTextTrim();
            String resultType = context.attributeValue("resultType");
            Class<?> resultTypeClass = resolveClass(resultType);
            // 将以上的属性封装为MappedStatement
            MappedStatement mappedStatement = new MappedStatement.Builder(configuration, namespace + "." + id, sql, sqlCommandType)
                    .parameterTypeClass(parameterTypeClass)
                    .resultTypeClass(resultTypeClass)
                    .build();
            // 将MappedStatement对象添加到configuration中
            configuration.addMappedStatement(mappedStatement);
            configuration.addMapper(Class.forName(namespace));
        } catch (Exception ex) {
            throw new RuntimeException("parseStatement error", ex);
        }
    }
}
