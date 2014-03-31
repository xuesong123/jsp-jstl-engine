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
import com.skin.ayada.util.StringUtil;

/**
 * <p>Title: SourceFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public abstract class SourceFactory
{
    private String sourcePattern = "jsp,jspf,jspx,tpl";

    /**
     * @param home
     * @param path
     * @param encoding
     * @return Source
     */
    public abstract Source getSource(String path, String encoding);

    /**
     * @param path
     * @return long
     */
    public abstract long getLastModified(String path);

    /**
     * @param path
     * @return int
     */
    public int getSourceType(String path)
    {
        if(this.sourcePattern == null)
        {
            this.sourcePattern = TemplateConfig.getInstance().getString("ayada.compile.source-pattern");
        }

        String fileType = this.getExtension(path).toLowerCase();

        if(StringUtil.contains(this.sourcePattern, fileType))
        {
            return Source.SCRIPT;
        }

        return Source.STATIC;
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
                return path.substring(i + 1);
            }

            return "";
        }

        return "";
    }

    /**
     * @return the sourcePattern
     */
    public String getSourcePattern()
    {
        return this.sourcePattern;
    }

    /**
     * @param sourcePattern the sourcePattern to set
     */
    public void setSourcePattern(String sourcePattern)
    {
        this.sourcePattern = sourcePattern;
    }
}
