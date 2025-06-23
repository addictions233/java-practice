package com.one.atomic;

/**
 * @ClassName: Main2
 * @Description: 测试 AtomicReference类的使用
 * @Author: one
 * @Date: 2022/02/14
 */
public class Main2 {
    public static void main(String[] args) throws InterruptedException {
        Reference reference = new Reference(0,System.currentTimeMillis());
        AtomicReferenceDemo atomicReferenceDemo = new AtomicReferenceDemo(reference);
        // 创建一百个线程, 分别去执行atomicReferenceDemo对象中的atomicIncrement()方法
        for (int i = 0; i < 100; i++) {
            new Thread(() -> atomicReferenceDemo.atomicIncrement()).start();
        }

        // 打印最终的结果, 让主线程休眠等待所有任务执行完成
        Thread.sleep(3000);
        Reference reference1 = atomicReferenceDemo.getAtomicReference().get();
        System.out.println("最终的结果:" + reference1.getSequence());
        System.out.println("最终的消耗时间:" + (reference1.getTimestamp() - reference.getTimestamp()));
        System.out.println("总的修改次数:" + atomicReferenceDemo.getTimes().get());
    }
}
