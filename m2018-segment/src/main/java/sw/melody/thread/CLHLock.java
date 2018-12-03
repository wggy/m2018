package sw.melody.thread;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @author ping
 * @create 2018-11-12 16:57
 **/

public class CLHLock {

    @Getter
    @Setter
    private static class CLHNode {
        private boolean isLocked = true;
        private Thread thread;
        private CLHNode prev;
        private CLHNode next;

        public CLHNode(boolean isLocked, Thread thread) {
            this.isLocked = isLocked;
            this.thread = thread;
        }

        public CLHNode(boolean isLocked) {
            this.isLocked = isLocked;
            this.thread = Thread.currentThread();
        }
    }

    private volatile CLHNode head;
    private volatile CLHNode tail;


    void setHead(CLHNode node) {
        head = node;
        node.thread = null;
        node.prev = null;
    }


    public static void main(String[] args) {
    }


    private static final AtomicReferenceFieldUpdater<CLHLock, CLHNode> updater = AtomicReferenceFieldUpdater.newUpdater(CLHLock.class, CLHNode.class, "tail");


    public void lock(CLHNode currentThreadCLHNode) {
        // 把this里的"tail" 值设置成currentThreadCLHNode
        CLHNode preNode = updater.getAndSet(this, currentThreadCLHNode);
        if (preNode != null) {
            while (preNode.isLocked) {

            }
        }
    }

    public void unLock(CLHNode currentThreadCLHNode) {
        // 如果队列里只有当前线程，则释放对当前线程的引用（for GC）。
        if (!updater.compareAndSet(this, currentThreadCLHNode, null)) {
            currentThreadCLHNode.isLocked = false;
        }
    }
}
