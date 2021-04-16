package com.huang.mybatis.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

/**
 * @author: hsz
 * @date: 2021/4/14 13:56
 * @description:
 */

public class XmlUtil {

    public static Document readXml(InputStream inputStream) throws DocumentException {
        // 创建一个读取器
        SAXReader saxReader = new SAXReader();

        // 读取文件内容
        return saxReader.read(inputStream);
    }
}
