package com.corejava.method;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: VariableTest
 * @Description: 测试方法中的值传递和引入传递
 * @Author: one
 * @Date: 2022/03/08
 */
public class VariableTest {
    public static void main(String[] args) {
        // 基本数据类型的包装类虽然传递的是地址值(引用传递), 但是基本数据类型的包装类和String类型, final修饰的不可变类一样
        // 不能直接修改属性值,要想重新赋值,就得在堆内存中开辟一个新的内存空间,并赋上新的值
        Integer i1 = 10000;  // i1变量地址值: Integer@797
        changeVariable(i1);
        // 输出还是10000
        System.out.println(i1);

        List<Integer> list = new ArrayList<>();
        addElement(list);
        System.out.println(list);

        String str = "a";
        concatString(str);
        // 输出还是 a
        System.out.println(str);

        StringBuffer sb = new StringBuffer("x");
        appendString(sb);
        // 输出: xy
        System.out.println(sb);

    }

    private static void appendString(StringBuffer sb) {
        sb.append("y");
    }

    private static void concatString(String str) {
        str.concat("b");
    }

    private static void addElement(List<Integer> list) {
        // 而 list变量的地址值一直是 ArrayList@798
        list.add(1);
    }

    private static void changeVariable(Integer i) { // 此时i传入的地址值: Integer@797
        // 运行此赋值语句是 i的地址值变成了: Interger@798
        i += 1;
    }
}
