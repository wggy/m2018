package sw.melody.thread;

import io.swagger.models.auth.In;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author ping
 * @create 2018-11-08 14:36
 **/

public class SpinLock {

    private static AtomicReference<Thread> sign = new AtomicReference<>();

    private static int counter = 0;
    public static void lock() {
        File file = new File("E:/file/".concat("log.txt"));
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            if (sign.compareAndSet(null, Thread.currentThread())) {
                if (counter >= 100) {
                    break;
                }
                System.out.println(counter);
                try {
                    bw.write(Thread.currentThread().getName()+" 获得锁, count=" + (counter++) + "\n");
                    bw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                unLock();
            }
        }

    }

    public static void unLock() {
        sign.compareAndSet(Thread.currentThread(), null);
    }

    public static void main(String[] args) throws InterruptedException {
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

        Thread.sleep(10000);

        Set<Integer> set = new HashSet<>();
        set.toString();
    }

}
