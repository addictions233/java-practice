package com.corejava.queue;

import java.util.Iterator;

/**
 *  自己定义一个队列 Queue接口
 *      其实现类需要提供以下的队列使用方法:
 * @param <E>
 */
public interface MyQueue<E> extends Iterable<E> {
    /**
     *  在队列尾部添加元素(入队)
     * @param element
     */
    public abstract void add(E element);

    /**
     *  在队列头部删除元素(出队)
     * @return
     */
    public abstract E remove();

    /**
     *  获取队列中存储的元素个数
     */
    public abstract int size();

    /**
     *  重写 Iterable接口中的 iterator()方法
     */
    @Override
    public abstract Iterator<E> iterator();
}
