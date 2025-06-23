package com.one.conpool.main;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author one
 *  写一个 TimeCost类,用来测试一段代码的执行耗费时间
 *      使用了访问器的设计思想
 */
public final class TimeCost {
    private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

    /**
     * 定义访问器接口, 实现该接口必须重写execute()方法
     */
    public interface Task {
        void execute();
    }

    /**
     * 其它类要想调用test()测试一段代码的执行时间,必须给该方法传一个访问器 task对象
     * @param task
     */
    public static void test(Task task){
        if(task==null) return;
        System.out.println("起始时间:"+sdf.format(new Date()));
        long begin = System.currentTimeMillis();
        task.execute();
        long finish = System.currentTimeMillis();
        System.out.println("结束时间:"+sdf.format(new Date()));
        System.out.println("耗时:"+(finish-begin)+"ms");
    }
}
