package sw.melody.thread;

/**
 * @author ping
 * @create 2018-11-08 10:53
 **/

public class NoVisibility {
    private static boolean isStop = false;
    private static long num = 0;

    public static void main(String[] args) {
        ReaderThread readerThread = new ReaderThread();
        readerThread.start();
        new WriterThread().start();
    }

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            while (!isStop) {
                Thread.yield();
            }
            System.out.println(num);
        }

    }

    private static class WriterThread extends Thread {
        @Override
        public void run() {
            isStop = true;
            num = 1111111111111L;
        }
    }
}
