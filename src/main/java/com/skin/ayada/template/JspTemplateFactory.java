/*
 * $RCSfile: JspTemplateFactory.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-11-13 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.template;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.compile.JspCompiler;
import com.skin.ayada.config.TemplateConfig;
import com.skin.ayada.factory.FactoryClassLoader;
import com.skin.ayada.source.SourceFactory;
import com.skin.ayada.util.IO;
import com.skin.ayada.util.StringUtil;
import com.skin.ayada.util.TemplateUtil;

/**
 * <p>Title: JspTemplateFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class JspTemplateFactory extends TemplateFactory
{
    private String work;
    private String classPath;
    private static final Logger logger = LoggerFactory.getLogger(JspTemplateFactory.class);

    public JspTemplateFactory()
    {
    }

    /**
     * @param work
     */
    public JspTemplateFactory(String work, String classPath)
    {
        this.work = work;
        this.classPath = classPath;
    }

    /**
     * @param sourceFactory
     * @param file
     * @param encoding
     * @return Template
     * @throws Exception
     */
    @Override
    public Template create(SourceFactory sourceFactory, String file, String encoding) throws Exception
    {
        Template template = super.create(sourceFactory, file, encoding);

        long t1 = System.currentTimeMillis();
        JspTemplate jspTemplate = compile(template, this.getWork());
        long t2 = System.currentTimeMillis();

        if(logger.isDebugEnabled())
        {
            logger.debug("jsp compile time: " + (t2 - t1));
        }

        return jspTemplate;
    }

    /**
     * @param source
     * @return Template
     */
    @Override
    public Template compile(String source) throws Exception
    {
        Template template = super.compile(source);

        long t1 = System.currentTimeMillis();
        JspTemplate jspTemplate = this.compile(template, this.getWork());
        long t2 = System.currentTimeMillis();

        if(logger.isDebugEnabled())
        {
            logger.debug("jsp compile time: " + (t2 - t1));
        }

        return jspTemplate;
    }

    /**
     * @param template
     * @return TemplateHandler
     */
    private JspTemplate compile(Template template, String work) throws Exception
    {
        String home = template.getHome();
        String root = ""; // this.getRootPath(home);
        String path = this.join(root, template.getPath().trim());
        String className = this.getClassName(path);
        String simpleName = this.getSimpleName(className);
        String packageName = this.getPackageName(className);
        String classPath = StringUtil.replace(packageName, ".", "/") + "/" + simpleName;

        if(logger.isDebugEnabled())
        {
            logger.debug("home: " + home + "\r\n"
                + "root: " + root + "\r\n"
                + "path: " + path + "\r\n"
            	+ "className: " + className + "\r\n"
                + "simpleName: " + simpleName + "\r\n"
                + "packageName: " + packageName + "\r\n"
                + "classPath: " + classPath + "\r\n"
                + "work: " + work + "\r\n");
        }

        boolean fastJstl = TemplateConfig.getInstance().getBoolean("ayada.compile.fast-jstl");
        JspCompiler jspCompiler = new JspCompiler();
        jspCompiler.setFastJstl(fastJstl);

        String source = jspCompiler.compile(template, simpleName, packageName);
        File srcFile = new File(work, classPath + ".java");
        File clsFile = new File(work, classPath + ".class");
        File tplFile = new File(work, classPath + ".tpl");

        write(source, srcFile);
        write(this.getTemplateDescription(template), tplFile);

        String lib = this.getClassPath();

        if(lib == null)
        {
            lib = System.getProperty("java.class.path");
        }

        String[] args = new String[]{
            "-d", work,
            "-nowarn",
            "-g",
            "-encoding", "UTF-8",
            "-classpath", this.getClassPath(),
            "-Xlint:unchecked",
            srcFile.getCanonicalPath()
        };

        javax.tools.JavaCompiler javaCompiler = javax.tools.ToolProvider.getSystemJavaCompiler();
        int status = javaCompiler.run(System.in, System.out, System.out, args);

        if(status == 0)
        {
            byte[] bytes = getBytes(clsFile);
            JspTemplate jspTemplate = (JspTemplate)(getInstance(className, bytes));
            jspTemplate.setHome(template.getHome());
            jspTemplate.setPath(template.getPath());
            jspTemplate.setNodes(template.getNodes());
            jspTemplate.setLastModified(template.getLastModified());
            jspTemplate.setUpdateTime(template.getUpdateTime());
            return jspTemplate;
        }
        throw new Exception("compile error: " + status);
    }

    /**
     * @param parent
     * @param child
     * @return String
     */
    private String join(String parent, String child)
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append(parent.trim());

        if(child.startsWith("/") || child.startsWith("\\"))
        {
            buffer.append(child.trim());
        }
        else
        {
            buffer.append("/");
            buffer.append(child);
        }

        return buffer.toString();
    }

    /**
     * @param home
     * @return String
     */
    protected String getRootPath(String home)
    {
        String temp = StringUtil.replace(home.trim(), "\\", "/");

        if(temp.endsWith("/"))
        {
            temp = temp.substring(0, temp.length() - 1);
        }

        int k = temp.lastIndexOf("/");

        if(k > -1)
        {
            return temp.substring(k + 1);
        }

        return temp;
    }

    /**
     * @param file
     * @return byte[]
     * @throws IOException
     */
    private byte[] getBytes(File file) throws IOException
    {
        InputStream inputStream = null;

        try
        {
            inputStream = new FileInputStream(file);
            byte[] buffer = new byte[(int)file.length()];
            inputStream.read(buffer, 0, buffer.length);
            return buffer;
        }
        finally
        {
            IO.close(inputStream);
        }
    }

    /**
     * @param className
     * @return String
     */
    private String getClassName(String path)
    {
        String className = path;
        int k = className.lastIndexOf(".");

        if(k > -1)
        {
            className = className.substring(0, k);
        }

        className = StringUtil.replace(className, "/", ".");
        className = StringUtil.replace(className, "\\", ".");
        className = StringUtil.replace(className, "..", ".");

        if(className.startsWith("."))
        {
            className = "_jsp" + className;
        }
        else
        {
            className = "_jsp." + className;
        }

        k = className.lastIndexOf(".");
        String simpleName = className.substring(k + 1) + "Template";
        return className.substring(0, k + 1) + Character.toUpperCase(simpleName.charAt(0)) + simpleName.substring(1);
    }

    /**
     * @param className
     * @return String
     */
    private String getSimpleName(String className)
    {
        int k = className.lastIndexOf(".");

        if(k > -1)
        {
            return className.substring(k + 1);
        }
        return className;
    }

    /**
     * @param className
     * @return String
     */
    private String getPackageName(String className)
    {
        int k = className.lastIndexOf(".");

        if(k > -1)
        {
            return className.substring(0, k);
        }
        return "_jsp";
    }

    /**
     * @param className
     * @param bytes
     * @return Object
     */
    private Object getInstance(String className, byte[] bytes)
    {
        FactoryClassLoader factoryClassLoader = FactoryClassLoader.getInstance();
        Class<?> clazz = factoryClassLoader.create(className, bytes);

        try
        {
            return clazz.newInstance();
        }
        catch(InstantiationException e)
        {
            e.printStackTrace();
        }
        catch(IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @return String
     */
    private String getTemplateDescription(Template template)
    {
        StringWriter stringWriter = new StringWriter();
        TemplateUtil.print(template, new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    /**
     * @param source
     * @param file
     * @throws IOException
     */
    private void write(String source, File file) throws IOException
    {
        File parent = file.getParentFile();

        if(parent.exists() == false)
        {
            parent.mkdirs();
        }

        IO.write(source.getBytes("UTF-8"), file);
    }

    /**
     * @return the work
     */
    public String getWork()
    {
        return this.work;
    }

    /**
     * @param work the work to set
     */
    public void setWork(String work)
    {
        this.work = work;
    }

    /**
     * @param classPath the classPath to set
     */
    public void setClassPath(String classPath)
    {
        this.classPath = classPath;
    }

    /**
     * @return the classPath
     */
    public String getClassPath()
    {
        return this.classPath;
    }

    public static void main(String[] args)
    {
        String home = "/web/resin_vhost/finder.com/template";
        String path = "/web/resin_vhost/finder.com/template/finder/workspace.jsp";
        path = path.substring(home.length());

        JspTemplateFactory jspTemplateFactory = new JspTemplateFactory();
        String className = jspTemplateFactory.getClassName(path);
        String simpleName = jspTemplateFactory.getSimpleName(className);
        String packageName = jspTemplateFactory.getPackageName(className);
        System.out.println("className: " + className);
        System.out.println("simpleName: " + simpleName);
        System.out.println("packageName: " + packageName);
    }
}
