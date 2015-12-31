/*
 * $RCSfile: RecommendTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-12-12 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.taglib;

import java.util.HashMap;
import java.util.Map;

import com.skin.ayada.tagext.AttributeTagSupport;
import com.skin.ayada.tagext.Tag;
import com.skin.ayada.tagext.TagSupport;

/**
 * <p>Title: RecommendTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class RecommendTag extends TagSupport {
    private String recommendName;
    private int entrySize;
    private String createTime;
    private String updateTime;

    /**
     * @return int
     */
    @Override
    public int doStartTag() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("recommendName", this.recommendName);
        map.put("entrySize", this.entrySize);
        map.put("createTime", this.createTime);
        map.put("updateTime", this.updateTime);
        this.setAttribute(String.valueOf(this.recommendName), map);
        return Tag.SKIP_BODY;
    }

    /**
     * @param name
     * @param value
     */
    protected void setAttribute(String name, Object value) {
        Tag parent = this.getParent();

        if(parent instanceof AttributeTagSupport) {
            AttributeTagSupport tag = (AttributeTagSupport)(parent);
            tag.setAttribute(name, value);
        }
        else {
            throw new RuntimeException("Illegal use of parameter-style tag without servlet as its direct parent");
        }
    }

    /**
     * @return the recommendName
     */
    public String getRecommendName() {
        return this.recommendName;
    }

    /**
     * @param recommendName the recommendName to set
     */
    public void setRecommendName(String recommendName) {
        this.recommendName = recommendName;
    }

    /**
     * @return the entrySize
     */
    public int getEntrySize() {
        return this.entrySize;
    }

    /**
     * @param entrySize the entrySize to set
     */
    public void setEntrySize(int entrySize) {
        this.entrySize = entrySize;
    }

    /**
     * @return the createTime
     */
    public String getCreateTime() {
        return this.createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the updateTime
     */
    public String getUpdateTime() {
        return this.updateTime;
    }

    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
