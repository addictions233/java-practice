package com.corejava.queue;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *  用固定长度数组来实现队列:  队列特点:  先进先出, 只能在队头出队,只能在队尾入队
 *      提示: 队列底层包含一个数组和两个指针 head 和 tail ,
 *                  空队列:  head == tail
 *                  满队列:  head == tail+1
 * @param <E>
 */
public class CircularArrayQueue<E> implements MyQueue<E> {
    /**
     *  声明一个数组,用来存储元素
     */
    private E[] array;

    /**
     *  声明指针
     */
    private int head;
    private int tail;

    /**
     *  定义一个常量,用来表示队列的默认初始化容量;
     */
    private static final int MAX_CAPACITY = 3;

    /**
     *  空参构造
     */
    public CircularArrayQueue(){
        this(MAX_CAPACITY);
    }
    /**
     *  有参构造
     */
    public CircularArrayQueue(int Capacity){
        if(Capacity <= MAX_CAPACITY){
            array = (E[]) new Object[MAX_CAPACITY+1];  // 当 head == tail+1时表示队满 ,所以必须要浪费一个数组空间
        } else{
            array = (E[]) new Object[Capacity+1];
        }
        head = tail =0;
    }

    /**
     *  队尾有元素入队
     * @param element
     */
    @Override
    public void add(E element) {
        //向队列中添加元素时,要先对队列是否为满队列进行判断
       if(tail == array.length-1){ //当队尾指针指向数组最后一个位置时
           if(head == 0){ // 队头指针指向数组的第一个位置,即为满队列
               System.out.println("添加元素失败,队列为满队列");
               return;
           } else{
               array[tail] = element;
               tail = 0;
           }

       } else {
           if( tail+1 == head){  // 一般情况下用 tail+1 == head 来判断是否为满队列
               System.out.println("添加元素失败,队列为满队列");
               return;
           } else{
               array[tail] = element;
               tail++;
           }
       }
    }

    /**
     *  队头有元素出队
     * @return
     */
    @Override
    public E remove() {
        // 在队列中删除元素之前,得对队列是否为空进行判断
        if(head == tail){ //任何情况下直接判断 head==tail就能判断队列是否为空
            System.out.println("队列元素为空,无法删除元素");
            return null;
        } else{
            E element = array[head];
            array[head] = null;
            if(head == array.length-1){
                head = 0;
            } else{
                head++;
            }
            return element;
        }

    }

    /**
     *  判断队列中村存储的元素个数
     * @return
     */
    @Override
    public int size() {
        if(tail >= head){
            return tail-head;
        } else{
            return MAX_CAPACITY+1-(head-tail);
        }
    }

    /**
     *  重写迭代器接口 Iterable接口的 Iterator<E> iterator()方法
     * @return
     */
    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    /**
     * 给自己定义的集合写一个迭代器接口的实现类 Itr
     */
    private class Itr implements Iterator<E> {
        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such
//        int expectedModCount = modCount;

        // prevent creating a synthetic constructor
        Itr() {}   //因为是用private修饰的内部类,所以就没有在构造器前面加访问修饰符

        public boolean hasNext() {
            return cursor != size();
        }

        @SuppressWarnings("unchecked")
        public E next() {
//            checkForComodification();   // 这句是抛出并发修改异常的
            int i = cursor;
            if (i >= size())
                throw new NoSuchElementException();
            Object[] elementData = CircularArrayQueue.this.array;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (E) elementData[lastRet = i];
        }
    }
}
