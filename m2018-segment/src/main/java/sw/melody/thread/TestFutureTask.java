package sw.melody.thread;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;
import java.util.concurrent.*;

/**
 * @author ping
 * @create 2018-11-14 10:23
 **/

public class TestFutureTask {
    private static final ThreadPoolExecutor threadPoolTaskExecutor = new ThreadPoolExecutor(10, 10, 60L, TimeUnit.SECONDS, new LinkedBlockingDeque());


    public static void main(String[] args) {
        try {
            handle();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void handle() throws ExecutionException, InterruptedException {
        TaskRun task = new TaskRun();
        FutureTask<Integer> futureTask = new FutureTask<>(task, 50);
        threadPoolTaskExecutor.submit(futureTask);
        while (true) {
            if (futureTask.isDone()) {
                System.out.println(futureTask.get());
                break;
            }
        }


    }

    private static class Task implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            System.out.println("子线程在进行计算");
            Thread.sleep(3000);
            int sum = 0;
            for(int i=0;i<100;i++)
                sum += i;
            return sum;
        }
    }

    private static class TaskRun implements Runnable {

        @Override
        public void run() {
            System.out.println("子线程在进行计算");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int sum = 0;
            for(int i=0;i<100;i++)
                sum += i;
        }
    }
}
