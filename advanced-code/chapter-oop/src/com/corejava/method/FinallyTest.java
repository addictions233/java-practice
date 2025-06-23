package com.corejava.method;

/**
 * @ClassName: FinallyTest
 * @Description: TODO
 * @Author: one
 * @Date: 2022/03/25
 */
public class FinallyTest {
    public static void main(String[] args) {
        getNumber();
    }

    private static int getNumber() {
        int i = 0;
        try {
            System.out.println();
            i =  1 / 1;
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("4444");
        }
        return i;
    }
}
