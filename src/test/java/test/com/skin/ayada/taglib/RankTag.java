/*
 * $RCSfile: RankTag.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-12-10 $
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
 * <p>Title: RankTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class RankTag extends TagSupport {
    private int rankId;
    private long cateId;
    private String description;

    /**
     * @return int
     */
    @Override
    public int doStartTag() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rankId", this.rankId);
        map.put("cateId", this.cateId);
        map.put("description", this.description);
        this.setAttribute(String.valueOf(this.rankId), map);
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
     * @return the rankId
     */
    public int getRankId() {
        return this.rankId;
    }

    /**
     * @param rankId the rankId to set
     */
    public void setRankId(int rankId) {
        this.rankId = rankId;
    }

    /**
     * @return the cateId
     */
    public long getCateId() {
        return this.cateId;
    }

    /**
     * @param cateId the cateId to set
     */
    public void setCateId(long cateId) {
        this.cateId = cateId;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
