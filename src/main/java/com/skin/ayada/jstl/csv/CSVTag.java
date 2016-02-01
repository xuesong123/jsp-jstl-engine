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
package com.skin.ayada.jstl.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import com.skin.ayada.csv.CSVReader;
import com.skin.ayada.csv.DataSet;
import com.skin.ayada.tagext.IterationTag;
import com.skin.ayada.tagext.TagSupport;
import com.skin.ayada.tagext.TryCatchFinally;

/**
 * <p>Title: CSVReader</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class CSVTag extends TagSupport implements IterationTag, TryCatchFinally {
    private String file;
    private String encoding;
    private String content;
    private Reader reader;
    private String var;
    private List<String> headers;

    @Override
    public int doStartTag() throws Exception {
        if(this.file != null) {
            this.reader = getReader(new File(this.file), this.encoding);
        }
        else {
            this.reader = new StringReader(this.content);
        }

        this.headers = CSVReader.getHeaders(this.reader);
        List<String> data = CSVReader.next(this.reader);

        if(this.var != null) {
            this.pageContext.setAttribute(this.var, new DataSet(this.headers, data));
        }
        return EVAL_PAGE;
    }

    @Override
    public int doEndTag() throws Exception {
        return EVAL_PAGE;
    }

    @Override
    public int doAfterBody() throws Exception {
        List<String> data = CSVReader.next(this.reader);

        if(data != null) {
            if(this.var != null) {
                this.pageContext.setAttribute(this.var, new DataSet(this.headers, data));
            }
            return EVAL_BODY_AGAIN;
        }
        else {
            return EVAL_PAGE;
        }
    }

    @Override
    public void doCatch(Throwable throwable) throws Throwable {
        throw throwable;
    }

    @Override
    public void doFinally() {
        if(this.reader != null) {
            try {
                this.reader.close();
            }
            catch(IOException e) {
            }
        }
    }

    /**
     * @param file
     * @param encoding
     * @return Reader
     * @throws IOException
     */
    protected static Reader getReader(File file, String encoding) throws IOException {
        Reader reader = null;
        
        if(encoding == null) {
            reader = new InputStreamReader(new FileInputStream(file));
        }
        else {
            reader = new InputStreamReader(new FileInputStream(file), encoding);
        }
        return new BufferedReader(reader);
    }

    /**
     * @param file the file to set
     */
    public void setFile(String file) {
        this.file = file;
    }

    /**
     * @return the file
     */
    public String getFile() {
        return this.file;
    }

    /**
     * @param encoding the encoding to set
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * @return the encoding
     */
    public String getEncoding() {
        return this.encoding;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return this.content;
    }

    /**
     * @param var the var to set
     */
    public void setVar(String var) {
        this.var = var;
    }

    /**
     * @return the var
     */
    public String getVar() {
        return this.var;
    }
}
