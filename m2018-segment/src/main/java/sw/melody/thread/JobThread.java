package sw.melody.thread;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ping
 * @create 2018-10-31 9:23
 **/
@Setter
@Getter
public class JobThread extends Thread {

    private volatile boolean toStop = false;

    private String jobId;
    private int startId;
    private int endId;
    private long startTime;

    public JobThread(String jobId, int startId, int endId, long startTime) {
        this.jobId = jobId;
        this.startId = startId;
        this.endId = endId;
        this.startTime = startTime;
    }


    @Override
    public void run() {
        // to-do biz 循环执行业务直到重点endId
        File file = new File("E:/file/".concat(jobId).concat(".txt"));
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = startId; i <= endId; i++) {
            try {
                bw.write(jobId.concat(" : ").concat(i + "").concat("\n"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();
        try {
            bw.write(jobId.concat("线程耗时：").concat((endTime - startTime) + "毫秒"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bw.flush();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(jobId.concat("线程耗时：").concat((endTime - startTime) + "毫秒"));
//         通知分配器，业务执行完成

        AssignThread.pushTriggerQueue(this);

    }


    public void toStop() {
        toStop = true;
    }

}
