package com.one.threadbase;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @author one
 * @description CPU的核心数
 * @date 2024-12-17
 */
public class CPUTest {

    public static void main(String[] args) {
        int cpu = Runtime.getRuntime().availableProcessors();
        // 获取CPU的逻辑核数
        System.out.println(cpu);
        System.out.println("------------------------------------------------");
        // Java虚拟线程系统的管理接口
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        // 仅仅获取线程和线程堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        for (ThreadInfo threadInfo : threadInfos) {
            // 打印线程信息
            System.out.println("[" + threadInfo.getThreadId() + "]" + threadInfo.getThreadName() + ":" + threadInfo.getThreadState());
        }
    }
}
