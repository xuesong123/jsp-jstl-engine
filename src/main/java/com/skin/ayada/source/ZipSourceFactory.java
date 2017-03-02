/*
 * $RCSfile: ZipSourceFactory.java,v $
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
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.skin.ayada.Source;
import com.skin.ayada.SourceFactory;
import com.skin.ayada.scanner.JarScanner;
import com.skin.ayada.scanner.Scanner.Visitor;
import com.skin.ayada.scanner.ZipScanner;

/**
 * <p>Title: ZipSourceFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class ZipSourceFactory extends SourceFactory {
    private String file;
    private String home;

    /**
     * @param file
     * @param home
     */
    public ZipSourceFactory(String file, String home) {
        this.file = file;
        this.home = home;
    }

    /**
     * @param visitor
     * @throws Exception
     */
    @Override
    public void accept(Visitor visitor) throws Exception {
        ZipScanner.accept(new File(this.file), visitor, this.home);
    }

    /**
     * @param path
     * @return Source
     */
    @Override
    public Source getSource(String path) {
        ZipFile zipFile = null;

        try {
            zipFile = this.getZipFile();
            ZipEntry zipEntry = zipFile.getEntry(path);

            if(zipEntry == null) {
                throw new RuntimeException("file not found: " + path);
            }
            return new Source(this.file, path, this.getSourceType(path), this.getLastModified(path));
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
     * @param path
     * @return URL
     */
    @Override
    public URL getResource(String path) {
        try {
            return JarScanner.join(new File(this.file).toURI().toURL(), path);
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
        return new File(this.file).lastModified();
    }

    /**
     * @param path
     * @return boolean
     */
    @Override
    public boolean exists(String path) {
        ZipFile zipFile = null;

        try {
            zipFile = this.getZipFile();
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
     * @return ZipFile
     * @throws IOException
     */
    protected ZipFile getZipFile() throws IOException {
        File file = new File(this.file);

        if(file.exists() && file.isFile()) {
            return new ZipFile(file);
        }
        throw new IOException(this.file + "not exists or is not zip file");
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

    /**
     * @return String
     */
    public String getHome() {
        return this.home;
    }

    /**
     * @param home
     */
    public void setHome(String home) {
        this.home = home;
    }
}
