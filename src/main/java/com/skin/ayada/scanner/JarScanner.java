/*
 * $RCSfile: ZipScanner.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-01-13 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.scanner;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.jar.JarFile;

/**
 * <p>Title: ZipScanner</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class JarScanner extends ZipScanner {
    private static final String JAR_URL_PREFIX = "jar:";
    private static final String JAR_URL_SEPARATOR = "!/";
    private static final String FILE_URL_PREFIX = "file:";

    /**
     * jar:file:/E:/WorkSpace/ayada/lib/slf4j-api-1.7.2.jar!/org/slf4j/Logger.class
     * jar:http://www.mytest.com/slf4j-api-1.7.2.jar!/org/slf4j/Logger.class
     * @param work
     */
    public JarScanner(String work) {
        super(work);
    }

    /**
     * @param url
     * @param visitor
     * @throws Exception
     */
    public static void accept(URL url, Visitor visitor) throws Exception {
        JarFile jarFile = null;

        try {
            jarFile = getJarFile(url);
            String root = getRootPath(url);
            accept(jarFile, visitor, root);
        }
        finally {
            if(jarFile != null) {
                jarFile.close();
            }
        }
    }

    /**
     * @param url
     * @return JarFile
     * @throws IOException
     */
    public static JarFile getJarFile(URL url) throws IOException {
        URLConnection urlConnection = url.openConnection();

        if(urlConnection instanceof JarURLConnection) {
            JarURLConnection jarURLConnection = (JarURLConnection)urlConnection;
            jarURLConnection.setUseCaches(false);
            return jarURLConnection.getJarFile();
        }
        else {
            String fileUrl = url.getFile();
            int separatorIndex = fileUrl.indexOf(JAR_URL_SEPARATOR);

            if(separatorIndex != -1) {
                String jarFileUrl = fileUrl.substring(0, separatorIndex);
                return getJarFile(jarFileUrl);
            }
            else {
                return new JarFile(fileUrl);
            }
        }
    }

    /**
     * @param jarFileUrl
     * @return JarFile
     * @throws IOException
     */
    public static JarFile getJarFile(String jarFileUrl) throws IOException {
        if(jarFileUrl.startsWith(FILE_URL_PREFIX)) {
            try {
                return new JarFile(toURI(jarFileUrl).getSchemeSpecificPart());
            }
            catch(URISyntaxException ex) {
                return new JarFile(jarFileUrl.substring(FILE_URL_PREFIX.length()));
            }
        }
        else {
            return new JarFile(jarFileUrl);
        }
    }

    /**
     * @param url
     * @param name
     * @return URL
     * @throws IOException 
     */
    public static URL join(URL url, String name) throws IOException {
        if(name.startsWith("/")) {
            return new URL(getJarURL(url) + "!" + name);
        }
        else {
            return new URL(getJarURL(url) + "!/" + name);
        }
    }

    /**
     * @param url
     * @return String
     */
    public static String getJarURL(URL url) {
        String path = url.toExternalForm();
        int k = path.indexOf("!/");

        if(k > -1) {
            path = path.substring(0, k);
        }

        if(!path.startsWith(JAR_URL_PREFIX)) {
            return JAR_URL_PREFIX + path;
        }
        return path;
    }

    /**
     * @param url
     * @return String
     */
    public static String getRootPath(URL url) {
        String root = "/";
        String path = url.toExternalForm();
        int k = path.indexOf("!/");

        if(k > -1) {
            root = path.substring(k + 2);
        }

        if(!root.endsWith("/")) {
            root = root + "/";
        }
        return root;
    }

    /**
     * @param url
     * @return URI
     * @throws URISyntaxException
     */
    public static URI toURI(URL url) throws URISyntaxException {
        return toURI(url.toString());
    }

    /**
     * @param location
     * @return URI
     * @throws URISyntaxException
     */
    public static URI toURI(String location) throws URISyntaxException {
        return new URI(location.replace(" ", "%20"));
    }
}
