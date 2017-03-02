/*
 * $RCSfile: SetEncoderTag.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-02-19 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import com.skin.ayada.Encoder;
import com.skin.ayada.ExpressionContext;
import com.skin.ayada.runtime.EncoderFactory;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;

/**
 * <p>Title: SetEncoderTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class SetEncoderTag extends TagSupport {
    private Object encoder;
    private Encoder oldEncoder;

    /**
     * @return int
     * @throws Exception
     */
    @Override
    public int doStartTag() throws Exception {
        Encoder elEncoder = null;
        ExpressionContext expressionContext = this.pageContext.getExpressionContext();

        if(this.encoder != null) {
            if(this.encoder instanceof Encoder) {
                elEncoder = (Encoder)(this.encoder);
            }
            else {
                elEncoder = EncoderFactory.getEncoder(this.encoder.toString());
            }
        }
        this.oldEncoder = expressionContext.getEncoder();
        expressionContext.setEncoder(elEncoder);
        return Tag.EVAL_PAGE;
    }

    /**
     * @return int
     * @throws Exception
     */
    @Override
    public int doEndTag() throws Exception {
        ExpressionContext expressionContext = this.pageContext.getExpressionContext();
        expressionContext.setEncoder(this.oldEncoder);
        return EVAL_PAGE;
    }

    /**
     * @param encoder the encoder to set
     */
    public void setEncoder(Object encoder) {
        this.encoder = encoder;
    }

    /**
     * @return the encoder
     */
    public Object getEncoder() {
        return this.encoder;
    }
}
