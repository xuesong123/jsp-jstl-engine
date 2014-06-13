/*
 * $RCSfile: User.java,v $$
 * $Revision: 1.1  $
 * $Date: 2013-2-26  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package example.model;

import java.io.Serializable;

/**
 * <p>Title: User</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class User implements Serializable
{
    private static final long serialVersionUID = 1L;
    private long userId;
    private String userName;
    private int age;

    /**
     * @return the userId
     */
    public long getUserId()
    {
        return this.userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(long userId)
    {
        this.userId = userId;
    }

    /**
     * @return the userName
     */
    public String getUserName()
    {
        return this.userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    /**
     * @return the age
     */
    public int getAge()
    {
        return this.age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age)
    {
        this.age = age;
    }
}
