package com.one.algorithm;

/**
 *  递归: (区别于迭代)   俄罗斯套娃
 *      1,必须有方法,且方法有形参
 *      2,递归调用必须有出口
 *      3,实参必须变化,且变化朝着出口的方法变化
 *      4,递归的次数不能调用太多
 *      方法体中调用方法本身  没有出口的递归会导致方法不断的入栈,却没有方法弹栈,最终的结果会导致栈内存溢出
 *      每次循环调用一定要有实参的变化,且实参的变化是朝着出口的方向变化
 *      递归是一个方法不停的入栈,然后就是方法的不断弹栈,直到弹出第一个入栈的方法
 */
public class Recurrence {
    public static void main(String[] args) {
        System.out.println(jieChen(5));

    }

    /**
     *用递归来计算 n! 阶乘
     */
    public static int jieChen(int n){
        if(n==1){
            return 1;
        }
        System.out.println("1111");
        int result = n*jieChen(n-1);
        return result;
    }
}
