package com.one.algorithm;

/**
 *  二分查找
 *      二分查找的前提是: 数组中元素已经按照升序或者降序排列
 */
public class BinarySearch {
    public static void main(String[] args) {
        int[] arr = {0,11,22,33,44,55,66,77,88,99};
        int index = binarySearch(arr,100);
        System.out.println(index);

    }
    public static int binarySearch(int[] arr,int desNum){
        final int ELEMENT_NOT_FOUND = -1;
        int min = 0;
        int max = arr.length-1;
        while(min <= max){
            if(desNum > arr[(min+max)/2]){
                min = (min+max)/2+1;
            } else if ( desNum < arr[(min+max)/2]){
                max = (min+max)/2-1;
            } else{
                return ((min+max)/2);
            }
        }
        return ELEMENT_NOT_FOUND;
    }
}
