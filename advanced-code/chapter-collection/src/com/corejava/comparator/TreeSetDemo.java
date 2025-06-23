package com.corejava.comparator;

import java.util.TreeSet;

public class TreeSetDemo {
    public static void main(String[] args) {
        TreeSet<Integer> set = new TreeSet<>((i1,i2)->i1-i2);
        set.add(22);
        set.add(55);
        set.add(22);
        set.add(11);
        set.add(44);
        set.add(33);
        System.out.println(set);

        TreeSet<String> set1 = new TreeSet<>((str1, str2) -> Integer.parseInt(str1) - Integer.parseInt(str2));
        set1.add("11");
        set1.add("55");
        set1.add("1000");
        set1.add("100");
        set1.add("99");
        System.out.println(set1);
    }

}
