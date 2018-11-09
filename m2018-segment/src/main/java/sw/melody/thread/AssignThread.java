package sw.melody.thread;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author ping
 * @create 2018-10-31 9:23
 **/
@Setter
@Getter
public class AssignThread extends Thread {
    private static final ConcurrentHashMap<Integer, JobThread> jobHolder = new ConcurrentHashMap<>();
    private static LinkedBlockingQueue<JobThread> triggerQueue = new LinkedBlockingQueue<>();

    private static final int threadNum = 8;
    private int totalCnt = 150000000;
    private int stepCnt = 1000000;
    private int currCnt = 0;
    private int currIndex = 1;
    private volatile boolean isStop = false;

    @Override
    public void run() {

        long start = System.currentTimeMillis();
        //初始化业务线程
        init();

        while (!isStop) {
            try {
                JobThread jobThread = triggerQueue.poll(3L, TimeUnit.SECONDS);

                if (jobThread != null && assign()) {
                    isStop = true;
                    long end = System.currentTimeMillis();
                    System.out.println("总共耗时：".concat((end - start) + "毫秒"));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public static void pushTriggerQueue(JobThread jobThread) {
        triggerQueue.add(jobThread);
    }

    public void init() {
        for (; currIndex <= threadNum; ) {
            // 1.创建记录 id、threadName、startId、endId、beginTime、endTime
            // 2.创建并启动线程
            assign();
        }
    }

    public boolean assign() {
        boolean result = false;
        if (currCnt < totalCnt) {
            int end = currCnt + stepCnt;
            if (end > totalCnt) {
                end = totalCnt;
                result = true;
            }
            int begin = currCnt + 1;
            String jobName = "thread-".concat((currIndex++) + "-").concat(begin + "-").concat(end + "");
            JobThread jobThread = new JobThread(jobName, begin, end, System.currentTimeMillis());
            jobHolder.put(currIndex, jobThread);
            jobThread.start();
            currCnt += stepCnt;
        } else {
            result = true;
        }
        return result;
    }

    public static void main(String[] args) {
        AssignThread assignThread = new AssignThread();
        assignThread.start();
    }
}
