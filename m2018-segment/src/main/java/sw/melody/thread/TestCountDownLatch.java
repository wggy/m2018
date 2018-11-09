package sw.melody.thread;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @author ping
 * @create 2018-11-06 11:31
 **/

public class TestCountDownLatch {
    private CountDownLatch countDownLatch = new CountDownLatch(4);

    public static void main(String[] args) {
        TestCountDownLatch testCountDownLatch = new TestCountDownLatch();
        testCountDownLatch.begin();
    }

    private void begin() {
        System.out.println("赛跑开始");

        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 4; i++) {
            //随机设置每个运动员跑多少秒结束
            int result = random.nextInt(3) + 1;
            new Thread(new Runner(result)).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("所有人都跑完了，裁判开始算成绩");
    }



    private class Runner implements Runnable {
        private int counter;

        public Runner(int counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(counter * 1000);
                System.out.println("运动员" + Thread.currentThread().getName() + "跑了" + counter + "秒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
        }
    }
}
