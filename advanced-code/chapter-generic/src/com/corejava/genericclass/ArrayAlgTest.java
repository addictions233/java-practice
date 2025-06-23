package com.corejava.genericclass;

/**
 *  测试 ArrayAlg中定义的方法
 */
public class ArrayAlgTest {
    public static void main(String[] args) {
        Integer[] array = {11,33,44,22,66,55};
        ArrayAlg.Pair<Integer> pair = ArrayAlg.minMax(array);
        System.out.println(pair.getFirst());  //输出: 11
        System.out.println(pair.getSecond()); //输出: 22


        String[] array2 = {"a","b","c","d"};
        ArrayAlg.Pair<String> pair2 = ArrayAlg.minMax(array2);
        System.out.println(pair2.getFirst()); //输出: a
        System.out.println(pair2.getSecond());// 输出: b

//        int[] arr = null;  // 数组为null
//        int[] arr2 = {};  // 空数组
     }
}
