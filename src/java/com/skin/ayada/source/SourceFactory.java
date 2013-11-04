/*
 * $RCSfile: SourceFactory.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-4 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.source;

import com.skin.ayada.config.TemplateConfig;

/**
 * <p>Title: SourceFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public abstract class SourceFactory
{
    /**
     * @param home
     * @param path
     * @param encoding
     * @return Source
     */
    public abstract Source getSource(String path, String encoding);

    /**
     * @param path
     * @return int
     */
    public int getSourceType(String path)
    {
        int type = 1;
        String fileType = this.getExtension(path).toLowerCase();
        TemplateConfig config = TemplateConfig.getInstance();

        if(config.contains("ayada.compile.source-pattern", fileType))
        {
            type = 0;
        }

        return type;
    }

    /**
     * @param path
     * @return String
     */
    public String getExtension(String path)
    {
        if(path != null && path.length() > 0)
        {
            char c = '0';
            int i = path.length() - 1;

            for(; i > -1; i--)
            {
                c = path.charAt(i);

                if(c == '.' )
                {
                    break;
                }
                else if(c == '/' || c == '\\' || c == ':')
                {
                    break;
                }
            }

            if(c == '.')
            {
                path.substring(i + 1);
            }

            return "";
        }

        return "";
    }
}
