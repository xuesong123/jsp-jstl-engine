/*
 * $RCSfile: MyTagFactor.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-07 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.template;

import com.skin.ayada.factory.DefaultTagFactory;
import com.skin.ayada.tagext.Tag;

/**
 * <p>Title: MyTagFactor</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class MyTagFactory extends DefaultTagFactory {
    /**
     * @return Tag
     */
    @Override
    public Tag create() {
        return new com.skin.ayada.jstl.core.ForEachTag();
    }
}
