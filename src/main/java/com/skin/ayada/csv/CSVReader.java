/*
 * $RCSfile: CSVReader.java,v $$
 * $Revision: 1.1 $
 * $Date: 2013-04-25 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.csv;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import com.skin.ayada.io.StringStream;

/**
 * <p>Title: CSVReader</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class CSVReader {
    /**
     * @param args
     */
    public static void main(String[] args) {
        test3();
    }

    /**
     * for test
     */
    public static void test1() {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try {
            inputStream = new FileInputStream("D:\\test.csv");
            inputStreamReader = new InputStreamReader(inputStream, "gbk");
            bufferedReader = new BufferedReader(inputStreamReader);
            List<String> headers = CSVReader.getHeaders(bufferedReader);
            System.out.println(headers);

            while(true) {
                List<String> data = CSVReader.next(bufferedReader);

                if(data != null) {
                    DataSet dataSet = new DataSet(headers, data);
                    System.out.println(dataSet.getInsert("test", false));
                }
                else {
                    break;
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                }
                catch(IOException e) {
                }
            }
        }
    }

    /**
     * for test
     */
    public static void test2() {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        String pattern = "insert into test(id, userName, password, sex, birthday)"
                + " values (${id}, '${userName}', '${password}', ${sex}, '${birthday}');";

        try {
            inputStream = new FileInputStream("D:\\test.csv");
            inputStreamReader = new InputStreamReader(inputStream, "gbk");
            CSVReader.export(inputStreamReader, new PrintWriter(System.out), pattern);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                }
                catch(IOException e) {
                }
            }
        }
    }

    /**
     * for test
     */
    public static void test3() {
        try {
            String source = "2,\"test2\", \"1234\", 1, \"2000-01-01\"";
            System.out.println("source: " + source);
            StringReader reader = new StringReader(source);
            List<String> headers = CSVReader.getHeaders(reader);
            System.out.println(headers);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param reader
     * @param out
     * @param pattern
     * @throws IOException
     */
    public static void export(Reader reader, PrintWriter out, String pattern) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(reader);
        List<String> headers = getHeaders(bufferedReader);
        out.println(headers);

        while(true) {
            List<String> data = next(bufferedReader);

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
     * @param reader
     * @return List<String>
     * @throws IOException
     */
    public static List<String> getHeaders(Reader reader) throws IOException {
        return next(reader);
    }

    /**
     * @param reader
     * @return DataSet
     * @throws IOException
     */
    public static List<String> next(Reader reader) throws IOException {
        String line = null;

        while((line = readLine(reader)) != null) {
            line = line.trim();

            if(line.length() < 1) {
                continue;
            }
            else {
                return parse(new StringStream(line));
            }
        }
        return null;
    }

    /**
     * @param reader
     * @return String
     * @throws IOException
     */
    public static String readLine(Reader reader) throws IOException {
        int c;
        StringBuilder buffer = new StringBuilder();

        while(true) {
            c = reader.read();

            if(c == -1) {
                if(buffer.length() > 0) {
                    return buffer.toString();
                }
                else {
                    return null;
                }
            }
            else if(c != '\n') {
                buffer.append((char)c);
            }
            else {
                break;
            }
        }
        return buffer.toString();
    }

    /**
     * @param stream
     * @return List<String>
     */
    public static List<String> parse(StringStream stream) {
        return parse(stream, new ArrayList<String>());
    }

    /**
     * @param stream
     * @param list
     * @return List<String>
     */
    public static List<String> parse(StringStream stream, List<String> list) {
        String token = null;

        while((token = getToken(stream)) != null) {
            if(token.equalsIgnoreCase("null")) {
                list.add(null);
            }
            else {
                list.add(token);
            }
        }
        return list;
    }

    /**
     * @param stream
     * @return String
     */
    public static String getToken(StringStream stream) {
        stream.skipWhitespace();
        char quote = ' ';
        int i = stream.read();
        StringBuilder buffer = new StringBuilder();

        if(i == -1) {
            return null;
        }
        else if(i == '\'' || i == '\"') {
            quote = (char)i;
        }
        else {
            stream.back();
        }

        while((i = stream.read()) != -1) {
            if(i == '\\') {
                escape(stream, buffer);
            }
            else if(i == quote) {
                i = stream.read();
                break;
            }
            else if(i == ',') {
                break;
            }
            else {
                buffer.append((char)i);
            }
        }
        stream.skipWhitespace();
        return buffer.toString();
    }

    /**
     * @param stream
     * @param buffer
     */
    private static void escape(StringStream stream, StringBuilder buffer) {
        char c = (char)(stream.read());

        if(c != -1) {
            switch(c) {
                case 'n': {
                    buffer.append('\n');
                    break;
                }
                case 't': {
                    buffer.append('\t');
                    break;
                }
                case 'b': {
                    buffer.append('\b');
                    break;
                }
                case 'r': {
                    buffer.append('\r');
                    break;
                }
                case 'f': {
                    buffer.append('\f');
                    break;
                }
                case '\'': {
                    buffer.append('\'');
                    break;
                }
                case '\"': {
                    buffer.append('\"');
                    break;
                }
                case '\\': {
                    buffer.append('\\');
                    break;
                }
                case 'u': {
                    char[] cbuf = new char[4];
                    int i = stream.read(cbuf);

                    if(i == 4) {
                        String hex = new String(cbuf);

                        try {
                            Integer value = Integer.parseInt(hex, 16);
                            buffer.append((char)(value.intValue()));
                        }
                        catch(NumberFormatException e) {
                        }
                    }

                    break;
                }
                default: {
                    stream.back();

                    char[] cbuf = new char[3];
                    int i = stream.read(cbuf);

                    if(i == 3) {
                        String oct = new String(cbuf);

                        try {
                            Integer value = Integer.parseInt(oct, 8);
                            buffer.append((char)(value.intValue()));
                        }
                        catch(NumberFormatException e) {
                        }
                    }

                    break;
                }
            }
        }
    }
}
