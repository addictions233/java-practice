package com.corejava.interfacedemo.inter1;

import java.time.LocalDate;

/**
 * @author one
 */
public class Main {
    public static void main(String[] args) {
        Inter7Impl i7 = new Inter7Impl();
        System.out.println(i7.function());
        i7.show();
        System.out.println(Inter7Impl.num);
        LocalDate localDate = LocalDate.now();
    }
}
