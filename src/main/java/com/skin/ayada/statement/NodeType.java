/*
 * $RCSfile: NodeType.java,v $$
 * $Revision: 1.1 $
 * $Date: 2012-7-03 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.statement;

/**
 * <p>Title: NodeType</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class NodeType {
    /**
     * doctype
     */
    public static final String DOCTYPE = "html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"";

    /**
     * node
     */
    public static final int NODE    = 1;

    /**
     * text
     */
    public static final int TEXT    = 2;

    /**
     * text
     */
    public static final int COMMENT = 3;

    /**
     * text
     */
    public static final int CDATA   = 4;

    /**
     * text
     */
    public static final int VARIABLE   = 2011;

    /**
     * text
     */
    public static final int EXPRESSION = 2012;

    /**
     * text
     */
    public static final int TAG_NODE   = 2013;

    /**
     * startsWith "<%@" page
     */
    public static final int JSP_DIRECTIVE_PAGE    = 2014;

    /**
     * startsWith "<%@" taglib
     */
    public static final int JSP_DIRECTIVE_TAGLIB  = 2015;

    /**
     * startsWith "<%@" include
     */
    public static final int JSP_DIRECTIVE_INCLUDE = 2016;

    /**
     * startsWith "<%!"
     */
    public static final int JSP_DECLARATION = 2017;

    /**
     * startsWith "<%="
     */
    public static final int JSP_EXPRESSION  = 2018;

    /**
     * startsWith "<% " [\r|\n]
     */
    public static final int JSP_SCRIPTLET   = 2019;

    /**
     * startsWith "<% " [\r|\n]
     */
    public static final int UNKNOWN         = 9999;

    /**
     * data
     */
    public static final String DATA_NAME    = "#data";

    /**
     * text
     */
    public static final String TEXT_NAME    = "#text";

    /**
     * expr
     */
    public static final String EXPR_NAME    = "#expr";

    /**
     * vari
     */
    public static final String VARI_NAME    = "#vari";

    /**
     * CDATA
     */
    public static final String CDATA_NAME   = "<![CDATA[";

    /**
     * comment
     */
    public static final String COMMENT_NAME = "#comment";

    /**
     * jsp:declaration
     */
    public static final String JSP_DECLARATION_NAME   = "jsp:declaration";

    /**
     * jsp:expression
     */
    public static final String JSP_EXPRESSION_NAME    = "jsp:expression";

    /**
     * jsp:scriptlet
     */
    public static final String JSP_SCRIPTLET_NAME      = "jsp:scriptlet";

    /**
     * jsp:directive.page
     */
    public static final String JSP_DIRECTIVE_PAGE_NAME = "jsp:directive.page";

    /**
     * jsp:directive.taglib
     */
    public static final String JSP_DIRECTIVE_TAGLIB_NAME    = "jsp:directive.taglib";

    /**
     * jsp:directive.include
     */
    public static final String JSP_DIRECTIVE_INCLUDE_NAME = "jsp:directive.include";


    /**
     * pair closed
     */
    public static final int PAIR_CLOSED = 2;

    /**
     * self closed
     */
    public static final int SELF_CLOSED = 3;

    /**
     * @param node
     * @return boolean
     */
    public static boolean isCloseNode(Node node) {
        return isCloseNode(node.getNodeName().toLowerCase());
    }

    /**
     * @param nodeName
     * @return boolean
     */
    public static boolean isCloseNode(String nodeName) {
        return (nodeName.equalsIgnoreCase("br")
                || nodeName.equalsIgnoreCase("hr")
                || nodeName.equalsIgnoreCase("img")
                || nodeName.equalsIgnoreCase("link")
                || nodeName.equalsIgnoreCase("meta")
                || nodeName.equalsIgnoreCase("base")
                || nodeName.equalsIgnoreCase("input"));
    }
}
