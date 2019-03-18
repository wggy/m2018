package sw.melody.concurrent;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CountDownLatchDemo2 {
    private static final int N = 10;

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch doneSignal = new CountDownLatch(N);
        Executor e = Executors.newCachedThreadPool();

        for (int i = 0; i < N; ++i) {
            e.execute(new Worker(doneSignal, i));
        }
        System.out.println(doneSignal.getCount());

        doneSignal.await();
        System.out.println(doneSignal.getCount());
        doneSignal = new CountDownLatch(N);
        for (int i = N; i < N + N; ++i) {
            e.execute(new Worker(doneSignal, i));
        }

        System.out.println(doneSignal.getCount());
    }


    static class Worker extends Thread {
        private final CountDownLatch doneSignal;
        private final int i;

        Worker(CountDownLatch doneSignal, int i) {
            this.doneSignal = doneSignal;
            this.i = i;
        }

        @Override
        public void run() {
            try {
                doWork(i);
                doneSignal.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        void doWork(int i) throws InterruptedException {
            TimeUnit.SECONDS.sleep(i);
            log.info("线程：{} 等待{}秒", this.getName(), i);
        }
    }
}
