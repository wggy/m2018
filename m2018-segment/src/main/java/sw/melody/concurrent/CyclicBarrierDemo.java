package sw.melody.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;

/**
 * @author ping
 * @create 2019-03-08 11:26
 **/
@Slf4j
public class CyclicBarrierDemo {


    public static void main(String[] args) throws InterruptedException {
        int num = 10;
        CyclicBarrier barrier = new CyclicBarrier(num, new Runnable() {
            @Override
            public void run() {
                log.info("所有年级达到操场，开始升国旗...");
            }
        });

        for (int i=0; i<num; i++) {
            Grade grade = new Grade("班级" + (i+1), barrier);
            grade.start();
        }

        for (int i=0; i<num; i++) {
            Grade grade = new Grade("班级" + (i+1), barrier);
            grade.start();
        }

    }


    static class Grade extends Thread {
        private String name;
        CyclicBarrier cyclicBarrier;
        public Grade(String name, CyclicBarrier barrier) {
            this.name = name;
            this.cyclicBarrier = barrier;
        }
        @Override
        public void run() {
            log.info("{} 到达操场...", this.name);
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

}
