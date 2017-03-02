/*
 * $RCSfile: TagLibraryFactory.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-02-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * <p>Title: TagLibraryFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class TagLibraryFactory {
    private static final Map<String, TagInfo> standard = load();
    private static final Logger logger = LoggerFactory.getLogger(TagLibraryFactory.class);

    /**
     * @return TagLibrary
     */
    public static TagLibrary getTagLibrary() {
        return new TagLibrary();
    }

    /**
     * @return TagLibrary
     */
    public static TagLibrary getStandardTagLibrary() {
        /**
         * buf fix:
         * t:rename指令允许在页面范围内修改标签的定义.
         * 当标签定义被修改之后, 后面再加载的所有标签都被修改了.
         * 
         * 标准标签库是不可修改的
         * 每次调用标准标签库都返回一个复制的标签库
         */
        Map<String, TagInfo> map = clone(standard);
        TagLibrary tagLibrary = new TagLibrary();
        tagLibrary.setup(map);
        return tagLibrary;
    }

    /**
     * @return Map<String, TagInfo>
     */
    private static Map<String, TagInfo> load() {
        try {
            ClassLoader classLoader = TagLibraryFactory.class.getClassLoader();
            Map<String, TagInfo> map1 = parse(classLoader.getResourceAsStream("ayada-taglib-default.xml"));
            Map<String, TagInfo> map2 = parse(classLoader.getResourceAsStream("ayada-taglib.xml"));
            map1.putAll(map2);
            return map1;
        }
        catch(Exception e) {
            logger.warn(e.getMessage(), e);
        }
        return new HashMap<String, TagInfo>();
    }

    /**
     * @param prefix
     * @param resource
     * @return Map<String, TagInfo>
     * @throws Exception
     */
    public static Map<String, TagInfo> load(String prefix, String resource) throws Exception {
        ClassLoader classLoader = TagLibraryFactory.class.getClassLoader();
        Map<String, TagInfo> map = parse(classLoader.getResourceAsStream(resource));
        Map<String, TagInfo> result = new LinkedHashMap<String, TagInfo>(map.size());

        for(Map.Entry<String, TagInfo> entry : map.entrySet()) {
            TagInfo tagInfo = entry.getValue();
            String name = tagInfo.getName();
            int k = name.indexOf(":");

            if(k > -1) {
                name = name.substring(k + 1);
            }

            name = prefix + ":" + name;
            tagInfo.setName(name);
            result.put(name, tagInfo);
        }
        return result;
    }

    /**
     * @param inputStream
     * @return Map<String, TagInfo>
     * @throws Exception
     */
    public static Map<String, TagInfo> parse(InputStream inputStream) throws Exception {
        Map<String, TagInfo> map = new LinkedHashMap<String, TagInfo>();

        if(inputStream == null) {
            return map;
        }

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(new InputSource(inputStream));
        Element element = document.getDocumentElement();
        NodeList childNodes = element.getChildNodes();

        for(int i = 0, length = childNodes.getLength(); i < length; i++) {
            Node node = childNodes.item(i);
            int nodeType = node.getNodeType();

            if(nodeType == Node.ELEMENT_NODE) {
                String nodeName = node.getNodeName();

                if(nodeName.equals("tag")) {
                    TagInfo tagInfo = getTagInfo(node);

                    if(tagInfo != null) {
                        map.put(tagInfo.getName(), tagInfo);
                    }
                }
            }
        }
        return map;
    }

    /**
     * @param node
     * @return TagInfo
     */
    private static TagInfo getTagInfo(Node node) {
        TagInfo tagInfo = new TagInfo();
        NodeList childNodes = node.getChildNodes();

        if(childNodes.getLength() > 0) {
            for(int i = 0, length = childNodes.getLength(); i < length; i++) {
                Node n = childNodes.item(i);

                if(n.getNodeType() == Node.ELEMENT_NODE) {
                    String nodeName = n.getNodeName();

                    if(nodeName.equals("name")) {
                        tagInfo.setName(n.getTextContent().trim());
                    }
                    else if(nodeName.equals("tag-class")) {
                        tagInfo.setTagClass(n.getTextContent().trim());
                    }
                    else if(nodeName.equals("body-content")) {
                        tagInfo.setBodyContent(TagInfo.getBodyContent(n.getTextContent()));
                    }
                    else if(nodeName.equals("ignore-whitespace")) {
                        tagInfo.setIgnoreWhitespace(!"false".equals(n.getTextContent()));
                    }
                    else if(nodeName.equals("description")) {
                        tagInfo.setDescription(n.getTextContent().trim());
                    }
                }
            }
        }
        else {
            NamedNodeMap map = node.getAttributes();

            for(int i = 0, len = map.getLength(); i < len; i++) {
                Node n = map.item(i);
                String nodeName = n.getNodeName();

                if(nodeName.equals("name")) {
                    tagInfo.setName(n.getNodeValue().trim());
                }
                else if(nodeName.equals("tag-class")) {
                    tagInfo.setTagClass(n.getNodeValue().trim());
                }
                else if(nodeName.equals("body-content")) {
                    tagInfo.setBodyContent(TagInfo.getBodyContent(n.getNodeValue().trim()));
                }
                else if(nodeName.equals("ignore-whitespace")) {
                    tagInfo.setIgnoreWhitespace(!"false".equals(n.getTextContent()));
                }
                else if(nodeName.equals("description")) {
                    tagInfo.setDescription(n.getNodeValue().trim());
                }
            }
        }

        String name = tagInfo.getName();
        String tagClass = tagInfo.getTagClass();

        if(name == null || name.length() < 1) {
            return null;
        }

        if(tagClass == null || tagClass.length() < 1) {
            return null;
        }
        return tagInfo;
    }

    /**
     * @param library
     * @return Map<String, TagInfo>
     */
    public static Map<String, TagInfo> clone(Map<String, TagInfo> library) {
        Map<String, TagInfo> map = new HashMap<String, TagInfo>();

        for(Map.Entry<String, TagInfo> entry : library.entrySet()) {
            String name = entry.getKey();
            TagInfo tagInfo = entry.getValue();
            map.put(name, tagInfo.clone());
        }
        return map;
    }
}