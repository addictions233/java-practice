package com.corejava.anonymousinnerclass;

public class Main {

    public static void main(String[] args) {
        // 直接使用子类
        Person studentPerson = new Student();
        studentPerson.eat();

        // 使用匿名内部类: 在java中使用 new对象后加{} 来定义匿名内部类
        // new对象后跟大括号 {} 的写法，实际上是创建了一个‌匿名内部类‌（Anonymous Inner Class）。
        // 这种语法允许你在声明对象的同时，动态地定义一个继承自某个类或实现某个接口的匿名子类，并重写其方法
        Person anonymousPerson = new Person() {
            @Override
            public void eat() {
                System.out.println("这是匿名内部类中的eat方法");
            }
        };
        anonymousPerson.eat();

        // 匿名内部类的构造器: 可以使用 new类名{{ "这里进行匿名对象的初始化赋值" }}
        Person anonymousPerson2 = new Person() {
            /** 这里再打一个打括号，就是在匿名子类的构造器中写东西 **/ {
                setName("zhangsan");
            }

            @Override
            public void eat() {
                System.out.println("匿名内部类中" + getName() + "在吃饭!");
            }
        };
        anonymousPerson2.eat();


        String sql = new SQL() {
            {
                // 在匿名内部类中调用方法进行初始化赋值
                select("*").from("t_user").where("uname like 'j%'")
                        .and("sex='男'").orderby("id desc");
            }
        }.toString();
        System.out.println(sql);
    }
}
