package com.one.volatileDemo;

/**
 * 使用双重锁实现单例模式中必须使用到volatile关键字
 * @author one
 */
public class DCLSingleton {
    /**
     *  看样子已经达到了要求，除了第一次创建对象之外，其它的访问在第一个if中就返回了，因此不会走到同步块中，已经完美了吗？
     *  如上代码段中的注释：假设线程一执行到instance = new Singleton()这句，这里看起来是一句话，但实际上其被编译后在JVM执行的对应会变代码就发现，
     *  这句话被编译成8条汇编指令，大致做了三件事情：
     *  1）给instance实例分配内存；
     *  2）初始化instance的构造器；
     *  3）将instance对象指向分配的内存空间（注意到这步时instance就非null了）
     *  如果指令按照顺序执行倒也无妨，但JVM为了优化指令，提高程序运行效率，允许指令重排序。如此，在程序真正运行时以上指令执行顺序可能是这样的：
     *  a）给instance实例分配内存；
     *  b）将instance对象指向分配的内存空间；
     *  c）初始化instance的构造器；
     *  这时候，当线程一执行b）完毕，在执行c）之前，被切换到线程二上，这时候instance判断为非空，
     *  此时线程二直接来到return instance语句，拿走instance然后使用，接着就顺理成章地报错（对象尚未初始化）。
     *  具体来说就是synchronized虽然保证了线程的原子性（即synchronized块中的语句要么全部执行，要么一条也不执行），
     *  但单条语句编译后形成的指令并不是一个原子操作（即可能该条语句的部分指令未得到执行，就被切换到另一个线程了）。
     *  根据以上分析可知，解决这个问题的方法是：禁止指令重排序优化，即使用volatile变量。
     */
    private static volatile DCLSingleton instance;

    private DCLSingleton() {

    }

    public static DCLSingleton getInstance() {
        if (instance == null) {
            synchronized (DCLSingleton.class) {
                if (instance == null) {
                    /**
                     * 对象创建的过程: 创建--> 初始化 --> 建立连接
                     * 1, 先申请内存,赋值默认为0
                     * 2, 构造方法 赋值初始化, 8
                     * 3, 建立连接 instance -> new DCLSingleton()
                     */
                    instance = new DCLSingleton();
                }
            }
        }
        return instance;
    }
}
