package sw.melody.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@Slf4j
public class WaitNotifyAndLockSupport {
    private static final Object obj = new Object();

    public static void main(String[] args) {
//        Thread t = new Thread(() -> {
//            System.out.println("t start...");
//            LockSupport.park();
//            LockSupport.park();
//            System.out.println("t finished...");
//        });
//        t.start();
//        LockSupport.unpark(t);
//        LockSupport.unpark(t);

        Executor executor = Executors.newCachedThreadPool();
        Pool pool = new Pool();
        for (int i = 0; i < 10; i++) {
            executor.execute(new Student(i+1, pool));
        }

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pool.releaseTrack();

    }


    static class Pool {
        private static final int MAX_AVAILABLE = 1;
        private final Semaphore available = new Semaphore(MAX_AVAILABLE, true);
        protected Track[] items = {new Track(1)};

        protected boolean[] used = new boolean[MAX_AVAILABLE];

        public Track getTrack() throws InterruptedException {
            available.acquire();
            return getNextAvailableItem();
        }

        public void releaseTrack(Object x) {
            if (markAsUnused(x)) {
                available.release();
            }
        }

        public void releaseTrack() {
            for (int i = 0; i < MAX_AVAILABLE; ++i) {
                used[i] = false;
            }
            available.release();
        }


        protected synchronized Track getNextAvailableItem() {
            for (int i = 0; i < MAX_AVAILABLE; ++i) {
                if (!used[i]) {
                    used[i] = true;
                    return items[i];
                }
            }
            return null;
        }

        protected synchronized boolean markAsUnused(Object item) {
            for (int i = 0; i < MAX_AVAILABLE; ++i) {
                if (item == items[i]) {
                    if (used[i]) {
                        used[i] = false;
                        return true;
                    }
                    return false;
                }
            }
            return false;
        }
    }

    static class Track {
        private int num;

        public Track(int num) {
            this.num = num;
        }

        @Override
        public String toString() {
            return "Track{" +
                    "num=" + num +
                    '}';
        }
    }

    static class Student implements Runnable {

        private int num;
        private Pool pool;

        public Student(int num, Pool pool) {
            this.num = num;
            this.pool = pool;
        }

        @Override
        public void run() {

            try {
                //获取跑道
                Track track = pool.getTrack();
                if (track != null) {
                    System.out.println("学生" + num + "在" + track.toString() + "上跑步");
                    TimeUnit.SECONDS.sleep(2);
//                    System.out.println("学生" + num + "释放" + track.toString());
                    //释放跑道
//                    pool.releaseTrack(track);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}


