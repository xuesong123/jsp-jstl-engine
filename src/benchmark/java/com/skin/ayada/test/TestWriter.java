package com.skin.ayada.test;

import java.io.IOException;
import java.io.Writer;

public class TestWriter extends Writer {
    StringBuilder buffer;

    public TestWriter() {
        this(4096);
    }

    public TestWriter(int initialSize) {
        if (initialSize < 0) {
            throw new IllegalArgumentException("Negative buffer size");
        }
        this.buffer = new StringBuilder(initialSize);
    }

    public void write(int c) {
        this.buffer.append((char) c);
    }

    public void write(char cbuf[], int off, int len) {
        if ((off < 0) || (off > cbuf.length) || (len < 0) || ((off + len) > cbuf.length) || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return;
        }
        this.buffer.append(cbuf, off, len);
    }

    public void write(String str) {
        this.buffer.append(str);
    }

    public void write(String str, int off, int len) {
        this.buffer.append(str.substring(off, off + len));
    }

    public TestWriter append(CharSequence csq) {
        if (csq == null) {
            write("null");
        } else {
            write(csq.toString());
        }
        return this;
    }

    public TestWriter append(CharSequence csq, int start, int end) {
        CharSequence cs = (csq == null ? "null" : csq);
        write(cs.subSequence(start, end).toString());
        return this;
    }

    public TestWriter append(char c) {
        write(c);
        return this;
    }

    public String toString() {
        return this.buffer.toString();
    }

    public StringBuilder getBuffer() {
        return this.buffer;
    }

    public void flush() {

    }

    public void close() throws IOException {
    }
}
