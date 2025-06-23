package com.one.log;

import org.apache.log4j.Logger;

/**
 * @ClassName: Log4jDemo1
 * @Description: 日志记录的特点:
 *                  1, 日志的输出位置可以是多种: 控制台,文件或者数据库
 *                  2, 日志是多线程记录的,不影响业务代码的性能
 *     日志记录的体系结构:
 *           日志接口有两个:
 *                  一, apache的 commons-logging 简称 JCL
 *                  二, simple logging facade for java  简称: slf4j
 *          日志的常用实现:
 *                  log4j, logback ,JUL(java.util.logging), log4j2
 *          log4j使用的配置文件名称必须叫做: log4j.properties
 * @Author: one
 * @Date: 2021/06/21
 */
public class Log4jDemo1 {

    /**
     * 这里所有使用的类都是log4j包下的Logger类,如果导入了Slf4j包,也可以使用该包下的Logger类
     */
    private static final Logger  LOGGER = Logger.getLogger(Log4jDemo1.class);

    public static void main(String[] args) {
        LOGGER.fatal("fatal级别的日志");
        LOGGER.error("error级别的日志");
        LOGGER.warn("warn级别的日志");
        LOGGER.info("info级别的日志");
        // 如果配置文件 log4j.properties中设置的日志输出级别为info,则不会输出debug级别的日志
        LOGGER.debug("debug级别的日志");
    }

    /**
     * 日志记录三要素:
     *      一, Loggers(记录器) : 日志的五种记录级别: debug < info < warn < error < fatal
     *                            只输出比设置记录日志级别高的日志信息
     *     二, Appenders(输出源): 把日志输出到不同的地方: 如 Console控制台, Files文件等
     *                      log4j.appender.ca = org.apache.log4j.ConsoleAppender
     *                      log4j.appender.fa = org.apache.log4j.FileAppender
     *     三, Layouts(布局) : 常用的布局管理器如下:
     *                      org.apache.log4j.PatternLayout(可以灵活地指定布局模式)
     *                      org.apache.log4j.SimpleLayout(包含日志信息的级别和信息字符串)
     *                      org.apache.log4j.TTCCLayout(包含日志产生的时间,线程,类别等信息)
     */
}
