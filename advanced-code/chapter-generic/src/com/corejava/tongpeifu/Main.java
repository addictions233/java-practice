package com.corejava.tongpeifu;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Integer> list = List.of(1, 2, 3, 4, 5);
        System.out.println(MaxUtil.max(list));
    }
}
