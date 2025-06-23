package com.corejava.interfacedemo.polymophic;

public interface Animal {
    void eat();
}

abstract class AbstractAnimal implements Animal{
    private int legs = 4;
}

class Dog extends AbstractAnimal implements Animal{
    @Override
    public void eat(){
        System.out.println("狗吃肉");
    }

    public void watchHome(){
        System.out.println("狗看门");
    }
//    public int showLegs(){
//        return legs;
//    }
}

class Cat implements Animal{
    public void eat(){
        System.out.println("猫吃鱼");
    }
    public void catchMouse(){
        System.out.println("猫抓鼠");
    }
}

