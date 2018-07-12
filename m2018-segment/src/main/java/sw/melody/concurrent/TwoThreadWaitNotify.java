package sw.melody.concurrent;

/***
 * Created by ping on 2018-7-9
 *
 * 两个线程通过对同一对象调用等待 wait() 和通知 notify() 方法来进行通讯。
 * 如两个线程交替打印奇偶数：
 */
public class TwoThreadWaitNotify {
    private int number = 0;
    private boolean flag = false;

    public static void main(String[] args) {
//        TwoThreadWaitNotify notify = new TwoThreadWaitNotify();
//        Thread jiThread = new Thread(new JiNum(notify));
//        jiThread.setName("A");
//        Thread ouThread = new Thread(new OuNum(notify));
//        ouThread.setName("B");
//        jiThread.start();
//        ouThread.start();

        try {
            join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void join() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println("running");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }) ;
        Thread t2 = new Thread(() -> {
            System.out.println("running2");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }) ;

        t1.start();
        t2.start();

        //等待线程1终止
        t1.join();

        //等待线程2终止
        t2.join();

        System.out.println("main over");
    }

    private static class JiNum implements Runnable {
        TwoThreadWaitNotify lockObj;

        public JiNum(TwoThreadWaitNotify lockObj) {
            this.lockObj = lockObj;
        }

        @Override
        public void run() {
            while (lockObj.number <= 100) {
                synchronized (lockObj) {

                    if (lockObj.flag) {
                        System.out.print(Thread.currentThread().getName() + "线程持有锁, ");
                        System.out.println("JiNum = " + (++lockObj.number));
                        lockObj.flag = false;
                        lockObj.notify();
                    } else {
                        try {
                            lockObj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private static class OuNum implements Runnable {
        TwoThreadWaitNotify lockObj;

        public OuNum(TwoThreadWaitNotify lockObj) {
            this.lockObj = lockObj;
        }
        @Override
        public void run() {
            while (lockObj.number <= 100) {
                synchronized (lockObj) {
                    if (!lockObj.flag) {
                        System.out.print(Thread.currentThread().getName() + "线程持有锁, ");
                        System.out.println("OuNum = " + (++lockObj.number));
                        lockObj.flag = true;
                        lockObj.notify();
                    } else {
                        try {
                            lockObj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

}
