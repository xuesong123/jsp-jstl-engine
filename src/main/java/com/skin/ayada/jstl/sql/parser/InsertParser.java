package com.skin.ayada.jstl.sql.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.skin.ayada.io.StringStream;
import com.skin.ayada.jstl.sql.Record;

/**
 * @author weixian
 * @version 1.0
 */
public class InsertParser {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String sql = read(new File("D:\\workspace\\jartest\\test1.sql"), "UTF-8");
			InsertParser parser = new InsertParser();
			List<Record> resultSet = parser.parse(sql.toString());

			for(Record record : resultSet) {
				System.out.println(record.toString());
			}
			// write(new File("test2.sql"), resultSet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void test() {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into a(`a1`, [a2], 'a3') values ('a', 1, 1)\r\n");
		sql.append("insert into a(`a1`, a2, a3) values ('b', 2, 2);\r\n");
		sql.append("insert into a(`a1`, a2, a3) values ('c', null, 3)");

		try {
			InsertParser parser = new InsertParser();
			List<Record> resultSet = parser.parse(sql.toString());

			for(Record record : resultSet) {
				System.out.println(record.toString());
			}
			parser.print(resultSet, "insert into mytable(`b1`, `b2`, `b3`) values (${a1}, ${a2}, ${a3});");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param sql
	 * @return List<Record>
	 * @throws IOException
	 */
	public List<Record> parse(String sql) throws IOException {
		StringStream stream = new StringStream(sql);
		List<Record> resultSet = new ArrayList<Record>();

		while(true) {
			Record record = this.read(stream);
			
			if(record == null) {
				break;
			}
			resultSet.add(record);
		}
		return resultSet;
	}

	/**
	 * @param stream
	 * @return Record
	 */
	public Record read(StringStream stream) {
		String token = null;

		/**
		 * read insert
		 */
		stream.skipWhitespace();

		if(stream.eof()) {
			return null;
		}

		token = this.getToken(stream);

		if(!token.equalsIgnoreCase("insert")) {
			return null;
		}

		/**
		 * read into
		 */
		stream.skipWhitespace();
		token = this.getToken(stream);

		if(!token.equalsIgnoreCase("into")) {
			return null;
		}

		stream.skipWhitespace();
		String tableName = this.getToken(stream);
		stream.skipWhitespace();

		if(stream.read() != '(') {
			throw new RuntimeException("expect '('!");
		}

		String columnName = null;
		Record record = new Record();
		record.setTableName(tableName);
		List<String> columns = new ArrayList<String>();
		List<Object> values = new ArrayList<Object>();

		/**
		 * read columns
		 */
		while(true) {
			stream.skipWhitespace();

			if(stream.peek() == ',') {
				stream.read();
				stream.skipWhitespace();
			}

			if(stream.peek() == ')') {
				break;
			}

			columnName = this.getWord(stream);
			columns.add(columnName);

			if(columnName.length() < 1) {
				break;
			}
		}

		stream.skipWhitespace();
		
		if(stream.read() != ')') {
			throw new RuntimeException("expect ')'!");
		}

		stream.skipWhitespace();
		token = this.getToken(stream);

		if(!token.equalsIgnoreCase("values")) {
			throw new RuntimeException("expect keyword 'values'!");
		}

		stream.skipWhitespace();
		if(stream.read() != '(') {
			throw new RuntimeException("expect '('!");
		}

		Object columnValue = null;

		while(true) {
			stream.skipWhitespace();

			if(stream.peek() == ',') {
				stream.read();
				stream.skipWhitespace();
			}

			if(stream.peek() == ')') {
				break;
			}
			columnValue = this.getColumnValue(stream);
			values.add(columnValue);
		}

		stream.skipWhitespace();
		if(stream.read() != ')') {
			throw new RuntimeException("expect ')' !");
		}

		stream.skipWhitespace();

		if(stream.peek() == ';') {
			stream.read();
		}

		if(columns.size() == values.size()) {
			for(int i = 0; i < columns.size(); i++) {
				record.addColumn(columns.get(i), values.get(i));
			}
		}
		else {
			throw new RuntimeException("column not match: columns.size: " + columns.size() + ", values.size: " + values.size());
		}
		return record;
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
                        this.escape(stream, buffer);
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
     * @return String
     */
    public String getToken(StringStream stream) {
        int c = 0;
        StringBuilder buffer = new StringBuilder();
		stream.skipWhitespace();

        while((c = stream.read()) != -1) {
            if(c <= ' ' || c == '(' || c == ')' || c == ',') {
                stream.back();
                break;
            }
            else {
                buffer.append((char)c);
            }
        }

        String token = buffer.toString();

        if(token.equals("--")) {
            while((c = stream.read()) != -1) {
                if(c == '\n') {
                    break;
                }
            }
            return this.getToken(stream);
        }
        return token;
    }

    /**
     * @param stream
     * @return String
     */
    public String getWord(StringStream stream) {
        int c = 0;
        char quoto = '\0';
        StringBuilder buffer = new StringBuilder();
        stream.skipWhitespace();
        c = stream.read();

        if(c == '`' || c == '\'' || c == '"' || c == '[') {
            quoto = (char)c;
            stream.skipWhitespace();
        }
        else {
            stream.back();
        }

        while((c = stream.read()) != -1) {
            if(this.isSqlIdentifierPart(c)) {
                buffer.append((char)c);
            }
            else {
                stream.back();
                break;
            }
        }

        String word = buffer.toString();
        stream.skipWhitespace();

        if(quoto != '\0') {
            c = stream.read();

            if(quoto != c && !(quoto == '[' && c == ']')) {
            	throw new RuntimeException("column '" + word + "', except '" + quoto + "': found '" + (char)c + "'");
            }
        }
        return word;
    }

    /**
     * @param stream
     * @return Object
     */
	public Object getColumnValue(StringStream stream) {
		stream.skipWhitespace();

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
            	this.unescape(stream, buffer);
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
     * @param stream
     * @param buffer
     */
    private void escape(StringStream stream, StringBuilder buffer) {
        char c = (char)(stream.read());

        if(c != StringStream.EOF) {
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

    /**
     * @param stream
     * @param buffer
     */
    private void unescape(StringStream stream, StringBuilder buffer) {
        int c = stream.read();

        if(c < 0) {
            return;
        }

        switch(c) {
            case 'n':{
                buffer.append("\n");
                break;
            }
            case 't': {
                buffer.append("\t");
                break;
            }
            case 'b': {
                buffer.append("\b");
                break;
            }
            case 'r': {
                buffer.append("\r");
                break;
            }
            case 'f': {
                buffer.append("\f");
                break;
            }
            case '\'': {
                buffer.append("\'");
                break;
            }
            case '\"': {
                buffer.append("\"");
                break;
            }
            case '\\': {
                buffer.append("\\");
                break;
            }
            case 'u': {
                char[] cbuf = new char[4];
                int length = stream.read(cbuf);

                if(length == 4) {
                    String hex = new String(cbuf);

                    try {
                    	int value = Integer.parseInt(hex, 16);
                    	buffer.append((char)value);
                    }
                    catch(NumberFormatException e) {
                    }
                }
                break;
            }
            default: {
                stream.back();
                char[] cbuf = new char[3];
                int length = stream.read(cbuf);

                if(length == 3) {
                    String hex = new String(cbuf);

                    try {
                    	int value = Integer.parseInt(hex, 8);
                    	buffer.append((char)value);
                    }
                    catch(NumberFormatException e) {
                    }
                }
                break;
            }
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

    public boolean isSqlIdentifierPart(int i) {
        if(i == '_') {
            return true;
        }
        return (i >= 48 && i <= 57) || (i >= 97 && i <= 122) || (i >= 65 && i <= 90);
    }

	/**
	 * @param resultSet
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
     * @param encoding
     * @return String
     * @throws IOException
     */
    public static String read(File file, String encoding) throws IOException {
    	InputStream inputStream = null;

    	try {
    		inputStream = new FileInputStream(file);

	    	if(encoding != null){
	        	return read(new InputStreamReader(inputStream, encoding));
	    	}
	    	else{
	        	return read(new InputStreamReader(inputStream));
	    	}
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
     * @param inputStream
     * @param encoding
     * @return String
     * @throws IOException
     */
    public static String read(InputStream inputStream, String encoding) throws IOException {
    	if(encoding != null){
        	return read(new InputStreamReader(inputStream, encoding));
    	}
    	else{
        	return read(new InputStreamReader(inputStream));
    	}
    }

    /**
     * @param reader
     * @return String
     * @throws IOException
     */
    public static String read(Reader reader) throws IOException {
    	int length = 0;
    	char[] cbuf = new char[4096];
    	StringBuilder buffer = new StringBuilder();

    	while((length = reader.read(cbuf)) > 0) {
    		buffer.append(cbuf, 0, length);
    	}
    	return buffer.toString();
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
