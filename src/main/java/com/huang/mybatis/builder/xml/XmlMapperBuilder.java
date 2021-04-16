package com.huang.mybatis.builder.xml;

import com.huang.mybatis.builder.BaseBuilder;
import com.huang.mybatis.session.Configuration;
import com.huang.mybatis.util.XmlUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: hsz
 * @date: 2021/4/14 10:19
 * @description:
 */

public class XmlMapperBuilder extends BaseBuilder {

    private Document document;
    private String resource;

    public XmlMapperBuilder(InputStream inputStream, Configuration configuration, String resource) throws DocumentException {
        super(configuration);
        this.resource = resource;
        this.document = XmlUtil.readXml(inputStream);
    }

    public void parse() {
        if (!configuration.isResourceLoaded(resource)) {
            // 解析mapper
            configurationElement(document.getRootElement());
            configuration.addLoadedResource(resource);
//            bindMapperForNamespace();
        }
    }

    /**
     * 解析mapper文件中的节点，最终封装成一个MapperedStatemanet
     */
    private void configurationElement(Element element) {
        try {
            String namespace = element.attributeValue("namespace");
            if (namespace == null || namespace.isEmpty()) {
                throw new RuntimeException("Mapper's namespace cannot be empty");
            }
            buildStatementFromContext(elementsByTag(element, "select|insert|update|delete"), namespace);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing Mapper XML. The XML location is '" + resource + "'. Cause: " + e, e);
        }
    }

    private List<Element> elementsByTag(Element element, String expression) {
        List<Element> elementList = new ArrayList<>();
        String[] tags = expression.split("\\|");
        for (String tag : tags) {
            List<Element> elements = element.elements(tag);
            if (elements != null && elements.size() > 0) {
                elementList.addAll(elements);
            }
        }
        return elementList;
    }

    private void buildStatementFromContext(List<Element> elementList, String namespace) {
        for (Element context : elementList) {
            final XMLStatementBuilder statementParser = new XMLStatementBuilder(configuration, context, namespace);
            statementParser.parseStatement();
        }
    }
}
