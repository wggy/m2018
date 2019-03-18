package sw.melody.concurrent;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ping
 * @create 2019-03-11 16:22
 **/
@Slf4j
public class ThreadInterruptedDemo {

    public static void main(String[] args) {
        StopThread thread = new StopThread();
        thread.start();
        thread.interrupt();
        while (thread.isAlive()) {
        }
        thread.print();
        log.info("线程中断状态：{}", Thread.interrupted());
        log.info("线程中断状态：{}", Thread.interrupted());
    }


    static class StopThread extends Thread {
        private int x = 0;
        private int y = 0;

        @Override
        public void run() {
            synchronized (this) {
                while (Thread.currentThread().isInterrupted()) {
                    x++;
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        log.info("线程抛出中断异常");
                    }
                    y++;
                }
            }
        }

        void print() {
            log.info("x={}, y={}", x, y);
        }
    }
}
