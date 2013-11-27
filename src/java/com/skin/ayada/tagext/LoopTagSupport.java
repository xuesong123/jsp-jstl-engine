/*
 * $RCSfile: LoopTagSupport.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-2-19  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.tagext;

/**
 * <p>Title: LoopTagSupport</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public abstract class LoopTagSupport extends TagSupport implements LoopTag, IterationTag
{
    private int index = 0;
    private int count = 0;
    private int begin = -1;
    private int end = -1;
    private int step = -1;
    private Object current = null;
    private String var = null;
    private String varStatus = null;
    private LoopTagStatus loopStatus = null;

    protected abstract void prepare();

    public int doStartTag() throws Exception
    {
        this.index = 0;
        this.count = 0;
        this.prepare();

        if(this.end != -1 && this.begin > this.end)
        {
            return SKIP_BODY;
        }

        if(this.begin > -1)
        {
            this.index = this.begin;
        }
        else
        {
            this.index = 0;
        }

        if(this.step < 1)
        {
            this.step = 1;
        }

        this.count = 0;
        this.index = this.index - this.step;
        return EVAL_BODY_INCLUDE;
    }

    public int doAfterBody() throws Exception
    {
        if(this.hasNext())
        {
            this.setCurrent(this.next());
            return EVAL_BODY_AGAIN;
        }
        else
        {
            return SKIP_BODY;
        }
    }

    @Override
    public int doEndTag() throws Exception
    {
        if(this.var != null)
        {
            this.pageContext.removeAttribute(this.var);
        }

        if(this.varStatus != null)
        {
            this.pageContext.removeAttribute(this.varStatus);
        }

        return super.doEndTag();
    }

    /**
     * @return boolean
     */
    public boolean isFirst()
    {
        return (this.count == 1) || (this.index == 0);
    };

    /**
     * @return boolean
     */
    public boolean isLast()
    {
        return ((this.index + this.step) >= this.end);
    };

    /**
     * @return boolean
     */
    public abstract boolean hasNext();

    /**
     * @return boolean
     */
    public abstract Object next();

    /**
     * @return the index
     */
    public int getIndex()
    {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(int index)
    {
        this.index = index;
    }

    /**
     * @return the begin
     */
    public int getBegin()
    {
        return begin;
    }

    /**
     * @param begin the begin to set
     */
    public void setBegin(int begin)
    {
        this.begin = begin;
    }

    /**
     * @return the count
     */
    public int getCount()
    {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count)
    {
        this.count = count;
    }

    /**
     * @return the end
     */
    public int getEnd()
    {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(int end)
    {
        this.end = end;
    }

    /**
     * @return the step
     */
    public int getStep()
    {
        return step;
    }

    /**
     * @param step the step to set
     */
    public void setStep(int step)
    {
        this.step = step;
    }

    /**
     * @return the current
     */
    public Object getCurrent()
    {
        return current;
    }

    /**
     * @param current the current to set
     */
    public void setCurrent(Object current)
    {
        this.current = current;

        if(this.var != null)
        {
            this.pageContext.setAttribute(this.var, this.current);
        }
    }

    /**
     * @return the var
     */
    public String getVar()
    {
        return var;
    }

    /**
     * @param var the var to set
     */
    public void setVar(String var)
    {
        this.var = var;
    }

    /**
     * @return the varStatus
     */
    public String getVarStatus()
    {
        return varStatus;
    }

    /**
     * @param varStatus the varStatus to set
     */
    public void setVarStatus(String varStatus)
    {
        this.varStatus = varStatus;
    }

    /**
     * @return the loopStatus
     */
    public LoopTagStatus getLoopStatus()
    {
        return loopStatus;
    }

    /**
     * @param loopStatus the loopStatus to set
     */
    public void setLoopStatus(LoopTagStatus loopStatus)
    {
        this.loopStatus = loopStatus;
    };
}
