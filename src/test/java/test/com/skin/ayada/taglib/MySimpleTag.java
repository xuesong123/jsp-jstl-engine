/*
 * $RCSfile: MySimpleTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.taglib;

import com.skin.ayada.tagext.JspFragment;
import com.skin.ayada.tagext.SimpleTag;

/**
 * <p>Title: MySimpleTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class MySimpleTag extends SimpleTag {
    /**
     * @see com.skin.ayada.tagext.Tag#release()
     */
    @Override
    public void release() {
    }

    /**
     * @see com.skin.ayada.tagext.SimpleTag#doTag()
     */
    @Override
    public void doTag() throws Exception {
        JspFragment jspFrgment = this.getJspBody();

        for(int i = 0; i < 3; i++) {
            jspFrgment.invoke(this.pageContext.getOut());
        }
    }
}
