/*
 * $RCSfile: CSVReader.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-04-25 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.csv;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.skin.ayada.io.Stream;

/**
 * <p>Title: CSVReader</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class CSVReader {
    private Feature feature;
    private Stream stream;

    /**
     * @param file
     * @param charset
     * @throws IOException
     */
    public CSVReader(File file, String charset) throws IOException {
        this.feature = new Feature();
        this.stream = Stream.getStream(file, charset, 8192);
    }

    /**
     * @param inputStream
     * @param charset
     * @throws IOException 
     */
    public CSVReader(InputStream inputStream, String charset) throws IOException {
        this.feature = new Feature();
        this.stream = Stream.getStream(inputStream, charset, 8192);
    }

    /**
     * @param reader
     */
    public CSVReader(Reader reader) {
        this.feature = new Feature();
        this.stream = new Stream(reader, 8192);
    }

    /**
     * @param reader
     * @param out
     * @param pattern
     * @throws IOException
     */
    public static void export(Reader reader, PrintWriter out, String pattern) throws IOException {
        CSVReader csv = new CSVReader(reader);
        List<String> headers = csv.getHeaders();
        out.println(headers);

        while(true) {
            List<String> data = csv.next();

            if(data != null) {
                DataSet dataSet = new DataSet(headers, data);
                out.println(dataSet.replace(pattern));
            }
            else {
                break;
            }
        }
    }

    /**
     * @return List<String>
     * @throws IOException
     */
    public List<String> getHeaders() throws IOException {
        return this.next();
    }

    /**
     * @return List<String>
     * @throws IOException
     */
    public List<String> next() throws IOException {
        int i = this.stream.peek();

        if(i == 0x0D && this.stream.peek(1) == 0x0A) {
            this.stream.read();
            this.stream.read();
            return null;
        }

        if(i == 0x0A || i == Stream.EOF) {
            this.stream.read();
            return null;
        }

        String value = null;
        List<String> list = new ArrayList<String>();

        while((value = this.getValue(this.stream)) != null) {
            list.add(value);
        }
        return list;
    }

    /**
     * @param stream
     * @return String
     * @throws IOException
     */
    public String getValue(Stream stream) throws IOException {
        if(this.feature.getSkipWhitespace()) {
            this.skipWhitespace(stream);
        }

        int i = 0;
        int quote = stream.peek();
        char seperator = this.feature.getSeperator();

        /**
         * \n
         */
        if(quote == 0x0A || quote == Stream.EOF) {
            stream.read();
            return null;
        }

        /**
         * \r\n
         */
        if(quote == 0x0D && stream.peek(1) == 0x0A) {
            stream.read();
            stream.read();
            return null;
        }

        if(quote == this.feature.getQuote()) {
            stream.read();
        }
        else {
            quote = 0;
        }

        StringBuilder buffer = new StringBuilder();

        if(quote == this.feature.getQuote()) {
            while((i = stream.read()) != Stream.EOF) {
                if(i == quote) {
                    if(stream.peek() == quote) {
                        stream.read();
                        buffer.append((char)i);
                    }
                    else {
                        break;
                    }
                }
                else {
                    buffer.append((char)i);
                }
            }

            if(this.feature.getSkipWhitespace()) {
                this.skipWhitespace(stream);
            }

            if(stream.peek() == seperator) {
                stream.read();
            }
        }
        else {
            while((i = stream.peek()) != Stream.EOF) {
                if(i == seperator) {
                    stream.read();
                    break;
                }
                else if(i == 0x0A) {
                    break;
                }
                else {
                    stream.read();

                    if(i != 0x0D) {
                        buffer.append((char)i);
                    }
                }
            }
        }
        return buffer.toString();
    }

    /**
     * @return Feature
     */
    public Feature getFeature() {
        return this.feature;
    }

    /**
     * @param feature
     */
    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    /**
     * @param stream
     * @throws IOException
     */
    public void skipWhitespace(Stream stream) throws IOException {
        while(stream.peek() == ' ') {
            stream.read();
        }
    }

    /**
     * close the stream
     */
    public void close() {
        if(this.stream != null) {
            this.stream.close();
        }
    }
}
