/*
 * $RCSfile: UserHandler.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-27 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.handler;

import java.util.ArrayList;
import java.util.List;

import test.com.skin.ayada.model.User;

/**
 * <p>Title: UserHandler</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class UserHandler {
    /**
     * @param userId
     * @return User
     */
    public static User getUserById(long userId) {
        User user = new User();
        user.setUserId(userId);
        user.setUserName("test" + userId);
        user.setAge(22);
        return user;
    }

    /**
     * @param size
     * @return List<User>
     */
    public static List<User> getUserList(int size) {
        List<User> userList = new ArrayList<User>();

        for(int i = 0; i < size; i++) {
            User user = new User();
            user.setUserId(Long.valueOf(i + 1));
            user.setUserName("test" + (i + 1));
            user.setAge(i);
            userList.add(user);
        }
        return userList;
    }
}
