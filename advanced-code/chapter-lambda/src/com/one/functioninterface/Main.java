package com.one.functioninterface;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Person wangwu = new Person("王五", 25);
        Person lisi = new Person("李四", 24);
        Person zhansan = new Person("张三", 23);
        List<Person> lists = new ArrayList<>(List.of(zhansan, lisi, wangwu));
        // lambada表达式和方法引用 都是基于重写函数式接口中的抽象方法的 前提
        lists.sort((o1, o2) -> o1.compareByMy(o2, Person::compareByAge));
        System.out.println(lists);

    }
}
