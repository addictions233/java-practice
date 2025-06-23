package com.corejava.inheritance;

import java.time.LocalDate;

/**
 *  定义一个Manager类来继承Employee类
 */
public class Manager extends Employee{
    /**
     *  添加一个新的属性 bonus;
     */
    private double bonus;
    /**
     *  如果父类中没有空参构造,其子类就不能有空参构造
     *      原因: 子类要加载之前必须先加载父类, 子类的构造方法中一定会调用父类的构造方法
     *      如果在子类中不声明用super关键字调用父类构造方法,那么jvm会默认在子类构造方法中添加
     *      super()语句来调用父类空参构造,但是当父类没有空参构造,子类的构造方法中就必须得显性的
     *      调用父类构造方法
     */
    public Manager(String name, double salary, LocalDate birthday, double bonus) {
        super(name, salary, birthday);
        this.bonus = bonus;
    }

    public Manager(String name, double salary, LocalDate birthday) {
        super(name, salary, birthday);
    }

    // 为子类中的属性bonus设置get和set方法
    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }
    //由于Manager类中工资需要新的计算方法,所以重写父类Employee类中的getSalary()方法
    @Override
    public double getSalary() {
//        return this.getSalary()+this.bonus;  //这样写 getSalary()就是在无限循环自己调用自己,死循环
        return super.getSalary()+this.bonus;
    }

//    public void showName(){
//        System.out.println(super.name);  //不能用 super关键字访问父类的私有成员
//    }
}
