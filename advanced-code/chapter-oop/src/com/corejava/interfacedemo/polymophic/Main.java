package com.corejava.interfacedemo.polymophic;

/**
 * @author one
 *  instanceof 使用:
 *  格式: 对象 instanceof 类
 *  判断前面的对象的实际类型是不是后面的类的派生类, 是 返回true , 不是 返回 false
 */
public class Main {
    public static void main(String[] args) {
        useAnimal(new Dog());  // 输出 : 狗吃肉 狗看门
        useAnimal(new Cat());  // 输出 : 猫吃鱼 猫抓鼠

    }

    public static void useAnimal(Animal animal){
        animal.eat();
        // 强转类型转换对于自定义对象,是将多态中的父类对象转换为原型的子类对象
        if(animal instanceof Dog){
            Dog d = (Dog)animal;
            d.watchHome();
        }
        if (animal instanceof Cat){
            Cat c= (Cat)animal;
            c.catchMouse();
        }
    }
}
