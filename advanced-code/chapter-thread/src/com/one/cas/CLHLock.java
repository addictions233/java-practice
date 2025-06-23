package com.one.cas;

import java.util.concurrent.atomic.AtomicReference;

/**
 *CLH队列锁
 * 队列锁较为常见的是CLH队列锁，其由Craig、Landin 和 Hagersten三位发明，并以他们的名字缩写进行组合而命名。Java中的AQS就是使用了CLH队列锁的变体。在之前的两种自旋锁实现SpinLock、TicketLock中，实际上存在一个明显性能问题。各线程均在同一个共享变量上进行自旋，竞争非常激烈时，其会导致非常繁重的总线、内存流量。因为当一个线程释放锁、更新共享变量时，会引发其他CPU中该共享变量对应的Cache Line缓存行失效，并重新经由总线、内存读取
 *
 * 而CLH队列锁则是将各个加锁线程包装为Node节点并构建为一个隐式链表。各Node不断自旋检查其前驱节点的状态，当某线程进行解锁操作时，则会修改自身Node节点的状态以让其后继节点结束自旋、获取到锁。其实现过程如下所示，之所以称之为隐式链表，是因为所谓的链表事实上是通过每个线程利用ThreadLocal类型的prevNode变量保存其前驱节点来维护的。由前文可知，各线程是在其前驱节点的status变量上进行自旋的，故在构建CLH队列锁实例的过程中首先向隐式链表中添加了一个哨兵节点，以便处理第一个加锁线程。值得一提的是，在解锁过程时，执行currentNode.remove() 是必要的。其目的是为了保证该线程在下一次加锁调用lock()时，能够通过 currentNode.get() 获取到一个新的Node实例，防止死锁。至此可以看出CLH队列锁是一个公平的自旋锁实现，其次由于各线程自旋在不同Node实例的status变量上避免了繁重的总线、内存流量
 */
public class CLHLock {
    /**
     * 隐式链表的队尾指针
     */
    private AtomicReference<Node> tail;

    /**
     * 隐式链表的当前节点
     */
    private ThreadLocal<Node> currentNode;

    /**
     * 隐式链表的前驱节点
     */
    private ThreadLocal<Node> prevNode;

    public CLHLock() {
        // 向隐式链表添加第一个节点, 其是哨兵节点
        tail = new AtomicReference<>( new Node() );
        currentNode = ThreadLocal.withInitial( Node::new );
        prevNode = new ThreadLocal<>();
    }

    public void lock() {
        // 每次第一次加锁都会获取一个新的Node实例, 作为当前节点
        Node node = currentNode.get();
        node.status = true;
        // 将当前节点加入隐式链表的队尾, 具体实现方式:
        // 通过队尾指针tail获取隐式链表的队尾节点prev, 并将其作为当前节点node的前驱节点;
        // 同时将队尾指针tail 指向 当前节点node
        Node prev = tail.getAndSet( node );
        prevNode.set( prev );
        // 当前线程在其前驱节点prev的status变量上进行自旋
        // 直到前驱节点释放锁时, 其status变量修改为false结束自旋
        while( prev.status );
    }

    public void unlock() {
        // 将当前节点的status变量修改为false, 使得其后继节点能够退出自旋
        Node node = currentNode.get();
        node.status = false;

        prevNode.remove();
        // 防止死锁
        currentNode.remove();
    }

    private static class Node {
        private volatile boolean status = false;
    }
}
