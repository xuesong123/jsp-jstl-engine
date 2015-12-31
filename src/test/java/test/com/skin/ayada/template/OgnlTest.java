/*
 * $RCSfile: OgnlTest.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-02-26 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ognl.Ognl;
import ognl.OgnlException;
import test.com.skin.ayada.handler.UserHandler;
import test.com.skin.ayada.model.User;

import com.skin.ayada.jstl.util.BeanUtil;

/**
 * <p>Title: OgnlTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class OgnlTest {
    public static void main(String[] args) {
        try {
            BeanUtil ognlUtil = new BeanUtil();
            Map<Object, Object> map = new HashMap<Object, Object>();
            map.put("util", ognlUtil);
            map.put("stringValue", "  ");
            map.put("arrayValue", new int[]{});

            List<User> userList = UserHandler.getUserList(16);
            map.put("userList", userList);

            Object result = Ognl.getValue("((userList.size() + 1) / 2).intValue()", map);
            System.out.println(result.getClass().getName() + ": " + result);
            System.out.println(Ognl.getValue("@java.lang.System@out.println(\"123\")", map));
        }
        catch(OgnlException e) {
            e.printStackTrace();
        }
    }
}
