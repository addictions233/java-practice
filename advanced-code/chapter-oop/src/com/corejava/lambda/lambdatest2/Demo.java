package com.corejava.lambda.lambdatest2;

public class Demo {
    public static void main(String[] args) {
        //匿名内部类的形式
        useInterA(new InterA() {
            @Override
            public void show(String str, int num) {
                System.out.println(str+"----"+num);
            }
        },"aaaa",666);

        //lambda表达式的形式
        useInterA((str,num)-> System.out.println(str+"----"+num),"bbbb",444);

    }

    public static void useInterA(InterA a, String str, int num){
        a.show(str,num);
    }
}
