/*
 * $RCSfile: ForEachTagTest.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-8-26 $
 *
 * Copyright (C) 2008 WanMei, Inc. All rights reserved.
 *
 * This software is the proprietary information of WanMei, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.jstl.core;

import java.io.StringWriter;

import com.skin.ayada.jstl.core.ForEachTag;
import com.skin.ayada.runtime.JspFactory;
import com.skin.ayada.runtime.PageContext;

/**
 * <p>Title: ForEachTagTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class ForEachTagTest
{
    public static void main(String[] args)
    {
        StringWriter out = new StringWriter();
        PageContext pageContext = JspFactory.getPageContext(out);

        ForEachTag forEachTag = new ForEachTag();
        forEachTag.setPageContext(pageContext);
        forEachTag.setItems("1, 2, 3");
        int flag = forEachTag.doStartTag();
        System.out.println("index: " + forEachTag.getIndex());
    }
}
