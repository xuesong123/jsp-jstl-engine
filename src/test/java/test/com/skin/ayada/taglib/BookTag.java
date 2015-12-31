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
public class BookTag extends TagSupport {
    private long bookId;
    private String bookName;
    private String bookCover;
    private int bookType;

    /**
     * @return int
     */
    @Override
    public int doStartTag() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("bookId", this.bookId);
        map.put("bookName", this.bookName);
        map.put("bookCover", this.bookCover);
        map.put("bookType", this.bookType);
        this.setAttribute(String.valueOf(this.bookId), map);
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
     * @return the bookId
     */
    public long getBookId() {
        return this.bookId;
    }

    /**
     * @param bookId the bookId to set
     */
    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    /**
     * @return the bookName
     */
    public String getBookName() {
        return this.bookName;
    }

    /**
     * @param bookName the bookName to set
     */
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    /**
     * @return the bookCover
     */
    public String getBookCover() {
        return this.bookCover;
    }

    /**
     * @param bookCover the bookCover to set
     */
    public void setBookCover(String bookCover) {
        this.bookCover = bookCover;
    }

    /**
     * @return the bookType
     */
    public int getBookType() {
        return this.bookType;
    }

    /**
     * @param bookType the bookType to set
     */
    public void setBookType(int bookType) {
        this.bookType = bookType;
    }
}
