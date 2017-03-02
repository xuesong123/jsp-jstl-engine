/*
 * $RCSfile: PerformanceTest.java,v $
 * $Revision: 1.1 $
 * $Date: 2013-11-08 $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package com.skin.ayada.test;

import java.io.File;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.skin.ayada.test.model.Book;
import com.skin.ayada.test.model.User;

/**
 * <p>Title: BenchmarkTest</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class BenchmarkTest {
    private String engines;

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        int warm = 100;
        int count = 20000;
        int dataSize = 100;
        BenchmarkTest benchmarkTest = new BenchmarkTest();
        benchmarkTest.setEngines("velocity,freemarker,ayada1,ayada2");
        benchmarkTest.setEngines("velocity,freemarker,ayada2");
        benchmarkTest.setEngines("httl,velocity,freemarker,ayada1,ayada2");
        benchmarkTest.setEngines("velocity,freemarker,ayada1,ayada2");
        benchmarkTest.setEngines("java,httl,velocity,freemarker,ayada1,ayada2");
        benchmarkTest.setEngines("velocity,ayada3,ayada2,freemarker,ayada1");

        try {
            benchmarkTest.test("webapp/benchmark/bookList", warm, count, dataSize);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param file
     * @param warm
     * @param count
     * @param dataSize
     * @throws Exception
     */
    public void test(String file, int warm, int count, int dataSize) throws Exception {
        String[] names = this.engines.split(",");
        int nameMaxLength = this.getMaxLength(names, 10);
        Benchmark[] cases = this.getTestCaseList(names);

        Runtime runtime = Runtime.getRuntime();
        System.out.println("==================== test environment =====================");
        System.out.print(" OS: " + System.getProperty("os.name") + System.getProperty("os.version") + " " + System.getProperty("os.arch"));
        System.out.print(", CPU: " + Runtime.getRuntime().availableProcessors() + " cores");
        System.out.println(", JVM: " + System.getProperty("java.version"));
        System.out.print("MEM: max: " + (runtime.maxMemory()   / 1024 / 1024) + "M");
        System.out.print(", total: " + (runtime.totalMemory() / 1024 / 1024)  + "M");
        System.out.print(", free: " + (runtime.freeMemory()  / 1024 / 1024) + "M");
        System.out.print(", use: " + (runtime.totalMemory() / 1024 / 1024 - runtime.freeMemory() / 1024 / 1024) + "M");
        System.out.println("\r\n");

        System.out.println("==================== test parameters ======================");
        System.out.println("engines: " + this.engines);
        System.out.println("   warm: " + warm + ", count: " + count + ", data-size: " + dataSize);
        System.out.println();

        String fileName = this.getFileName(file);
        String work = new File(file).getParent();
        Book[] books = this.getBookList(dataSize);
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("user", new User(1L, "admin", 18));
        context.put("books", books);

        for(int i = 0; i < cases.length; i ++) {
            cases[i].init(work);
            cases[i].execute(fileName, context, new StringWriter(), warm);
        }

        if(cases.length > 0) {
            cases[0].execute(fileName, context, new StringWriter(), count);
        }

        long base = 0;

        System.out.println("==================== test result ==========================");
        System.out.println(padding("engine", nameMaxLength)
                + "," + padding("time", 12)
                + "," + padding("tps", 12)
                + "," + padding("rate", 12));

        for(int i = 0; i < cases.length; i ++) {
            String name = names[i];
            Benchmark benchmark = cases[i];

            long elapsed = this.execute(benchmark, fileName, context, new StringWriter(), count);
            long tps = this.getTPS(elapsed, count);

            if(i == 0) {
                base = tps;
            }

            long ratio = (base == 0 || tps == 0) ? 0 : 100 * tps / base;
            System.out.println(padding(name.toLowerCase(), nameMaxLength)
                    + "," + padding(elapsed + "ms", 12)
                    + "," + padding(tps + "/s", 12)
                    + "," + padding(ratio + "%", 12));
        }
        System.out.println("===========================================================");
    }

    /**
     * @param benchmark
     * @param name
     * @param context
     * @param writer
     * @param count
     * @return long
     * @throws Exception 
     */
    public long execute(Benchmark benchmark, String name, Map<String, Object> context, StringWriter writer, int count) throws Exception {
        long start = System.currentTimeMillis();
        benchmark.execute(name, context, writer, count);
        return System.currentTimeMillis() - start;
    }

    /**
     * @param timeMillis
     * @param count
     * @return long
     */
    private long getTPS(long timeMillis, int count) {
        return (timeMillis == 0 || count == 0) ? 0 : (1000L * count / timeMillis);
    }

    /**
     * @return the engines
     */
    public String getEngines() {
        return this.engines;
    }

    /**
     * @param engines the engines to set
     */
    public void setEngines(String engines) {
        this.engines = engines;
    }

    /**
     * @param names
     * @return int
     */
    private int getMaxLength(String[] names, int length) {
        int max = length;

        for(String name : names) {
            max = Math.max(max, name.trim().length());
        }
        return max;
    }

    /**
     * @param path
     * @return String
     */
    private String getFileName(String path) {
        int k = path.lastIndexOf("/");
        
        if(k > -1) {
            return path.substring(k + 1);
        }
        return path;
    }

    /**
     * @param engines
     * @return Benchmark[]
     * @throws Exception 
     */
    private Benchmark[] getTestCaseList(String[] names) throws Exception {
        Benchmark[] cases = new Benchmark[names.length];

        for(int i = 0; i < names.length; i ++) {
            String name = names[i];
            cases[i] = this.getInstance(name.trim());
        }
        return cases;
    }

    /**
     * @param name
     * @return Benchmark
     * @throws Exception
     */
    private Benchmark getInstance(String name) throws Exception {
        String packageName = this.getPackageName(this);
        String className = packageName + ".engine." + Character.toUpperCase(name.charAt(0)) + name.substring(1) + "Test";
        Class<?> type = Class.forName(className);
        return (Benchmark)(type.newInstance());
    }

    /**
     * @param object
     * @return String
     */
    private String getPackageName(Object object) {
        String className = object.getClass().getName();
        int k = className.lastIndexOf('.');

        if(k > -1) {
            return className.substring(0, k);
        }
        return "";
    } 

    /**
     * @param size
     * @return Book[]
     */
    private Book[] getBookList(int size) {
        Date sysTime = parse("2017-02-12 08:00:00", "yyyy-MM-dd HH:mm:ss");
        Book[] books = new Book[size];

        for(int i = 0; i < size; i ++) {
            Book book = new Book();
            book.setTitle("title----------------------" + i);
            book.setAuthor("author----------------------" + i);
            book.setPublisher("putlisher----------------------" + i);
            book.setPublication(sysTime);
            book.setPrice(90);
            book.setDiscount(80);
            books[i] = book;
        }
        return books;
    }

    /**
     * @param date
     * @param pattern
     * @return Date
     */
    private Date parse(String date, String pattern) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(pattern);
            return dateFormat.parse(date);
        }
        catch (ParseException e) {
        }
        return null;
    }

    /**
     * @param value
     * @param length
     * @return String
     */
    protected static String padding(long value, int length) {
        return padding(Long.toString(value), length);
    }

    /**
     * @param str
     * @param len
     * @return 
     */
    protected static String padding(String text, int length) {
        if(text.length() < length) {
            StringBuilder buffer = new StringBuilder(length);

            for(int i = length - text.length(); i > 0; i --) {
                buffer.append(' ');
            }
            buffer.append(text);
            return buffer.toString();
        }
        return text;
    }
}
