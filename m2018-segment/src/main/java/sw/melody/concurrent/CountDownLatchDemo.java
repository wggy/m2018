package sw.melody.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CountDownLatchDemo {

    private static final int N = 10;

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(N);
        for (int i = 0; i < N; ++i) {
            new Thread(new Worker(startSignal, doneSignal, "Thread-" + i)).start();
        }

        doSomethingElse();
        startSignal.countDown();
        doSomethingElse();
        doneSignal.await();
    }

    static void doSomethingElse() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        log.info("线程：{} 睡眠2秒", Thread.currentThread().getName());
    }

    static class Worker extends Thread {
        private final CountDownLatch startSignal;
        private final CountDownLatch doneSignal;

        public Worker(CountDownLatch startSignal, CountDownLatch doneSignal, String name) {
            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
            this.setName(name);
        }

        @Override
        public void run() {
            try {
                startSignal.await();
                doWork();
                doneSignal.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        void doWork() throws InterruptedException {
            TimeUnit.SECONDS.sleep(3);
            log.info("线程：{} 睡眠3秒", this.getName());
        }
    }
}
