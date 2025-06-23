package com.one.optimize;

/**
 * @author one
 * @description jstack找出占用cpu最高的线程堆栈信息
 * @date 2024-12-8
 */
public class CPUTest {

    public int compute() {  //一个方法对应一块栈帧内存区域
        int a = 1;
        int b = 2;
        int c = (a + b) * 10;
        return c;
    }

    public static void main(String[] args) {
        CPUTest cpu = new CPUTest();
        while (true){
            cpu.compute();
        }
    }
}
