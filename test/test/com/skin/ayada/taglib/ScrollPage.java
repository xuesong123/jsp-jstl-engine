/*
 * $RCSfile: ScrollPage.java,v $$
 * $Revision: 1.1  $
 * $Date: 2011-11-3  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.taglib;

import java.io.PrintWriter;

import com.skin.ayada.tagext.TagSupport;

/**
 * <p>Title: ScrollPageTag</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class ScrollPage extends TagSupport
{
    private int pageNum;
    private int pageSize;
    private int count;
    private String className;
    private String href;
    private String pattern;

    private static final int DEFAULT_SIZE = 7;

    @Override
    public int doEndTag()
    {
        try
        {
            String html = this.render();
            this.pageContext.getOut().print(html);
        }
        catch(java.io.IOException e)
        {
        }

        return EVAL_PAGE;
    }

    /**
     * @param pages
     * @return String
     */
    protected String render()
    {
        if(this.pageNum < 1)
        {
            this.pageNum = 1;
        }

        if(this.pageSize < 1)
        {
            this.pageSize = 1;
        }

        if(this.className == null)
        {
            this.className = "";
        }

        if(this.href == null || this.href.trim().length() < 1)
        {
            this.href = "javascript:void(0)";
        }

        int count = this.getCount();
        int total = this.getTotal();
        int[] pages = ScrollPage.getPages(this.pageNum, total, DEFAULT_SIZE);

        StringBuilder buffer = new StringBuilder();
        buffer.append("<div");

        if(this.className != null && this.className.trim().length() > 0)
        {
            buffer.append(" class=\"").append(this.className).append("\"");
        }

        buffer.append(String.format(" page=\"%d\" count=\"%d\" total=\"%d\">", this.pageNum, count, total));

        if(this.pageNum > 1)
        {
            String prev = this.replace(this.href, "%s", this.pageNum - 1);
            buffer.append(String.format("<a class=\"scrollpage\" href=\"%s\" page=\"%d\" title=\"上一页\">上一页</a>", prev, this.pageNum - 1));
        }
        else
        {
            String prev = "javascript:void(0)";
            buffer.append(String.format("<a class=\"disabled\" href=\"%s\" page=\"%d\" title=\"上一页\">上一页</a>", prev, this.pageNum - 1));
        }

        for(int i = 0; i < pages.length; i++)
        {
            int n = pages[i];

            if(n != 0)
            {
                if(n == this.pageNum)
                {
                    buffer.append(String.format("<a class=\"active\" href=\"%s\" page=\"%d\">%d</a>", this.replace(this.href, "%s", n), n, n));
                }
                else
                {
                    buffer.append(String.format("<a class=\"scrollpage\" href=\"%s\" page=\"%d\">%d</a>", this.replace(this.href, "%s", n), n, n));
                }
            }
            else
            {
                buffer.append("<span class=\"ellipsis\">...</span>");
            }
        }

        buffer.append("<span class=\"pagenum\">");
        buffer.append(String.format("<input type=\"text\" class=\"pagenum\" value=\"%d\"/>/%d页", this.pageNum, total));
        buffer.append("</span>");

        if(this.pageNum < total)
        {
            String next = this.replace(this.href, "%s", this.pageNum + 1);
            buffer.append(String.format("<a href=\"%s\" class=\"scrollpage\" page=\"%d\" title=\"下一页\">下一页</a>", next, this.pageNum + 1));
        }
        else
        {
            String next = "javascript: void(0)";
            buffer.append(String.format("<a href=\"%s\" class=\"disabled\" page=\"%d\" title=\"下一页\">下一页</a>", next, this.pageNum + 1));
        }

        if(this.pattern != null)
        {
            String info = this.pattern;
            info = this.replace(info, "!{pageNum}", this.pageNum);
            info = this.replace(info, "!{pageSize}", this.pageSize);
            info = this.replace(info, "!{count}", this.count);
            info = this.replace(info, "!{total}", total);
            buffer.append(info);
        }

        buffer.append("</div>");
        return buffer.toString();
    }

    /**
     * @param pageNo
     * @param totalPage
     * @param size
     * @return int[]
     */
    public static int[] getPages(int num, int pages, int size)
    {
        int[] result = null;

        if(pages <= (size + 4))
        {
            int length = (pages > 0 ? pages : 1);
            result = new int[length];

            for(int i = 0; i < length; i++)
            {
                result[i] = i + 1;
            }

            return result;
        }

        int start = num - size / 2;
        int end = start + size;

        if(start < 3)
        {
            start = 1;
            end = start + size + 1;
            result = new int[size + 3];
        }
        else if((start + size) >= pages)
        {
            start = pages - size;
            end = start + size;
            result = new int[size + 3];
        }
        else
        {
            end = start + size;
            result = new int[size + 4];
        }

        result[0] = 1;
        result[result.length - 1] = pages;

        if(start >= 3)
        {
            if(start == 3)
            {
                result[1] = 2;
            }

            for(int i = start; i < end; i++)
            {
                result[i - start + 2] = i;
            }
        }
        else
        {
            start = 1;
            end = size + 2;

            for(int i = start; i < end; i++)
            {
                result[i - start] = i;
            }
        }

        return result;
    }

    /**
     * @param source
     * @param search
     * @param page
     * @return String
     */
    protected String replace(String source, String search, int page)
    {
        if(source == null)
        {
            return "";
        }

        if(search == null)
        {
            return source;
        }

        int s = 0;
        int e = 0;
        int d = search.length();
        StringBuilder buffer = new StringBuilder();

        do
        {
            e = source.indexOf(search, s);

            if(e == -1)
            {
                buffer.append(source.substring(s));
                break;
            }
            buffer.append(source.substring(s, e)).append(page);
            s = e + d;
        }
        while(true);

        return buffer.toString();
    }

    /**
     * @param num
     * @param pages
     */
    public void print(PrintWriter out)
    {
        int total = this.getTotal();
        int[] pages = ScrollPage.getPages(this.pageNum, total, DEFAULT_SIZE);

        for(int i = 0; i < pages.length; i++)
        {
            int n = pages[i];

            if(n != 0)
            {
                if(n == this.pageNum)
                {
                    out.print("<" + n + ">");
                }
                else
                {
                    out.print("[" + n + "]");
                }
            }
            else
            {
                out.print("...");
            }
        }

        out.println();
        out.flush();
    }

    /**
     * @return the pageNum
     */
    public int getPageNum()
    {
        return this.pageNum;
    }

    /**
     * @param pageNum the pageNum to set
     */
    public void setPageNum(int pageNum)
    {
        this.pageNum = pageNum;
    }

    /**
     * @return the pageSize
     */
    public int getPageSize()
    {
        return this.pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count)
    {
        this.count = count;
    }

    /**
     * @return int
     */
    public int getCount()
    {
        return this.count;
    }

    /**
     * @return int
     */
    public int getTotal()
    {
        return (this.count + (this.pageSize - 1)) / this.pageSize;
    }

    /**
     * @return the className
     */
    public String getClassName()
    {
        return this.className;
    }

    /**
     * @param className the className to set
     */
    public void setClassName(String className)
    {
        this.className = className;
    }

    /**
     * @return the href
     */
    public String getHref()
    {
        return this.href;
    }

    /**
     * @param href the href to set
     */
    public void setHref(String href)
    {
        this.href = href;
    }

    /**
     * @return the pattern
     */
    public String getPattern()
    {
        return this.pattern;
    }

    /**
     * @param pattern the pattern to set
     */
    public void setPattern(String pattern)
    {
        this.pattern = pattern;
    }
}
