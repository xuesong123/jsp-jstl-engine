/*
 * $RCSfile: ConstructorTag.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-11-26 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import com.skin.ayada.tagext.ConstructorTagSupport;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;
import com.skin.ayada.util.ClassUtil;

/**
 * <p>Title: ConstructorTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class ConstructorTag extends TagSupport {
    private String type;
    private Object value;

    /**
     * @return int
     */
    @Override
    public int doStartTag() throws Exception {
        ConstructorTag.setArgument(this.getParent(), this.type, this.value);
        return Tag.SKIP_BODY;
    }

    /**
     * @param tag
     * @param type
     * @param value
     * @throws Exception
     */
    public static void setArgument(Tag tag, String type, Object value) throws Exception {
        if(tag instanceof ConstructorTagSupport) {
            ConstructorTagSupport constructorTagSupport = (ConstructorTagSupport)tag;

            if(type != null) {
                Class<?> parameterType = ClassUtil.getClass(type);
                Object argument = ClassUtil.cast(value, parameterType);
                constructorTagSupport.setArgument(parameterType, argument);
            }
            else {
                constructorTagSupport.setArgument(value.getClass(), value);
            }
        }
        else {
            throw new RuntimeException("Illegal use of parameter-style tag without servlet as its direct parent: parent tag is not a ConstructorSupportTag !");
        }
    }

    /**
     * @return the type
     */
    public String getType() {
        return this.type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return this.value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }
}
