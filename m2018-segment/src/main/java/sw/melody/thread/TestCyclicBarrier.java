package sw.melody.thread;

import java.util.concurrent.CyclicBarrier;

/**
 * @author ping
 * @create 2018-11-07 17:27
 **/

public class TestCyclicBarrier {
    private CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

    public static void main(String[] args) {
        new TestCyclicBarrier().begin();
    }

    public void begin() {
        for (int i = 0; i < 5; i++) {
            new Thread(new Student()).start();
        }
    }

    private class Student implements Runnable {

        @Override
        public void run() {
            try {
                System.out.println("学生" + Thread.currentThread().getName() + " 正在赶往XX饭店的路上");
                Thread.sleep(2000);
                //到了就等着，等其他人都到了，就进饭店
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("大家都到了，进去吃饭吧！");
        }
    }
}
