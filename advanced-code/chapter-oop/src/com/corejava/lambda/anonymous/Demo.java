package com.corejava.lambda.anonymous;

/**
 * @author one
 *  测试匿名内部类的使用
 */
public class Demo {
    public static void main(String[] args) {
        useInnerA(new InnerA() {
            @Override
            public void show() {
                System.out.println("匿名内部类作为方法的实参传递给形参");
            }
        });
        returnInnerA().show();
    }

    /**
     * 匿名内部类作为方法的实参传递
     * @param i i
     */
    public static void useInnerA(InnerA i){
        i.show();
    }

    /**
     * 匿名内部类作为方法的返回值类型
     * @return InnerA
     */
    public static InnerA returnInnerA(){
        return new InnerA() {
            @Override
            public void show() {
                System.out.println("匿名内部类作为方法的返回值");
            }
        };
    }
}
