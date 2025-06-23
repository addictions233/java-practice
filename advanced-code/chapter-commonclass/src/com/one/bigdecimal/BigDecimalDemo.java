package com.one.bigdecimal;

import java.math.BigDecimal;

/**
 * 使用double,float都用精度丢失的问题,在涉及到金额计算时丢失精度是不能允许的,需要使用BigDecimal类
 * @author one
 */
public class BigDecimalDemo {
    public static void main(String[] args) {
        // 禁止使用构造方法BigDecimal(double) 的方式将double值转化为BigDecimal对象,必须使用BigDecimal(String)
        BigDecimal big1 = new BigDecimal(10.0);
        BigDecimal big2 = new BigDecimal(4.0);
        System.out.println(big1.divide(big2));

        BigDecimal big3 = new BigDecimal("10.0");
        BigDecimal big4 = new BigDecimal("3.0");
//        System.out.println(big3.divide(big4));
    }
}
