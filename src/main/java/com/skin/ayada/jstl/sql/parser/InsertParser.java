/*
 * $RCSfile: InsertParser.java,v $
 * $Revision: 1.1 $
 * $Date: 2014-03-25 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.jstl.sql.parser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.skin.ayada.io.StringStream;
import com.skin.ayada.jstl.sql.Record;

/**
 * <p>Title: InsertParser</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * @author xuesong.net
 * @version 1.0
 */
public class InsertParser {
    /**
     * @param sql
     * @return List<Record>
     * @throws IOException
     */
    public List<Record> parse(String sql) throws IOException {
        StringStream stream = new StringStream(sql);
        List<Record> resultSet = new ArrayList<Record>();

        while(true) {
            List<Record> recordList = this.read(stream);

            if(recordList == null || recordList.isEmpty()) {
                break;
            }
            resultSet.addAll(recordList);
        }
        return resultSet;
    }

    /**
     * @param stream
     * @return Record
     */
    public List<Record> read(StringStream stream) {
        String token = null;

        /**
         * read insert
         */
        SqlParser.skipComment(stream);

        if(stream.eof()) {
            return null;
        }

        token = SqlParser.getToken(stream);

        if(!token.equalsIgnoreCase("insert")) {
            return null;
        }

        /**
         * read into
         */
        SqlParser.skipComment(stream);
        token = SqlParser.getToken(stream);

        if(!token.equalsIgnoreCase("into")) {
            return null;
        }

        SqlParser.skipComment(stream);
        stream.tryread("`", true);
        String tableName = SqlParser.getToken(stream);
        stream.tryread("`", true);
        SqlParser.skipComment(stream);

        if(stream.read() != '(') {
            throw new RuntimeException("expect '(', but found: " + stream.getRemain(30));
        }

        String columnName = null;
        List<String> columns = new ArrayList<String>();
        List<Record> resultSet = new ArrayList<Record>();

        /**
         * read columns
         */
        while(true) {
            SqlParser.skipComment(stream);

            if(stream.peek() == ',') {
                stream.read();
                SqlParser.skipComment(stream);
            }

            if(stream.peek() == ')') {
                break;
            }

            columnName = SqlParser.getWord(stream);
            columns.add(columnName);

            if(columnName.length() < 1) {
                break;
            }
        }

        SqlParser.skipComment(stream);

        if(stream.read() != ')') {
            throw new RuntimeException("expect ')'!");
        }

        SqlParser.skipComment(stream);
        token = SqlParser.getToken(stream);

        if(!token.equalsIgnoreCase("values")) {
            throw new RuntimeException("expect keyword 'values'!");
        }

        while(true) {
            List<Object> values = this.readValues(stream);

            if(columns.size() == values.size()) {
                Record record = new Record();
                record.setTableName(tableName);

                for(int i = 0; i < columns.size(); i++) {
                    record.addColumn(columns.get(i), values.get(i));
                }
                resultSet.add(record);
            }
            else {
                throw new RuntimeException("column not match: columns.size: " + columns.size() + ", values.size: " + values.size());
            }

            int end = stream.peek();

            if(end == ',') {
                stream.read();
                continue;
            }
            else {
                if(end == ';') {
                    stream.read();
                }
                break;
            }
        }
        return resultSet;
    }
    
    /**
     * @param stream
     * @return List<Object>
     */
    public List<Object> readValues(StringStream stream) {
        List<Object> values = new ArrayList<Object>();
        SqlParser.skipComment(stream);

        if(stream.read() != '(') {
            throw new RuntimeException("expect '('!");
        }

        Object columnValue = null;

        while(true) {
            SqlParser.skipComment(stream);

            if(stream.peek() == ',') {
                stream.read();
                SqlParser.skipComment(stream);
            }

            if(stream.peek() == ')') {
                break;
            }
            columnValue = this.getColumnValue(stream);
            values.add(columnValue);
        }

        SqlParser.skipComment(stream);
        if(stream.read() != ')') {
            throw new RuntimeException("expect ')' !");
        }
        SqlParser.skipComment(stream);
        return values;
    }

    /**
     * @param stream
     * @param list
     * @return List<String>
     */
    public List<String> parse(StringStream stream, List<String> list) {
        char c;
        int i = 0;
        StringBuilder buffer = new StringBuilder();
        String value = null;

        while(!stream.eof()) {
            c = (char)i;

            if(c == '"') {
                while(!stream.eof()) {
                    c = (char)i;

                    if(c == '\\') {
                        SqlParser.escape(stream, buffer);
                    }
                    else if(c == '"') {
                        i = stream.read();

                        if(i != StringStream.EOF && i != ',') {
                            throw new RuntimeException("Bad format !");
                        }

                        break;
                    }
                    else {
                        buffer.append(c);
                    }
                }
                list.add(buffer.toString());
                buffer.setLength(0);
            }
            else {
                buffer.append(c);

                while(!stream.eof()) {
                    c = (char)i;

                    if(c != ',') {
                        buffer.append(c);
                    }
                    else {
                        break;
                    }
                }

                value = buffer.toString().trim();

                if(value.equals("NULL")) {
                    list.add(null);
                }
                else {
                    list.add(value);
                }
                buffer.setLength(0);
            }
        }
        return list;
    }

