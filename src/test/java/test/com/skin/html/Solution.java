/*
 * $RCSfile: Solution.java,v $$
 * $Revision: 1.1  $
 * $Date: 2014-8-17  $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package test.com.skin.html;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * <p>Title: Solution</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * @author xuesong.net
 * @version 1.0
 */
public class Solution {
    public static void main(String[] args) {
        int[] a = new int[]{
            1,  2,  3,  4,  5,
            6,  7,  8,  9,  10,
            11, 12, 13, 14, 15
        };

        int[] result = convertMatrix(5, 3, a);

        for(int i = 0; i < result.length; i++) {
            System.out.print(result[i] + ", ");
        }
        System.out.println();
    }

    @SuppressWarnings("resource")
    public static void main1(String[] args) {
        Scanner in = new Scanner(System.in);

        int[] res;
        int _width = in.nextInt();
        int _height = in.nextInt();

        int[] _matrix = new int[_width * _height];
        int _matrix_item;
        for(int _matrix_i = 0; _matrix_i < _width * _height; _matrix_i++) {
            _matrix_item = in.nextInt();
            _matrix[_matrix_i] = _matrix_item;
        }

        res = convertMatrix(_width, _height, _matrix);

        for(int res_i = 0; res_i < res.length; res_i++) {
            System.out.print(res[res_i] + ", ");
        }
        System.out.println();
    }

    /**
     *  1  2  3  4  5
     *  6  7  8  9  10
     *  11 12 13 14 15
     *  1  2  3  4  5 9 13 12 11 6 7 8
     *
     * while(){ right -> left down - left -> up}
     *
     * @param width
     * @param height
     * @param matrix
     * @return int[]
     */
    public static int[] convertMatrix(int width, int height, int[] matrix) {
        boolean flag = true;
        int[] temp = new int[width * height];
        List<Integer> result = new ArrayList<Integer>();

        int x = -1;
        int y = 0;
        int index = 0;

        while(flag) {
            // right
            while(true) {
                x++;
                if(x >= width) {
                    x--;
                    break;
                }

                index = y * width + x;

                if(temp[index] == 1) {
                    x--;
                    flag = false;
                    break;
                }

                result.add(matrix[index]);
                temp[index] = 1;
            }

            // left down
            while(true) {
                x--;
                y++;
                if(x < 0 || y >= height) {
                    x++;
                    y--;
                    break;
                }

                index = y * width + x;

                if(temp[index] == 1) {
                    x++;
                    y--;
                    flag = false;
                    break;
                }

                result.add(matrix[index]);
                temp[index] = 1;
            }

            // left
            while(true) {
                x--;
                if(x < 0) {
                    x++;
                    break;
                }

                index = y * width + x;

                if(temp[index] == 1) {
                    x++;
                    flag = false;
                    break;
                }

                result.add(matrix[index]);
                temp[index] = 1;
            }

            // up
            while(true) {
                y--;
                if(y < 0) {
                    y++;
                    break;
                }

                index = y * width + x;

                if(temp[index] == 1) {
                    y++;
                    break;
                }

                result.add(matrix[index]);
                temp[index] = 1;
            }
        }

        int[] a = new int[result.size()];

        for(int i = 0; i < a.length; i++) {
            a[i] = result.get(i).intValue();
        }
        return a;
    }
}
