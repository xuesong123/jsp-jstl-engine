/*
 * $RCSfile: MemMonitorTest.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-03-03 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.ayada.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.skin.ayada.util.MemMonitor;

/**
 * <p>Title: MemMonitorTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class MemMonitorTest
{
    public static void main(String[] args)
    {
        OutputStream outputStream = null;

        try
        {
            outputStream = new FileOutputStream("D:\\mem.log");
            PrintWriter out = new PrintWriter(outputStream);
            MemMonitor memMonitor = new MemMonitor();

            for(int i = 0; i < 10000; i++)
            {
                String[] a = new String[200];

                for(int j = 0; j < a.length; j++)
                {
                    a[j] = String.valueOf(j);
                }

                memMonitor.test(out, (i == 0), true);
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(outputStream != null)
            {
                try
                {
                    outputStream.close();
                }
                catch(IOException e)
                {
                }
            }
        }
    }
}