    /**
     * @param stream
     * @return Object
     */
    public Object getColumnValue(StringStream stream) {
        SqlParser.skipComment(stream);

        char token;
        StringBuilder buffer = new StringBuilder();
        int c = stream.read();

        if(c == '\'') {
            token = (char)c;
        }
        else {
            token = ' ';
            buffer.append((char)c);
        }

        while((c = stream.read()) != -1) {
            if(c == '\\') {
                SqlParser.unescape(stream, buffer);
            }
            else {
                if(token == ' ') {
                    if(c == token || c == ',' || c == '(' || c == ')' || Character.isISOControl(c)) {
                        if(c == '(' || c == ')') {
                            stream.back();
                        }
                        break;
                    }
                    else {
                        buffer.append((char)c);
                    }
                }
                else {
                    if(c != token) {
                        buffer.append((char)c);
                    }
                    else {
                        break;
                    }
                }
            }
        }

        if(token == '\'') {
            return buffer.toString();
        }
        else {
            String value = buffer.toString();

            if(value.equalsIgnoreCase("null")) {
                return null;
            }
            return this.getValue(value);
        }
    }

    /**
     * @param source
     * @return Object
     */
    public Object getValue(String source) {
        String temp = source.trim();
        Object value = source;

        if(temp.length() < 1) {
            return value;
        }

        int type = getDataType(source);

        switch(type) {
            case 0: {
                break;
            }
            case 1: {
                try {
                    value = Boolean.parseBoolean(temp);
                }
                catch(NumberFormatException e) {
                }

                break;
            }
            case 2: {
                try {
                    if(temp.charAt(0) == '+') {
                        value = Integer.parseInt(temp.substring(1));
                    }
                    else {
                        value = Integer.parseInt(temp);
                    }
                }
                catch(NumberFormatException e) {
                }
                break;
            }
            case 3: {
                try {
                    value = Float.parseFloat(temp);
                }
                catch(NumberFormatException e) {
                }
                break;
            }
            case 4: {
                try {
                    value = Double.parseDouble(temp);
                }
                catch(NumberFormatException e) {
                }
                break;
            }
            case 5: {
                try {
                    if(temp.endsWith("l") || temp.endsWith("L")) {
                        value = Long.parseLong(temp.substring(0, temp.length() - 1));
                    }
                    else {
                        value = Long.parseLong(temp);
                    }
                }
                catch(NumberFormatException e) {
                }
                break;
            }
            default: {
                break;
            }
        }
        return value;
    }

    /**
     * 0 - String
     * 1 - Boolean
     * 2 - Integer
     * 3 - Float
     * 4 - Double
     * 5 - Long
     * @param content
     * @return int
     */
    public int getDataType(String content) {
        String source = content.trim();

        if(source.length() < 1) {
            return 0;
        }

        if(source.equals("true") || source.equals("false")) {
            return 1;
        }

        char c;
        int d = 0;
        int type = 2;
        int length = source.length();

        for(int i = 0; i < length; i++) {
            c = source.charAt(i);

            if(i == 0 && (c == '+' || c == '-')) {
                continue;
            }

            if(c == '.') {
                if(d == 0) {
                    d = 4;
                    continue;
                }
                return 0;
            }

            if(c < 48 || c > 57) {
                if(i == length - 1) {
                    if(c == 'f' || c == 'F') {
                        return 3;
                    }
                    else if(c == 'd' || c == 'D') {
                        return 4;
                    }
                    else if(c == 'l' || c == 'L') {
                        return (d == 0 ? 5 : 0);
                    }
                    else {
                        return 0;
                    }
                }

                if(i == length - 2 && (c == 'e' || c == 'E') && Character.isDigit(source.charAt(length - 1))) {
                    return 4;
                }
                return 0;
            }
        }
        return (d == 0 ? type : d);
    }

    /**
     * @param resultSet
     * @param pattern
     */
    public void print(List<Record> resultSet, String pattern) {
        for(Record record : resultSet) {
            System.out.println(this.replace(pattern, record));
        }
    }

    /**
     * @param source
     * @param record
     * @return String
     */
    public String replace(String source, Record record) {
        char c;
        StringBuilder name = new StringBuilder();
        StringBuilder result = new StringBuilder(4096);

        for(int i = 0; i < source.length(); i++) {
            c = source.charAt(i);

            if(c == '$' && i < source.length() - 1 && source.charAt(i + 1) == '{') {
                for(int j = i + 2; j < source.length(); j++) {
                    i = j;
                    c = source.charAt(j);

                    if(c == '}') {
                        Object value = record.getColumnValue(name.toString());

                        if(value instanceof String) {
                            result.append("'");
                            result.append(record.escape((String)value));
                            result.append("'");
                        }
                        else {
                            result.append(value);
                        }
                        break;
                    }
                    else {
                        name.append(c);
                    }
                }
                name.setLength(0);
            }
            else {
                result.append(c);
            }
        }
        return result.toString();
    }

    /**
     * @param file
     * @param resultSet
     */
    public static void write(File file, List<Record> resultSet) {
        OutputStream outputStream = null;

        try {
            byte[] CRLF = "\r\n".getBytes();
            outputStream = new FileOutputStream(file);

            for(Record record : resultSet) {
                outputStream.write(record.toString().getBytes());
                outputStream.write(CRLF);
            }
            outputStream.flush();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        finally {
            if(outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
