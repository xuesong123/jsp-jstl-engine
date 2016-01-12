/*
 * $RCSfile: CacheTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-6-8 $
 *
 * Copyright (C) 2008 WanMei, Inc. All rights reserved.
 *
 * This software is the proprietary information of WanMei, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.core;

import java.io.IOException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.tagext.BodyContent;
import com.skin.ayada.tagext.BodyTag;
import com.skin.ayada.tagext.BodyTagSupport;
import com.skin.ayada.tagext.Tag;

/**
 * <p>Title: CacheTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class CacheTag extends BodyTagSupport {
    private Object cache;
    private String key;
    private int expires;
    private static final Logger logger = LoggerFactory.getLogger(CacheTag.class);

    @Override
    public int doStartTag() {
        if(this.key == null || this.getExpires() < 1) {
            return BodyTag.EVAL_BODY_BUFFERED;
        }

        if(this.cache == null) {
            this.cache = this.pageContext.getAttribute("cacheClient");
        }

        String content = CacheTag.getContent(this.cache, this.key);

        if(content != null) {
            try {
                if(logger.isDebugEnabled()) {
                    logger.debug("cache.hit: key: " + this.getKey() + ", expires: " + this.getExpires());
                }
                this.pageContext.getOut().write(content);
            }
            catch(IOException e) {
                logger.error(e.getMessage(), e);
            }
            return Tag.SKIP_BODY;
        }
        else {
            return BodyTag.EVAL_BODY_BUFFERED;
        }
    }

    @Override
    public int doEndTag() {
        BodyContent bodyContent = this.getBodyContent();

        if(bodyContent != null) {
            String content = bodyContent.getString();

            if(this.getExpires() > 0) {
                if(logger.isDebugEnabled()) {
                    logger.debug("cache.set: key: " + this.getKey() + ", expires: " + this.getExpires());
                }
                CacheTag.setContent(this.cache, this.getKey(), this.getExpires(), content);
            }
    
            try {
                this.pageContext.getOut().write(content);
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
        return Tag.EVAL_PAGE;
    }

    /**
     * @param cache
     * @param key
     * @return String
     */
    public static String getContent(Object cache, String key) {
        if(cache == null) {
            throw new IllegalArgumentException("parameter 'cache' must be not null !");
        }

        try {
            Class<?> type = cache.getClass();
            Method method = type.getMethod("getCache", new Class<?>[]{String.class});
            return (String)(method.invoke(cache, new Object[]{key}));
        }
        catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * @param cache
     * @param key
     * @param content
     */
    public static void setContent(Object cache, String key, int expires, String content) {
        if(cache == null) {
            throw new IllegalArgumentException("parameter 'cache' must be not null !");
        }

        try {
            Class<?> type = cache.getClass();
            Method method = type.getMethod("setCache", new Class<?>[]{String.class, int.class, Object.class});
            method.invoke(cache, new Object[]{key, expires, content});
        }
        catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
    
    /**
     * @return the cache
     */
    public Object getCache() {
        return this.cache;
    }

    /**
     * @param cache the cache to set
     */
    public void setCache(Object cache) {
        this.cache = cache;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return this.key;
    }

    /**
     * @param expires the expires to set
     */
    public void setExpires(int expires) {
        this.expires = expires;
    }

    /**
     * @return the expires
     */
    public int getExpires() {
        return this.expires;
    }
}
