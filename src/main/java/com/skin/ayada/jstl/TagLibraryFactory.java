/*
 * $RCSfile: TagLibraryFactory.java,v $$
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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
public class TagLibraryFactory
{
    private static final Map<String, TagInfo> map = load();

    /**
     * @return TagLibrary
     */
    public static TagLibrary getStandardTagLibrary()
    {
        TagLibrary tagLibrary = new TagLibrary();
        tagLibrary.setup(map);
        return tagLibrary;
    }

    /**
     * @return Map<String, TagInfo>
     */
    private static Map<String, TagInfo> load()
    {
        try
        {
            ClassLoader classLoader = TagLibraryFactory.class.getClassLoader();
            Map<String, TagInfo> map1 = parse(classLoader.getResourceAsStream("ayada-taglib-default.xml"));
            Map<String, TagInfo> map2 = parse(classLoader.getResourceAsStream("ayada-taglib.xml"));
            map1.putAll(map2);
            return map1;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return new HashMap<String, TagInfo>();
    }

    /**
     * @param prifix
     * @param uri
     * @return Map<String, TagInfo>
     */
    public static Map<String, TagInfo> load(String prefix, String uri) throws Exception
    {
        String resource = uri;

        if(resource.startsWith("http://"))
        {
            int k = resource.indexOf("/", 7);
            
            if(k > -1)
            {
                resource = resource.substring(k + 1);
            }
        }

        ClassLoader classLoader = TagLibraryFactory.class.getClassLoader();
        Map<String, TagInfo> map = parse(classLoader.getResourceAsStream(resource + ".xml"));
        Map<String, TagInfo> result = new HashMap<String, TagInfo>(map.size());

        for(Map.Entry<String, TagInfo> entry : map.entrySet())
        {
            TagInfo tagInfo = entry.getValue();
            String name = tagInfo.getName();
            int k = name.indexOf(":");

            if(k > -1)
            {
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
     * @throws Exception
     */
    public static Map<String, TagInfo> parse(InputStream inputStream) throws Exception
    {
        Map<String, TagInfo> map = new LinkedHashMap<String, TagInfo>();

        if(inputStream == null)
        {
            return map;
        }

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(new InputSource(inputStream));
        Element element = document.getDocumentElement();
        NodeList childNodes = element.getChildNodes();

        for(int i = 0, length = childNodes.getLength(); i < length; i++)
        {
            Node node = childNodes.item(i);
            int nodeType = node.getNodeType();

            if(nodeType == Node.ELEMENT_NODE)
            {
                String nodeName = node.getNodeName();

                if(nodeName.equals("tag"))
                {
                    TagInfo tagInfo = getTagInfo(node);

                    if(tagInfo != null)
                    {
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
    private static TagInfo getTagInfo(Node node)
    {
        TagInfo tagInfo = new TagInfo();
        NodeList childNodes = node.getChildNodes();

        for(int i = 0, length = childNodes.getLength(); i < length; i++)
        {
            Node n = childNodes.item(i);

            if(n.getNodeType() == Node.ELEMENT_NODE)
            {
                String nodeName = n.getNodeName();

                if(nodeName.equals("name"))
                {
                    tagInfo.setName(n.getTextContent().trim());
                }
                else if(nodeName.equals("tag-class"))
                {
                    tagInfo.setTagClass(n.getTextContent().trim());
                }
                else if(nodeName.equals("body-content"))
                {
                    tagInfo.setBodyContent(TagInfo.getBodyContent(n.getTextContent()));
                }
                else if(nodeName.equals("description"))
                {
                    tagInfo.setDescription(n.getTextContent().trim());
                }
            }
        }

        String name = tagInfo.getName();
        String tagClass = tagInfo.getTagClass();

        if(name == null || name.length() < 1)
        {
            return null;
        }

        if(tagClass == null || tagClass.length() < 1)
        {
            return null;
        }

        return tagInfo;
    }
}