/*
 * $RCSfile: ClassPathClassLoader.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-11-06 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.factory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * <p>Title: ClassPathClassLoader</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @version 1.0
 */
public class ClassPathClassLoader extends ClassLoader {
    private String classPath;

    /**
     * @param parent
     * @param classPath
     */
    public ClassPathClassLoader(ClassLoader parent, String classPath) {
        super(parent);
        this.classPath = classPath;
    }

    /**
     * @param name
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = loadClassBytes(name);
        Class<?> clazz = defineClass(name, bytes, 0, bytes.length);

        if(clazz == null) {
            throw new ClassNotFoundException();
        }
        return clazz;
    }

    /**
     * @param className
     * @return byte[]
     * @throws ClassNotFoundException
     */
    private byte[] loadClassBytes(String className) throws ClassNotFoundException {
        FileInputStream fileInputStream = null;

        try {
            int i = 0;
            String classFile = getClassFile(className);
            fileInputStream = new FileInputStream(classFile);
            FileChannel fileChannel = fileInputStream.getChannel();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            WritableByteChannel writableByteChannel = Channels.newChannel(outputStream);
            ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

            while(true) {
                i = fileChannel.read(buffer);

                if(i == 0 || i == -1) {
                    break;
                }

                buffer.flip();
                writableByteChannel.write(buffer);
                buffer.clear();
            }
            return outputStream.toByteArray();
        }
        catch(IOException e) {
            throw new ClassNotFoundException(className);
        }
        finally {
            if(fileInputStream != null) {
                try {
                    fileInputStream.close();
                }
                catch(IOException e) {
                }
            }
        }
    }

    /**
     * @param className
     * @return String
     */
    private String getClassFile(String className) {
        String path = className.replace('.', File.separatorChar) + ".class";
        File file = new File(this.classPath, path);
        return file.getAbsolutePath();
    }
}
