/*
 * $RCSfile: JarSourceFactory.java,v $
 * $Revision: 1.1 $
 * $Date: 2014-12-10 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.source;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.skin.ayada.Source;
import com.skin.ayada.SourceFactory;
import com.skin.ayada.scanner.JarScanner;
import com.skin.ayada.scanner.Scanner.Visitor;

/**
 * <p>Title: JarSourceFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class JarSourceFactory extends SourceFactory {
    private URL url;

    /**
     * jar:file:/E:/WorkSpace/ayada/lib/finder-res1.jar!/finder
     * jar:http://www.mytest.com/finder-res1.jar!/finder
     * home: /finder
     * {@linkplain JarScanner}
     * @param file
     */
    public JarSourceFactory(File file) {
        try {
            this.url = file.toURI().toURL();
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param url
     */
    public JarSourceFactory(URL url) {
        this.url = url;
    }

    /**
     * @param visitor
     * @throws Exception
     */
    @Override
    public void accept(Visitor visitor) throws Exception {
        JarScanner.accept(this.url, visitor);
    }

    /**
     * @param path
     * @return Source
     */
    @Override
    public Source getSource(String path) {
        JarFile jarFile = null;

        try {
            jarFile = this.getJarFile();
            ZipEntry zipEntry = jarFile.getEntry(path);

            if(zipEntry == null) {
                throw new RuntimeException("file not found: " + path);
            }
            return new Source(this.url.toExternalForm(), path, this.getSourceType(path), this.getLastModified(path));
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            if(jarFile != null) {
                try {
                    jarFile.close();
                }
                catch(IOException e) {
                }
            }
        }
    }

    /**
     * @param path
     * @return URL
     */
    @Override
    public URL getResource(String path) {
        try {
            return JarScanner.join(this.url, path);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param path
     * @return long
     */
    @Override
    public long getLastModified(String path) {
        return 1000000L;
    }

    /**
     * @param path
     * @return boolean
     */
    @Override
    public boolean exists(String path) {
        ZipFile zipFile = null;

        try {
            zipFile = this.getJarFile();
            ZipEntry zipEntry = zipFile.getEntry(path);
            return (zipEntry != null);
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            if(zipFile != null) {
                try {
                    zipFile.close();
                }
                catch(IOException e) {
                }
            }
        }
    }

    /**
     * @return JarFile
     * @throws IOException
     */
    public JarFile getJarFile() throws IOException {
        return JarScanner.getJarFile(this.url);
    }
}
