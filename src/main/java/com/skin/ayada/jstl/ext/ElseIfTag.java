/*
 * $RCSfile: ElseIfTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2011-12-09 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.ext;

import com.skin.ayada.jstl.ext.IfTag;

/**
 * <p>Title: ElseIfTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class ElseIfTag extends IfTag {
    /**
     * @return int
     * @throws Exception
     */
    @Override
    public int doStartTag() throws Exception {
        IfTag ifTag = IfTag.getIfTag(this.pageContext);

        if(ifTag == null) {
            throw new RuntimeException("IfTag not match !");
        }

        if(ifTag.complete()) {
            return SKIP_BODY;
        }
        else {
            if(this.condition() == true) {
                ifTag.finish();
                return EVAL_BODY_INCLUDE;
            }
            else {
                return SKIP_BODY;
            }
        }
    }
}
