/*
 * $RCSfile: TestBodyTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-25 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.taglib;

import java.io.IOException;

import com.skin.ayada.runtime.JspWriter;
import com.skin.ayada.tagext.BodyContent;
import com.skin.ayada.tagext.BodyTag;
import com.skin.ayada.tagext.BodyTagSupport;

/**
 * <p>Title: TestBodyTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class TestBodyTag extends BodyTagSupport {
    @Override
    public int doStartTag() {
        JspWriter out = this.pageContext.getOut();

        try {
            out.print(TestBodyTag.class.getName());
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return BodyTag.EVAL_BODY_BUFFERED;
    }

    /**
     * @return int
     */
    @Override
    public int doEndTag() {
        BodyContent bodyContent = this.getBodyContent();

        if(bodyContent != null) {
            String content = bodyContent.getString().trim();

            try {
                bodyContent.getEnclosingWriter().print(" - " + content);
            }
            catch(IOException e) {
            }
        }
        return EVAL_PAGE;
    }
}
