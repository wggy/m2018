package sw.melody.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ping
 * @create 2018-11-12 17:03
 **/

public class TicketLock {
    private AtomicInteger serviceNum = new AtomicInteger();
    private AtomicInteger ticketNum = new AtomicInteger();

    public int lock() {
        int ticket = ticketNum.getAndIncrement();
        // 只要当前服务号不是自己的就不断轮询
        while (serviceNum.get() != ticket) {

        }

        return ticket;
    }

    public void unlock(int myTicket) {
        // 只有当前线程拥有者才能释放锁
        int next = myTicket + 1;
        serviceNum.compareAndSet(myTicket, next);
    }

}
