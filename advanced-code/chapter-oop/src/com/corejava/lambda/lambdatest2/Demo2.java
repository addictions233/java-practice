package com.corejava.lambda.lambdatest2;

public class Demo2 {
    public static void main(String[] args) {
        InterB b = returnInterB();
        b.show(100);
        returnInterB().show(100);

    }

    public static InterB returnInterB() {
        return (num) -> System.out.println("num=" + num);
    }
}
