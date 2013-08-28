/*
 * $RCSfile: TemplateFactory.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-2-19  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.template;

import com.skin.ayada.compile.TemplateCompiler;
import com.skin.ayada.jstl.TagLibrary;
import com.skin.ayada.jstl.TagLibraryFactory;

/**
 * <p>Title: TemplateFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class TemplateFactory
{
    /**
     * @param source
     * @return Template
     */
    public static Template create(String home, String file, String source)
    {
        TagLibrary tagLibrary = TagLibraryFactory.getStandardTagLibrary();
        TemplateCompiler compiler = new TemplateCompiler(source);
        compiler.setHome(home);
        compiler.setFile(file);
        compiler.setTagLibrary(tagLibrary);
        return compiler.compile();
    }
}
