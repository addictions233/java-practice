package com.one.string;

import java.util.Arrays;

/**
 * @ClassName: KMPDemo
 * @Description: 求解kmp算法中的next数组
 * @Author: one
 * @Date: 2021/08/02
 */
public class KMPDemo {
    public static void main(String[] args) {
        String str = "abcdabmc";
        char[] chs = str.toCharArray();
        int[] next = new int[chs.length+1];
        next(chs,chs.length,next);
        System.out.println(Arrays.toString(next));
    }

    public static int[] next(char[] chs, int length, int[] next) {
        next[1] = 0;
        int i = 1,j = 0;
        while(i < length) {
            if( j == 0 || chs[i] == chs[j-1]) {
                next[++i] = ++j;
            } else {
                j = next[j];
            }
        }
        return next;
    }
}
