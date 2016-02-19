/*
 * $RCSfile: ZipSourceFactory.java,v $$
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
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.skin.ayada.source.Source;
import com.skin.ayada.source.SourceFactory;
import com.skin.ayada.util.IO;

/**
 * <p>Title: ZipSourceFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class ZipSourceFactory extends SourceFactory {
    private String file;

    /**
     *
     */
    public ZipSourceFactory() {
    }

    /**
     * @param home
     */
    public ZipSourceFactory(String home) {
        this.setHome(home);
    }

    /**
     * @param path
     * @param encoding
     * @return Source
     */
    @Override
    public Source getSource(String path, String encoding) {
        ZipFile zipFile = null;
        InputStream inputStream = null;
        File file = new File(this.file);

        try {
            String realPath = (this.getHome() + path).replace('\\', '/');

            while(realPath.startsWith("/")) {
                realPath = realPath.substring(1);
            }

            zipFile = new ZipFile(file);
            ZipEntry zipEntry = zipFile.getEntry(realPath);

            if(zipEntry == null) {
                throw new RuntimeException("file not found: " + realPath);
            }

            inputStream = zipFile.getInputStream(zipEntry);
            String content = IO.read(inputStream, encoding, 4096);
            return new Source(this.file + "!/" + realPath, path, content, this.getSourceType(path), file.lastModified());
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                }
                catch(IOException e) {
                }
            }
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
     * @param path
     * @return long
     */
    @Override
    public long getLastModified(String path) {
        return new File(this.file).lastModified();
    }

    /**
     * @param path
     * @return boolean
     */
    @Override
    public boolean exists(String path) {
        ZipFile zipFile = null;
        File file = new File(this.file);

        try {
            zipFile = new ZipFile(file);
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
     * @return the file
     */
    public String getFile() {
        return this.file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(String file) {
        this.file = file;
    }
}
