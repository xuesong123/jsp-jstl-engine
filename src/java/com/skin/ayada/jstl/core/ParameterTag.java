/*
 * $RCSfile: ParameterTag.java,v $$
 * $Revision: 1.1  $
 * $Date: 2011-12-9  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import com.skin.ayada.tagext.BodyContent;
import com.skin.ayada.tagext.BodyTagSupport;
import com.skin.ayada.tagext.ParameterSupportTag;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;

/**
 * <p>Title: ParameterTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class ParameterTag extends BodyTagSupport
{
    private static final long serialVersionUID = 3337947213732345725L;
    private String name;
    private String value;

    @Override
    public int doEndTag()
    {
        Tag parent = this.getParent();

        if(parent instanceof ParameterSupportTag)
        {
            String value = this.getValue();

            if(value == null)
            {
                BodyContent body = this.getBodyContent();
                value = (body != null ? body.getString() : null);
            }

            ParameterSupportTag tag = (ParameterSupportTag)(parent);
            tag.setParameter(this.getName(), value);
            return TagSupport.EVAL_PAGE;
        }
        else
        {
            throw new RuntimeException("Illegal use of parameter-style tag without servlet as its direct parent");
        }
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the value
     */
    public String getValue()
    {
        return this.value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value)
    {
        this.value = value;
    }
}