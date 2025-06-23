package com.corejava.generictype;

import java.util.Enumeration;
import java.util.Vector;

/**
 * @ClassName: VectorDemo
 * @Description: Enumeration 是JDK 1.0添加的接口。使用到它的函数包括Vector、Hashtable等类，
 * 这些类都是JDK 1.0中加入的，Enumeration存在的目的就是为它们提供遍历接口。
 * Enumeration本身并没有支持同步，而在Vector、Hashtable实现Enumeration时，添加了同步。
 * 而Iterator 是JDK 1.2才添加的接口，它也是为了HashMap、ArrayList等集合提供遍历接口。
 * Iterator是支持fail-fast机制的：当多个线程对同一个集合的内容进行操作时，就可能
 * 会产生fail-fast。
 * @Author: one
 * @Date: 2022/03/30
 */
public class VectorDemo {
    public static void main(String[] args) {
        Vector<String> vector = new Vector<>(4);
        vector.add("zhangsan");
        vector.add("lisi");
        vector.add("wangwu");
        // 使用Enumeration遍历vector集合
        Enumeration<String> elements = vector.elements();
        while (elements.hasMoreElements()) {
            String value = elements.nextElement();
            System.out.println(value);
        }
    }
}
