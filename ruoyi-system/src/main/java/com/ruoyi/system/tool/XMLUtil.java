package com.ruoyi.system.tool;


import org.dom4j.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 封装了XML转换成object，object转换成XML的代码
 */
public class XMLUtil {
    /**
     * 将对象直接转换成String类型的 XML输出
     *
     * @param obj
     * @return
     */
    public static String convertToXml(Object obj) {
        // 创建输出流
        StringWriter sw = new StringWriter();
        try {
            // 利用jdk中自带的转换类实现
            JAXBContext context = JAXBContext.newInstance(obj.getClass());

            Marshaller marshaller = context.createMarshaller();
            // 格式化xml输出的格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    Boolean.TRUE);
            // 将对象转换成输出流形式的xml
            marshaller.marshal(obj, sw);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return sw.toString().replace("standalone=\"yes\"", "");
    }

    /**
     * 将对象根据路径转换成xml文件
     *
     * @param obj
     * @param path
     * @return
     */
    public static void convertToXml(Object obj, String path) {
        try {
            // 利用jdk中自带的转换类实现
            JAXBContext context = JAXBContext.newInstance(obj.getClass());

            Marshaller marshaller = context.createMarshaller();
            // 格式化xml输出的格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    Boolean.TRUE);
            // 将对象转换成输出流形式的xml
            // 创建输出流
            FileWriter fw = null;
            try {
                fw = new FileWriter(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            marshaller.marshal(obj, fw);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    /**
     * 将String类型的xml转换成对象
     */
    public static Object convertXmlStrToObject(Class clazz, String xmlStr) {
        Object xmlObject = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            // 进行将Xml转成对象的核心接口
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader sr = new StringReader(xmlStr);
            xmlObject = unmarshaller.unmarshal(sr);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xmlObject;
    }

    @SuppressWarnings("unchecked")
    /**
     * 将file类型的xml转换成对象
     */
    public static Object convertXmlFileToObject(Class clazz, String xmlPath) {
        Object xmlObject = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            FileReader fr = null;
            try {
                fr = new FileReader(xmlPath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            xmlObject = unmarshaller.unmarshal(fr);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xmlObject;
    }

    /**
     * 根据xpath搜索节点是否存在
     * @param doc	被搜索的XML文档
     * @param xpath	搜索xpath
     * @return		如果根据xpath搜索到1个或多个节点，则返回true；否则返回false
     */
    public static boolean findNode(Document doc, String xpath)
    {
        List<?> list = doc.selectNodes(xpath);

        return (list.size() > 0);
    }

    /**
     * 创建XmlDocument文档
     * @param text	XML字符串
     * @return		构造出的XmlDocument文档
     * @throws RuntimeException	如果XML字符串不合法,抛出RuntimeException异常
     */
    public static Document parserXmlDocument(String text)
    {
        try
        {
            Document doc = DocumentHelper.parseText(text);

            return doc;
        }
        catch(DocumentException ex)
        {
            throw new RuntimeException("不正确的XML字符串格式");
        }
    }

    /**
     * 在XmlNode中，按照xpath获取Node的值。如果Node未找到，抛出异常
     * @param baseNode	XmlNode
     * @param xpath		节点路径
     * @return			值
     */
    public static String getNodeValue(Node baseNode, String xpath)
    {
        Node node = baseNode.selectSingleNode(xpath);

        if (node == null)
        {
            throw new RuntimeException("XML节点[" + xpath + "]未找到");
        }

        return node.getText().trim();
    }

    /**
     * 在XmlNode中，按照xpath获取Node的值。如果Node未找到，抛出异常
     * @param baseNode	XmlNode
     * @param xpath		节点路径
     * @param row
     * @return			值
     * @return
     */
    public static String getNodeValue(Node baseNode, String xpath, int row)
    {
        Node node = baseNode.selectSingleNode(xpath);

        if (node == null)
        {
            throw new RuntimeException("第" + String.valueOf(row) + "行中的XML节点[" + xpath + "]未找到");
        }

        return node.getText().trim();
    }

    /**
     * 在XmlDocument中，按照xpath获取Node的值。如果Node未找到，抛出异常。异常信息由自定义
     * @param baseNode	XmlDocument文档
     * @param xpath	节点路径
     * @param msg	提示文本
     * @return RuntimeException	如果未找到节点，抛出异常
     */
    public static String getNodeValue(Node baseNode, String xpath, String msg)
    {
        Node node = baseNode.selectSingleNode(xpath);

        if (node == null)
        {
            String exmsg = msg.replace("??", xpath);
            throw new RuntimeException(exmsg);
        }

        return node.getText().trim();
    }

    /**
     * 在XmlDocument中，获取xpath上节点的属性值
     * @param baseNode
     * @param xpath
     * @param attrName
     * @return
     */
    public static String getAttributeValue(Node baseNode, String xpath, String attrName)
    {
        Node node = baseNode.selectSingleNode(xpath);

        if (node == null)
        {
            throw new RuntimeException("XML节点[" + xpath + "]未找到");
        }

        Element elemnet = (Element)node;

        return elemnet.valueOf("@" + attrName);
    }

    public static void checkNodeExists(Node baseNode, String xpath, String fieldDesc)
    {
        Node node = baseNode.selectSingleNode(xpath);

        if (node == null)
        {
            throw new RuntimeException("[" + fieldDesc + "]字段[" + xpath + "]未找到");
        }
    }

    public static void checkNodeValueNull(Node baseNode, String xpath, String fieldDesc)
    {
        String value = XMLUtil.getNodeValue(baseNode, xpath);

        if (value.trim().length() <= 0)
        {
            throw new RuntimeException("[" + fieldDesc + "]字段[" + xpath + "]不可为空");
        }
    }

    public static void checkNodeValueLength(Node baseNode, String xpath, String fieldDesc, int length)
    {
        String value = XMLUtil.getNodeValue(baseNode, xpath);

        if (value.trim().length() > length)
        {
            throw new RuntimeException("[" + fieldDesc + "]字段[" + xpath + "]的值长度超过规范定义的" + String.valueOf(length) + "位");
        }
    }

    public static void checkNodeValueInteger(Node baseNode, String xpath, String fieldDesc)
    {
        String value = XMLUtil.getNodeValue(baseNode, xpath);

        try
        {
            Integer.parseInt(value);
        }
        catch(NumberFormatException ex)
        {
            throw new RuntimeException("[" + fieldDesc + "]字段[" + xpath + "]的值[" + value + "]不是合法的整数值");
        }
    }

    public static void checkNodeValueDouble(Node baseNode, String xpath, String fieldDesc)
    {
        String value = XMLUtil.getNodeValue(baseNode, xpath);

        try
        {
            Double.parseDouble(value);
        }
        catch(NumberFormatException ex)
        {
            throw new RuntimeException("[" + fieldDesc + "]字段[" + xpath + "]的值[" + value + "]不是合法的数值");
        }
    }

    public static void checkNodeValueDate(Node baseNode, String xpath, String fieldDesc)
    {
        String value = XMLUtil.getNodeValue(baseNode, xpath);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            format.setLenient(false);
            format.parse(value);
        }
        catch(ParseException ex)
        {
            throw new RuntimeException("[" + fieldDesc + "]字段[" + xpath + "]的值[" + value + "]不是符合日期格式“yyyy-MM-dd”要求");
        }
    }
}