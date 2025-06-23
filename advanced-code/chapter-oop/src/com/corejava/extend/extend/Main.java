package com.corejava.extend.extend;

/**
 * 组合复用原则: 多使用组合,少用继承, java中是单继承的,要珍惜这次继承的机会
 *      1, 继承一个类,可以使用父类的非私有成员
 *      2, 将父类作为一个成员变量,注入到本类中,不是也能使用父类中的非私有成员吗
 * @author one
 */
public class Main {
    /**
     * 将一个子类的引用赋给一个超类变量,是可以的. 但是反过来,必须进行类型转换.
     * 但是转换的时候,可能出现"谎报"的ClassCastException异常,
     * 在使用强制类型转换前, 我们可以先用instance运算符, 判断能否转换成功
     *
     * @param args args
     */
    public static void main(String[] args) {
//        Son son2 = (Son)new Father();
        Father father1 = new Father();
        if (father1 instanceof Son) { // 返回false 父类肯定不是子类的派生类
            Son son1 = (Son) father1;
            son1.show();
            System.out.println("执行了1");
        }
        Father father2 = new Son();
        if (father2 instanceof Son) {
            // 强制类型转换是为了获取多态中的原型对象
            Son son2 = (Son) father2;
            son2.show();
            System.out.println("执行了2");
        }

        // 子类是继承了父类的非私有成员,所有son对象也能调用父类中独有的count()方法
        father2.count();

    }
}

