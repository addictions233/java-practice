package com.one.annotation2;

/**
 * @author one
 */
public class UseMyAnno2 {

    public void show(){
        System.out.println("调用了show方法");

    }

    @MyAnno2
    public void method(int num, String name){
        System.out.println("调用了method方法");

    }

}
