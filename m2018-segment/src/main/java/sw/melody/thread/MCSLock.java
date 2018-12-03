package sw.melody.thread;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @author ping
 * @create 2018-11-12 17:15
 **/

public class MCSLock {
    private static class MCSNode {
        MCSNode next;
        boolean isLocked = true;
    }

    volatile MCSNode queue;

    private static final AtomicReferenceFieldUpdater<MCSLock, MCSNode> updater = AtomicReferenceFieldUpdater.newUpdater(MCSLock.class, MCSNode.class, "queue");

    public void lock(MCSNode node) {
        // step 1
        MCSNode predecessor = updater.getAndSet(this, node);
        if (predecessor != null) {
            // step 2
            predecessor.next = node;
            // step 3
            while (predecessor.isLocked) {

            }
        }
    }

    public void unLock(MCSNode node) {
        // 锁拥有者进行释放锁才有意义
        if (updater.get(this) == node) {
            // 检查是否有人排在自己后面
            if (node.next == null) {
                // step 4
                if (updater.compareAndSet(this, node, null)) {
                    // compareAndSet返回true表示确实没有人排在自己后面
                    return;
                } else {
                    // 突然有人排在自己后面了，可能还不知道是谁，下面是等待后续者
                    // 这里之所以要忙等是因为：step 1执行完后，step 2可能还没执行完
                    // step 5
                    while (node.next == null) {
                    }
                }
            }
            node.next.isLocked = false;
            node.next = null;
        }
    }
}
