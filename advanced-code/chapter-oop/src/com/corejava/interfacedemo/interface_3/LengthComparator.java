package com.corejava.interfacedemo.interface_3;

import java.util.Comparator;

/**
 *  两者的区别: 实现两个接口的类不同,导致的compareTo方法的调用方法不同
 *  可比较接口  Comparable
 *  interface Comparable<T>{
 *      int CompareTo(T other);
 *  }
 *
 *
 *  比较器接口 Comparator
 *      interface Comparator<T>{
 *          int CompareTo(T first , T second);
 *      }
 */
public class LengthComparator implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        return o1.length()-o2.length();
    }
}


