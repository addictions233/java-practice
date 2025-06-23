package com.one.algorithm;

import java.util.Arrays;

/**
 *  冒泡排序
 *      n个元素,
 *          第一轮排n个元素,共比较 n-1次
 *
 */
public class BubbleSort {
    public static void main(String[] args) {
        int[] arr = {11,33,55,44,99,22,77,88,66};
        System.out.println(Arrays.toString(bubbleSor(arr)));
    }
    public static int[] bubbleSor(int[] arr){
        // i 代表要排几轮,
        for (int i = 0; i < arr.length-1; i++) {
            for (int j = 1; j < arr.length-i; j++) {
                int temp;
                if(arr[j-1]>arr[j]){
                    temp = arr[j];
                    arr[j] = arr[j-1];
                    arr[j-1] = temp;
                }
            }
        }
        return arr;
    }
}
