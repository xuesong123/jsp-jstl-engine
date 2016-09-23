/*
 * $RCSfile: FastJstl.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-08 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.compile;

import java.util.HashMap;
import java.util.Map;

import com.skin.ayada.jstl.core.AttributeTag;
import com.skin.ayada.jstl.core.ChooseTag;
import com.skin.ayada.jstl.core.CommentTag;
import com.skin.ayada.jstl.core.ConstructorTag;
import com.skin.ayada.jstl.core.ElementTag;
import com.skin.ayada.jstl.core.ExecuteTag;
import com.skin.ayada.jstl.core.ExitTag;
import com.skin.ayada.jstl.core.ForEachTag;
import com.skin.ayada.jstl.core.IfTag;
import com.skin.ayada.jstl.core.OtherwiseTag;
import com.skin.ayada.jstl.core.OutTag;
import com.skin.ayada.jstl.core.ParameterTag;
import com.skin.ayada.jstl.core.PrintTag;
import com.skin.ayada.jstl.core.PropertyTag;
import com.skin.ayada.jstl.core.SetTag;
import com.skin.ayada.jstl.core.WhenTag;
import com.skin.ayada.jstl.fmt.DateFormatTag;

/**
 * <p>Title: FastJstl</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class FastJstl {
    private static final Map<String, String> map = new HashMap<String, String>();

    static {
        map.put(IfTag.class.getName(), "1");
        map.put(SetTag.class.getName(), "1");
        map.put(OutTag.class.getName(), "1");
        map.put(ForEachTag.class.getName(), "1");
        map.put(ChooseTag.class.getName(), "1");
        map.put(WhenTag.class.getName(), "1");
        map.put(OtherwiseTag.class.getName(), "1");
        map.put(CommentTag.class.getName(), "1");
        map.put(PrintTag.class.getName(), "1");
        map.put(PropertyTag.class.getName(), "1");
        map.put(ParameterTag.class.getName(), "1");
        map.put(AttributeTag.class.getName(), "1");
        map.put(ElementTag.class.getName(), "1");
        map.put(ConstructorTag.class.getName(), "1");
        map.put(ExecuteTag.class.getName(), "1");
        map.put(ExitTag.class.getName(), "1");
        map.put(DateFormatTag.class.getName(), "1");
    }

    /**
     * @param className
     * @return boolean
     */
    public static boolean has(String className) {
        return (map.get(className) != null);
    }
}
