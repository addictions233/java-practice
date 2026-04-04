package com.one.lambda;

import com.one.functioninterface.Person;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 方法引用必须依赖于 函数式接口（Functional Interface），即只有一个抽象方法的接口
 * Lambda 表达式和方法引用本质上是函数式接口实例的简写形式。
 */
public class Lambda01 {

    public static void main(String[] args) {
        Person wangwu = new Person("王五", 25);
        Person lisi = new Person("李四", 24);
        Person zhansan = new Person("张三", 23);

        List<Person> list = Arrays.asList(zhansan, lisi, wangwu);

        // 现在对list集合进行排序: java中最传统的写法, 需要构建一个Comparator对象
        list.sort(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.compareByAge(o1);
            }
        });
        System.out.println(list);

        // 使用lambda进行改造: 由于Comparator是函数式接口, 所以我们可以用lambda表达式构造 这个接口
        // 用方法 来替代 函数式接口的对象本身, 函数式编程: 函数替代对象
        list.sort((p1, p2) -> {
            return p2.compareByAge(p1);
        });
        System.out.println(list);

        // 使用方法引用进行改造: 使用实例方法引用Instance Method References
        // 由于java.util.Comparator#compare(T o1, T o2) 的方法签名是需要两个同类型的参数, 并返回一个int类型
        // 而com.one.functioninterface.Person#compareByAge(Person p) 的方法签名也是两个同类型的参数并返回一个 int类型
        // 两者方法签名相同, 所以可以进行方法引用
        // lambada和方法引用均需要与接口的抽象方法签名匹配（如 FunctionalInterface）
        list.sort(Person::compareByAge);
//        list.sort(Person::getAge);  // 这样写就会编译报错, 因为两个方法签名不一致, 无法进行方法引用
        System.out.println(list);
    }
}
