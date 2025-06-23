package com.one.method;

public class Dog {
    public void eat(){
        System.out.println("狗吃骨头");
    }

    protected void eat(String food) throws RuntimeException{
        System.out.println("狗吃"+food);
    }

    String function(){
        return "狗看门";
    }

    private int legCount(String name){
        if("狗".equals(name)) return 4;
        if("鸡".equals(name)) return 2;
        return 0;
    }
}
