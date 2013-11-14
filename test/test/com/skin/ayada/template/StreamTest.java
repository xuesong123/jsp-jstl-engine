/*
 * $RCSfile: StreamTest.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-14 $
 *
 * Copyright (C) 2008 WanMei, Inc. All rights reserved.
 *
 * This software is the proprietary information of WanMei, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.template;

import java.util.Map;

import com.skin.ayada.compile.TemplateCompiler;
import com.skin.ayada.io.StringStream;
import com.skin.ayada.statement.JspDeclaration;
import com.skin.ayada.statement.NodeType;
import com.skin.ayada.util.NodeUtil;

/**
 * <p>Title: StreamTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class StreamTest
{
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        System.out.println("*****************");
        
        // TODO:
        TemplateCompiler tc = new TemplateCompiler(null);
        // tc.stream = new StringStream("<jsp:scriptlet>abc</jsp:scriptlet   >abc");

        String source = "   />abc</jsp:scriptlet   >abc";
        StringStream stream = new StringStream(source);

        System.out.println("         10        20        30        40        50        60");
        System.out.println("0123456789012345678901234567890123456789012345678901234567890");
        System.out.println(source);

        JspDeclaration node = new JspDeclaration();
        node.setTagClassName((String)null);
        node.setLineNumber(tc.getLineNumber());
        Map<String, String> attributes = tc.getAttributes();
        System.out.println("pos: " + stream.getPosition() + " - " + (char)(stream.peek()));

        node.setAttributes(attributes);
        node.setClosed(NodeType.SELF_CLOSED);

        System.out.println("size: " + attributes.size());
        System.out.println(NodeUtil.toString(attributes));
        System.out.println("pos: " + stream.getPosition() + " - " + (char)(stream.peek()));

        int i = 0;
        while((i = stream.read()) != -1)
        {
            if(i == '>')
            {
                System.out.println("2 c: [" + (char)i + "]");
                break;
            }
            else
            {
                System.out.println("2 c: [" + (char)i + "]");
            }
        }
        System.out.println("pos: " + stream.getPosition() + " - " + (char)(stream.peek()));
        tc.skipCRLF();
        System.out.println("pos: " + stream.getPosition() + " - " + (char)(stream.peek()));
        String content = tc.readNodeContent("jsp:scriptlet");
        System.out.println("content: [" + content + "]");
    }
}
