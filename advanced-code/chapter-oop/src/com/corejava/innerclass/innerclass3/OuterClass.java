package com.corejava.innerclass.innerclass3;

public class OuterClass {
    public void function(InnerClass innerClass){
        innerClass.method();
    }
    public abstract static class InnerClass{
        public abstract void method();
        public int num = 10;
    }

}
