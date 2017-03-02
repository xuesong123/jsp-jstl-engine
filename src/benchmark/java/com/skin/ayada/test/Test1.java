/*
 * $RCSfile: Test1.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-11-08 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.test;

import java.io.StringWriter;
import java.util.Date;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.skin.ayada.jstl.util.DateUtil;

/**
 * <p>Title: Test1</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class Test1 {
    /**
     * @param args
     */
    public static void main(String[] args) {
        Velocity.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        Velocity.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        Velocity.setProperty(RuntimeConstants.RUNTIME_LOG, "velocity.log");
        Velocity.init();

        StringWriter writer = new StringWriter();
        VelocityContext context = new VelocityContext();
        context.put("a", 1);
        context.put("b", 2);
        context.put("c", 3);
        context.put("sysTime", new Date());
        context.put("dateUtil", new DateUtil());

        String template = ""
                + "#set($timeString = $dateUtil.format($sysTime, 'yyyy-MM-dd HH:mm:ss') + 'ssss')\r\n"
                + "#set($value = ($a + $b) * $c)\r\n"
                + "$value\r\n"
                + "${($a + $b) * $c}\r\n"
                + "$dateUtil.format($sysTime, 'yyyy-MM-dd HH:mm:ss')\r\n"
                + "$timeString\r\n";

        Velocity.evaluate(context, writer, "mergeTemplate", template);
        System.out.println(writer.toString());
    }

}
