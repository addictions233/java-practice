package com.corejava.stack;

public class Test {
    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();
        stack.push("我");
        stack.push("是");
        stack.push("小");
        stack.push("粉");
        stack.push("红");
        int size = stack.size();
        for (int i = 0; i < size; i++) {
            System.out.println(stack.pop());
        }
    }
}
