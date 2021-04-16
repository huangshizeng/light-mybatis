package com.huang.mybatis.builder.xml;

import cn.hutool.core.io.resource.ClassPathResource;
import com.huang.mybatis.builder.BaseBuilder;
import com.huang.mybatis.datasource.DataSource;
import com.huang.mybatis.session.Configuration;
import com.huang.mybatis.util.XmlUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author: hsz
 * @date: 2021/4/14 09:40
 * @description:
 */

public class XmlConfigBuilder extends BaseBuilder {

    private Document document;
    private boolean parsed;

    public XmlConfigBuilder(InputStream inputStream) throws Exception {
        super(new Configuration());
        this.document = XmlUtil.readXml(inputStream);
        this.parsed = false;
    }

    public Configuration parse() {
        if (parsed) {
            throw new RuntimeException("Each PropertiesConfigBuilder can only be used once.");
        }
        parsed = true;
        parseConfiguration(document.getRootElement());
        return configuration;
    }

    private void parseConfiguration(Element element) {
        try {
            parseDataBase(element.element("dataSource"));
            parseMapper(element.element("mappers"));
        } catch (Exception e) {
            throw new RuntimeException("Error parsing SQL Mapper Configuration. Cause: " + e, e);
        }
    }

    private void parseDataBase(Element element) {
        Properties properties = getChildrenAsProperties(element);
        DataSource dataSource = getDataSource(properties);
        configuration.setDataSource(dataSource);
    }

    private DataSource getDataSource(Properties properties) {
        String driver = properties.getProperty("driver");
        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        return new DataSource(driver, url, username, password);
    }

    private Properties getChildrenAsProperties(Element element) {
        Properties properties = new Properties();
        List<Element> elementList = element.elements("property");
        elementList.forEach(e -> properties.put(e.attributeValue("name"), e.attributeValue("value")));
        return properties;
    }

    private void parseMapper(Element element) throws DocumentException {
        List<Element> elementList = element.elements("mapper");
        for (Element e : elementList) {
            String resource = e.attributeValue("resource");
            InputStream inputStream = new ClassPathResource(resource).getStream();
            XmlMapperBuilder mapperParser = new XmlMapperBuilder(inputStream, configuration, resource);
            mapperParser.parse();
        }
    }
}