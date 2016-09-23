/*
 * $RCSfile: MappedSourceFactory.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-3-26 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.source;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.skin.ayada.source.DefaultSourceFactory;
import com.skin.ayada.util.Path;

/**
 * <p>Title: MappedSourceFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class MappedSourceFactory extends DefaultSourceFactory {
    private Map<String, String> mapped;

    public MappedSourceFactory() {
        this.mapped = new HashMap<String, String>();
    }

    /**
     * @param prefix
     * @param home
     */
    public void add(String prefix, String home) {
        this.mapped.put(prefix, home);
    }

    /**
     * @param path
     */
    @Override
    public File getFile(String path) {
        if(path == null) {
            throw new NullPointerException("path must be not null !");
        }

        if(!Path.contains(this.getHome(), path)) {
            throw new RuntimeException("home: " + this.getHome() + ", file: " + path + " not exists !");
        }

        File file = null;
        String full = Path.join(this.getHome(), path);
        String relativePath = Path.getRelativePath(this.getHome(), full);

        for(Map.Entry<String, String> entry : this.mapped.entrySet()) {
            String prefix = entry.getKey();

            if(relativePath.startsWith(prefix)) {
                file = new File(entry.getValue(), relativePath.substring(prefix.length()));
                break;
            }
        }

        if(file == null) {
            file = new File(full);
        }

        if(!file.exists() || !file.isFile()) {
            throw new RuntimeException("home: " + this.getHome() + ", file: " + file.getAbsolutePath() + " not exists !");
        }
        return file;
    }
}
