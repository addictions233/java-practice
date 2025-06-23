package com.corejava.generictype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArrayListDemo4 {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        Collections.addAll(list, 11, 22, 33, 44);
        for (Integer integer : list) {
            System.out.println(integer);
        }

        List<String> list1 = List.of("zhangsan", "lisi", "wangwu");
        System.out.println(list1);

    }
}
