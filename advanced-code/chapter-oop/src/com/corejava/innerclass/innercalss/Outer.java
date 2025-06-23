package com.corejava.innerclass.innercalss;

/**
 *  外部类中想使用内部类的成员必须先创建内部类对象,再用内部类对象调用其成员
 *  而内部类中是可以直接调用外部类成员的,因为内部类中有一个对外部类对象的引用,所以
 *  内部类可以通过该引用直接使用外部类成员,省去了在内部类中创建外部类对象的步骤
 */
public class Outer {
    int num;
    String str;
    Inner inner = new Inner();
    public void show(){
        System.out.println("这是外部类的show方法");
        inner.show();
    }

    private class Inner{
        Inner(){}
        static final int num =10;
        void show(){
            System.out.println("这是内部类的show方法");
        }
    }
}
