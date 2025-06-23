package com.one.agent;

import java.lang.instrument.Instrumentation;

/**
 * @author one
 * @description 构建javaAgent, 需要通过jvm的agent参数加载, javaAgent也是skywalking的探针实现方式
 * jdk提供了一种强大的可以对已有class代码进行运行时注入修改的能力。
 * javaagent可以在启动时通过-javaagent:agentJarPath或运行时attach加载agent包的方式使用，
 * 通过javaagent我们可以对特定的类进行字节码修改， 在方法执行前后注入特定的逻辑。
 * 通过字节码修改，可以实现监控tracing、性能分析、在线诊断、代码热更新热部署等等各种能力。
 *
 *  1.监控tracing: 分布式tracing框架的Java类库(比如skywalking, brave, opentracing-java)常使用javaagent实现，
 *    因为tracing需要在各个第三方框架内注入tracing数据的统计收集逻辑，比如要在grpc、kafka中发送消息前后收集tracing日志，
 *    但是这些第三方的jar包我们不方便修改它们的代码，使用javaagent就成为了很好的选择。
 *  2.性能分析: 很多性能分析软件例如jprofiler使用javaagent技术，一般分析分为sampling和instrumentation两种方式，
 *    sample是通过类似jstack的方式采集方法的执行栈，instrumentation就是修改字节码来收集方法的执行次数、耗时等信息。
 *  3.在线诊断: arthas这样的软件使用javaagent技术在运行时将诊断逻辑注入到已有代码中，实现watch，trace等功能
 *    代码热更新、热部署: 通过javaagent技术，还能够实现Java代码的热更新，减少Java服务重启次数，提升开发效率，
 *    比如开源的https://github.com/HotswapProjects/HotswapAgent和https://github.com/dcevm/dcevm
 * #使用
 * @date 2024-11-30
 */
public class JavaAgent {

    /**
     * 起作用原因: java.lang.instrument.Instrumentation 修改字节码 字节码技术: javasisst, ASM, ByteBuddy等字节码技术
     * skywalking的探针技术就是利用的javaAgent实现的
     */
    public static void premain(String args, Instrumentation instrumentation) {
        System.out.println("premain:" + args);

        // TODO 定位 UserService#insert  字节码技术: javasisst, ASM, ByteBuddy等字节码技术
    }
}
