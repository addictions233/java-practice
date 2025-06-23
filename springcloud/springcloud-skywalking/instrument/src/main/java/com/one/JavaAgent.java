package com.one;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

/**
 * @author one
 * @description instrumentation就是修改字节码来收集方法的执行次数、耗时等信息。
 * @date 2024-11-30
 */
public class JavaAgent {

    public static void premain(String args, Instrumentation instrumentation) {
        System.out.println("premain: 获取方法调用时间");

        // 添加Transformer
        ClassFileTransformer transformer = new PerformMonitorTransformer();
        instrumentation.addTransformer(transformer);
    }
}
