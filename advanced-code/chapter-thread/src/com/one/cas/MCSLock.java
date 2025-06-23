package com.one.cas;

import java.util.concurrent.atomic.AtomicReference;

/**
 * MCS队列锁
 * 前面提到了CLH队列锁的各种好处，看上去好像一切都很完美。其实不然，CLH在SMP结构性下非常有效。
 * 但在NUMA结构中，如果其前驱节点不是位于该CPU自身模块的本地内存中，而是位于其他CPU模块的本地内存中，
 * 则会导致效率大幅降低。
 * 故John M. Mellor-Crummey、Michael L. Scott提出、发明了MCS队列锁。
 * 其与CLH队列锁的最大不同在于，MCS队列锁中各加锁线程是在自身的Node节点上进行自旋。
 * 这一改进使得其在NUMA结构下也具有很好的性能表现。具体地，其同样是先将各加锁线程包装为Node节点，
 * 然后通过Node的next变量指向其后继节点来显式地维护链表。当某线程进行解锁操作时，
 * 则会通过next指针找到当前节点的后继节点，并修改后继节点的状态以让其后继节点结束自旋、获取到锁。
 * 实现过程如下所示，当然MCS队列锁同样是一个公平的自旋锁
 */
public class MCSLock {
    /**
     * 链表的队尾指针
     */
    private AtomicReference<Node> tail;

    /**
     * 链表的当前节点
     */
    private ThreadLocal<Node> currentNode;

    public MCSLock() {
        tail = new AtomicReference<>(null);
        currentNode = ThreadLocal.withInitial( Node::new );
    }

    public void lock() {
        // 每次加锁获取一个新的Node实例, 作为当前节点
        Node node = currentNode.get();
        node.status = true;
        // 通过队尾指针tail获取链表的队尾节点prev, 并将队尾指针tail指向当前节点
        Node prev = tail.getAndSet( node ); // (1)
        // 旧的队尾节点(即prev)存在, 说明链表不为空
        if( prev!=null ) {
            // 当前节点加入链表
            prev.next = node;               // (2)
            // 当前线程在其自身节点的status变量上进行自旋
            // 直到前驱节点prev释放锁时, 将当前节点node的status修改为false
            while( node.status );
        }
    }

    public void unlock() {
        Node node = currentNode.get();
        // 如果当前节点的后继节点为空, 则需要清空队列, 即将队尾指针tail置空
        if( node.next==null ) {
            // 通过CAS的方式安全地将队尾指针tail置空
            if( !tail.compareAndSet(node, null) ) {
                //  如果这个CAS操作失败, 则说明有节点突然入队, 即刚刚执行完lock方法的(1)代码                
                //  此时需要等待lock方法的(2)代码完成
                while( node.next==null );
            }
        }

        // 当前节点的后继节点存在
        if( node.next!=null ) {
            // 将当前节点的后继节点的status变量修改为false
            node.next.status = false;
            node.next = null;
        }
        currentNode.remove();
    }

    private static class Node {
        private volatile boolean status = false;

        private volatile Node next = null;
    }
}