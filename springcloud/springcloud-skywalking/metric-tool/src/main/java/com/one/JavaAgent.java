package com.one;

import java.lang.instrument.Instrumentation;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author one
 * @description 利用javaAgent实现对jvm数据监测
 * @date 2024-11-30
 */
public class JavaAgent {

    public static void premain(String agentArgs, Instrumentation inst) {

        //每隔5秒打印JVM内存和GC信息
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Metric.printMemoryInfo();
                Metric.printGCInfo();
            }
        }, 0, 5000, TimeUnit.MILLISECONDS);
    }
}
