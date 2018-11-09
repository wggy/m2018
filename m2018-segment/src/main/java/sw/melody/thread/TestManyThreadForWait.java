package sw.melody.thread;

import java.util.concurrent.CountDownLatch;

/**
 * @author ping
 * @create 2018-11-07 17:11
 **/

public class TestManyThreadForWait {
    public static void main(String[] args) {
        TestManyThreadForWait testManyThreadForWait = new TestManyThreadForWait();
        testManyThreadForWait.begin();
    }

    private static CountDownLatch countDownLatch = new CountDownLatch(10);


    private void begin() {
        for (int i=1; i<=10; i++) {
            new UserRunner(i).start();
            countDownLatch.countDown();
        }
    }

    private class UserRunner extends Thread {

        int result;
        public UserRunner(int result) {
            this.result = result;
        }
        @Override
        public void run() {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(this.getName() + ": " + "执行完成。");
        }
    }
}
