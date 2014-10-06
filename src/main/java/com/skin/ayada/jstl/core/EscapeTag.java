/*
 * $RCSfile: EscapeTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2014年7月28日 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import com.skin.ayada.runtime.ELEncoder;
import com.skin.ayada.runtime.ELEncoderFactory;
import com.skin.ayada.runtime.ExpressionContext;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;

/**
 * <p>Title: EscapeTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class EscapeTag extends TagSupport
{
    private Object encoder;

    @Override
    public int doStartTag() throws Exception
    {
        ELEncoder elEncoder = null;
        ExpressionContext expressionContext = this.pageContext.getExpressionContext();

        if(this.encoder != null)
        {
            if(this.encoder instanceof ELEncoder)
            {
                elEncoder = (ELEncoder)(this.encoder);
            }
            else
            {
                elEncoder = ELEncoderFactory.getELEncoder(this.encoder.toString());
            }
        }

        expressionContext.setEncoder(elEncoder);
        return Tag.EVAL_PAGE;
    }

    /**
     * @return the encoder
     */
    public Object getEncoder()
    {
        return this.encoder;
    }

    /**
     * @param encoder the encoder to set
     */
    public void setEncoder(Object encoder)
    {
        this.encoder = encoder;
    }
}
