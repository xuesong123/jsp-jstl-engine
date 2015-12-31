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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skin.ayada.compile.JspCompiler;
import com.skin.ayada.config.TemplateConfig;
import com.skin.ayada.factory.ClassFactory;
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
public class JspTemplateFactory extends TemplateFactory {
    private String work;
    private String prefix;
    private String classPath;
    private static final Logger logger = LoggerFactory.getLogger(JspTemplateFactory.class);

    public static void main(String[] args) {
    	JspTemplateFactory jtf = new JspTemplateFactory();
        String path = "/default/a.b.jsp";
    	path = "/test.jsp";
    	path = "/!.jsp";

        String className = jtf.getClassName(path);
        String simpleName = jtf.getSimpleName(className);
        String packageName = jtf.getPackageName(className);
        String classPath = StringUtil.replace(packageName, ".", "/") + "/" + simpleName;

        System.out.println("path: " + path);
        System.out.println("className: " + className);
        System.out.println("simpleName: " + simpleName);
        System.out.println("packageName: " + packageName);
        System.out.println("classPath: " + classPath);
    }

    public JspTemplateFactory() {
    }

    /**
     * @param work
     */
    public JspTemplateFactory(String work, String classPath) {
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
    public Template create(SourceFactory sourceFactory, String file, String encoding) throws Exception {
        Template template = super.create(sourceFactory, file, encoding);

        if(logger.isDebugEnabled()) {
            long t1 = System.currentTimeMillis();
            JspTemplate jspTemplate = compile(template, this.getWork());
            long t2 = System.currentTimeMillis();
            logger.debug("jsp compile time: " + (t2 - t1));
            return jspTemplate;
        }
        else {
            return compile(template, this.getWork());
        }
    }

    /**
     * @param source
     * @return Template
     */
    @Override
    public Template compile(String source) throws Exception {
        Template template = super.compile(source);

        if(logger.isDebugEnabled()) {
            long t1 = System.currentTimeMillis();
            JspTemplate jspTemplate = this.compile(template, this.getWork());
            long t2 = System.currentTimeMillis();
            logger.debug("jsp compile time: " + (t2 - t1));
            return jspTemplate;
        }
        else {
            return this.compile(template, this.getWork());
        }
    }

    /**
     * @param template
     * @return TemplateHandler
     */
    protected JspTemplate compile(Template template, String work) throws Exception {
        String home = template.getHome();
        String path = template.getPath().trim();
        String className = this.getClassName(path);
        String simpleName = this.getSimpleName(className);
        String packageName = this.getPackageName(className);
        String classPath = StringUtil.replace(packageName, ".", "/") + "/" + simpleName;

        if(logger.isDebugEnabled()) {
            logger.debug("home: " + home
                + ", path: " + path
                + ", className: " + className
                + ", simpleName: " + simpleName
                + ", packageName: " + packageName
                + ", classPath: " + classPath
                + ", work: " + work);
        }

        File tplFile = new File(work, classPath + ".tpl");
        File srcFile = new File(work, classPath + ".java");
        File classFile = new File(work, classPath + ".class");
        File parent = classFile.getParentFile();
        String lib = this.getClassPath();

        if(lib == null) {
            lib = ClassFactory.getClassPath();
        }

        if(parent.exists() == false) {
            parent.mkdirs();
        }

        if(logger.isDebugEnabled()) {
        	logger.debug("tpl: {}, src: {}, class: {}",
        			tplFile.getAbsoluteFile(),
        			srcFile.getAbsoluteFile(),
        			classFile.getAbsoluteFile());
        }

        boolean fastJstl = TemplateConfig.getFashJstl();
        JspCompiler jspCompiler = new JspCompiler();
        jspCompiler.setFastJstl(fastJstl);
        String source = jspCompiler.compile(template, simpleName, packageName);
        this.write(source, srcFile);
        this.write(this.getTemplateDescription(template), tplFile);
        File[] files = classFile.getParentFile().listFiles();

        if(classFile.exists()) {
        	classFile.delete();
        }

        if(files != null && files.length > 0) {
            String name = null;
            String prefix = simpleName + "$";

            for(File file : files) {
                name = file.getName();

                if(name.startsWith(prefix) && name.endsWith(".class")) {
                    file.delete();
                }
            }
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

        if(logger.isDebugEnabled()) {
            logger.debug("compile: " + srcFile.getAbsolutePath());
        }

        javax.tools.JavaCompiler javaCompiler = javax.tools.ToolProvider.getSystemJavaCompiler();

        if(javaCompiler == null) {
            throw new NullPointerException("'javax.tools.JavaCompiler' not found!, please add 'tools.jar' to class_path!");
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int status = javaCompiler.run(System.in, outputStream, outputStream, args);
        byte[] data = outputStream.toByteArray();

        if(data != null && data.length > 0) {
            logger.error(new String(data, "UTF-8"));
        }

        if(status == 0) {
            return this.load(template, className, new File[]{new File(work)});
        }
        else {
            throw new Exception("compile error: " + status);
        }
    }

    /**
     * @param template
     * @param className
     * @param classPath
     * @return JspTemplate
     * @throws IOException
     */
    protected JspTemplate load(Template template, String className, File[] classPath) throws Exception {
        JspTemplate jspTemplate = (JspTemplate)(ClassFactory.getInstance(className, classPath));
        jspTemplate.setHome(template.getHome());
        jspTemplate.setPath(template.getPath());
        jspTemplate.setNodes(template.getNodes());
        jspTemplate.setDependencies(template.getDependencies());
        jspTemplate.setLastModified(template.getLastModified());
        jspTemplate.setUpdateTime(template.getUpdateTime());
        return jspTemplate;
    }

    /**
     * @param parent
     * @param child
     * @return String
     */
    protected String join(String parent, String child) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(parent.trim());

        if(child.startsWith("/") || child.startsWith("\\")) {
            buffer.append(child.trim());
        }
        else {
            buffer.append("/");
            buffer.append(child);
        }
        return buffer.toString();
    }

    /**
     * @param home
     * @return String
     */
    protected String getRootPath(String home) {
        String temp = StringUtil.replace(home.trim(), "\\", "/");

        if(temp.endsWith("/")) {
            temp = temp.substring(0, temp.length() - 1);
        }

        int k = temp.lastIndexOf("/");

        if(k > -1) {
            return temp.substring(k + 1);
        }
        return temp;
    }

    /**
     * @param className
     * @return String
     */
    protected String getClassName(String path) {
        String className = StringUtil.replace(path, "\\", "/");
        int k = className.lastIndexOf(".");

        if(k > -1) {
            className = className.substring(0, k);
        }

        String[] array = StringUtil.split(className, "/", true, true);
        String simpleName = this.getJavaIdentifier(array[array.length - 1]);

        if(simpleName.length() < 1) {
        	throw new RuntimeException("UnsupportFileNameException: file \"" + path + "\"");
        }

        StringBuilder buffer = new StringBuilder(this.getPrefix());

        for(int i = 0, length = array.length - 1; i < length; i++) {
            buffer.append("._");
            buffer.append(array[i]);
        }

        buffer.append(".");
        buffer.append(Character.toUpperCase(simpleName.charAt(0)));
        buffer.append(simpleName.substring(1));
        buffer.append("Template");
        return buffer.toString();
    }

    /**
     * @param name
     * @return String
     */
    protected String getJavaIdentifier(String name) {
    	char c;
    	StringBuilder buffer = new StringBuilder();

    	for(int i = 0; i < name.length(); i++) {
    		c = name.charAt(i);

            if(Character.isJavaIdentifierPart(c)) {
                buffer.append(c);
            }
    	}
    	return buffer.toString();
    }

    /**
     * @param className
     * @return String
     */
    protected String getSimpleName(String className) {
        int k = className.lastIndexOf(".");

        if(k > -1) {
            return className.substring(k + 1);
        }
        return className;
    }

    /**
     * @param className
     * @return String
     */
    protected String getPackageName(String className) {
        int k = className.lastIndexOf(".");

        if(k > -1) {
            return className.substring(0, k);
        }
        return this.getPrefix();
    }

    /**
     * @return String
     */
    protected String getTemplateDescription(Template template) {
        StringWriter stringWriter = new StringWriter();
        TemplateUtil.print(template, new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    /**
     * @param source
     * @param file
     * @throws IOException
     */
    protected void write(String source, File file) throws IOException {
        File parent = file.getParentFile();

        if(parent.exists() == false) {
            parent.mkdirs();
        }
        IO.write(file, source.getBytes("UTF-8"));
    }

    /**
     * @param work the work to set
     */
    public void setWork(String work) {
        this.work = work;
    }

    /**
     * @return the prefix
     */
    public String getWork() {
        return this.work;
    }

    /**
     * @param prefix the prefix to set
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * @return the prefix
     */
    public String getPrefix() {
        if(this.prefix == null) {
            this.prefix = "_tpl._jsp";
        }
        return this.prefix;
    }

    /**
     * @param classPath the classPath to set
     */
    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    /**
     * @return the classPath
     */
    public String getClassPath() {
        return this.classPath;
    }
}
