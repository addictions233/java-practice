package com.one.system;

import java.util.Arrays;

public class SystemDemo1 {
    public static void main(String[] args) {
        long millis1 = System.currentTimeMillis();
        int[] arr1 = {11,22,33,44,55,66,77};
        int[] arr2 = new int[arr1.length];
        System.out.println(Arrays.toString(arr1));
        System.arraycopy(arr1,0,arr2,0,6);
        System.out.println(Arrays.toString(arr2));
        long millis2 = System.currentTimeMillis();
        System.out.println(millis2-millis1);
    }
}
