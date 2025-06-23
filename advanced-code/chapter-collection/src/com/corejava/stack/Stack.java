package com.corejava.stack;

import java.util.ArrayList;

/**
 * @author one
 * @description 用ArrayList实现一个栈的数据结构: 先进先出, JDK中的Queue队列接口是由LinkList实现的
 * @param <E>
 */
public class Stack<E> {
    private ArrayList<E> list = new ArrayList<>();
    public int size(){
        return list.size();
    }
    public boolean isEmpty(){
        return list.isEmpty();
    }

    /**
     * 入栈
     * @param element 元素
     */
    public void push(E element){
        list.add(element);
    }

    /**
     * 出栈
     * @return E
     */
    public E pop(){
        return list.remove(list.size()-1);
    }

    /**
     * 查询栈顶元素
     * @return E
     */
    public E top(){
        return list.get(list.size()-1);
    }
}
