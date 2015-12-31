/*
 * $RCSfile: Test2.java,v $$
 * $Revision: 1.1  $
 * $Date: 2014-8-17  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.html;

import java.io.IOException;

/**
 * <p>Title: Test2</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class Test2 {
    public static void main(String[] args) throws Exception {
        /*
        int num = 3;
        int[][] rects = new int[][]{
            {1, 3, 2},
            {2, 4, 4},
            {6, 7, 5}
        };
        */
        test1();
    }

    public static void test1() throws IOException {
        int num = 0;
        java.io.InputStreamReader inputStreamReader = new java.io.InputStreamReader(System.in);
        java.io.BufferedReader buffer = new java.io.BufferedReader(inputStreamReader);

        while(num < 1) {
            try {
                System.out.println("Please enter a num: ");
                String line = buffer.readLine();
                num = Integer.parseInt(line);
            }
            catch(NumberFormatException e) {
                e.printStackTrace();
            }
        }

        int[][] rects = new int[num][3];

        for(int i = 0; i < num;) {
            System.out.println("Please enter rectangle[" + i + "]'s index: ");
            String line = buffer.readLine();
            String[] array = line.split(",");

            if(array.length == 3) {
                int[] p = new int[3];

                for(int j = 0; j < 3; j++) {
                    p[j] = Integer.parseInt(array[j]);
                }

                rects[i] = p;
                i++;
            }
        }

        int maxX = 0;
        int distance = 0;

        for(int i = 0; i < num; i++) {
            int[] a = rects[i];
            distance += (a[2] + a[2]);

            if(maxX < a[1]) {
                maxX = a[1];
            }
        }

        distance = distance + maxX;

        for(int i = 0; i < num; i++) {
            int[] a = rects[i];

            for(int j = 0; j < num; j++) {
                if(i != j) {
                    int[] b = rects[j];

                    if(a[0] >= b[0] && a[0] <= b[1]) {
                        distance = distance - (2 * Math.min(a[2], b[2]));
                    }
                }
            }
        }

        System.out.println(distance);
    }
}
