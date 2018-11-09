package sw.melody.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author ping
 * @create 2018-11-08 14:36
 **/

public class SpinLock {

    private static AtomicReference<Thread> sign = new AtomicReference<>();

    private static int counter = 1;
    public static void lock() {
        boolean ret = false;
        while (sign.compareAndSet(null, Thread.currentThread())) {
            for (int i=0; i<10; i++) {
                System.out.println(Thread.currentThread().getName() + ": " + (counter++));
            }
            ret = true;
        }
        if (ret) {
            unLock();
        }
    }

    public static void unLock() {
        sign.compareAndSet(Thread.currentThread(), null);
    }

    public static void main(String[] args) throws InterruptedException {
        Executors.newFixedThreadPool(10);
        Thread t1 = new Thread(() -> lock());
        Thread t2 = new Thread(() -> lock());
        Thread t3 = new Thread(() -> lock());
        Thread t4 = new Thread(() -> lock());
        Thread t5 = new Thread(() -> lock());
        Thread t6 = new Thread(() -> lock());
        Thread t7 = new Thread(() -> lock());
        Thread t8 = new Thread(() -> lock());
        Thread t9 = new Thread(() -> lock());
        Thread t10 = new Thread(() -> lock());
        t1.setName("t1");
        t2.setName("t2");
        t3.setName("t3");
        t4.setName("t4");
        t5.setName("t5");
        t6.setName("t6");
        t7.setName("t7");
        t8.setName("t8");
        t9.setName("t9");
        t10.setName("t10");


        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
        t9.start();
        t10.start();

        Thread.sleep(10*1000);
    }

}
