package com.one.bigdecimal;

import java.math.BigDecimal;

/**
 * @PackageName: com.corejava.bignumber
 * @ClassName: BigDecimalDemo
 * @Description:
 * @Author: one
 * @Date: 2020-9-5 21:00
 */
public class BigDecimalDemo2 {
    public static void main(String[] args) {

        double x = 10.0/3;
        System.out.println(x);
        /**
         * 为什么大数能够进行精确计算? 当小数点后无法除尽时,会抛出下面异常
         * ArithmeticException: Non-terminating decimal expansion; no exact representable decimal result.
         */
        BigDecimal a = BigDecimal.valueOf(10);
        BigDecimal b = BigDecimal.valueOf(3);
        BigDecimal c = a.divide(b);
        System.out.println("c=" + c);
    }
}
