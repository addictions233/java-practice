package com.one.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @ClassName: AtomicReferenceDemo
 * @Description:
 * 说到CAS理论，在java中我们第一个就想到了atomic类，一般常见的有AtomicInteger、AtomicBoolean等java.util.concurrent包下面的类，
 * 但是这个只能并发修改一个属性，
 * 如果我需要对多个属性同时进行并发修改，并且保证原子性呢？AtomicReference和AtomicInteger非常类似，
 * 不同之处就在于AtomicInteger是对整数的封装，而AtomicReference则对应普通的对象引用，是操控多个属性的原子性的并发类。
 *
 * AtomicReference: 解决并发修改多个属性
 * @Author: one
 * @Date: 2022/02/13
 */
public class AtomicReferenceDemo {
    /**
     * 原子引用
     */
    private AtomicReference<Reference> atomicReference;

    /**
     * 记录总的尝试修改次数
     */
    private AtomicInteger times = new AtomicInteger(0);

    /**
     * 构造器
     * @param reference 原子引用初始化对象值
     */
    public AtomicReferenceDemo(Reference reference) {
        atomicReference = new AtomicReference<>(reference);
    }

    /**
     * 模拟一个场景，在高并发的场景中，根据业务的需要，要求同时更新sequence和timestamp
     */
    public void atomicIncrement() {
        while (true) {
            times.getAndIncrement();
            // 获取并缓存原来的reference对象, 这个对象包含原来的序列和时间戳
            Reference referenceOld = atomicReference.get();
            // 基于原来的变量来更新新的序列和时间戳
            // 如果仅仅是改变序列,我们可以把reference对象中的sequence变量类型改变为 AtomicInteger
            long sequence = referenceOld.getSequence();
            sequence++;
            long timestamp = System.currentTimeMillis();
            Reference referenceNew = new Reference(sequence,timestamp);
            System.out.println(Thread.currentThread().getName() + "测试,旧值:" + referenceOld + ",新值:"+ referenceNew);
            // 使用CAS操作更新原来的变量, 更新的过程中, 需要传递原来的变量
            // compareAndSet()方法是native的，比对两个对象的方式不是equals而是==,比对的是内存的中地址
            // 如果当前atomicReference内存中的值和传递的referenceOld值一样,就进行修改, flag返回true
            // 如果当前atomicReference内存中的值和referenceOld值不一样,flag返回false;
            boolean flag = atomicReference.compareAndSet(referenceOld, referenceNew);
            System.out.println(Thread.currentThread().getName() + "修改的flag结果:" + flag);
            // 如果保存的原来的变量被其他线程修改了, 就需要重新拿到最新的变量,并再次计算,重新修改
            if (flag) {
                System.out.println("-------" + Thread.currentThread().getName() + "修改结果成功------");
                break;
            }
        }
    }


    public AtomicReference<Reference> getAtomicReference() {
        return atomicReference;
    }

    public AtomicInteger getTimes() {
        return times;
    }
}
