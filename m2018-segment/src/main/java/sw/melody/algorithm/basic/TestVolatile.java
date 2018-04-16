package sw.melody.algorithm.basic;

/***
 * Created by ping on 2018/3/30
 * volatile关键字的使用、线程interrupt方法的特性
 * 1、线程运行环境所需的变量在线程栈中有一份来自主存的拷贝；
 * 2、其他线程调用线全局变量涉及同步问题；
 * 3、共享变量flag中断线程，外部线程设置中断信号，实际上设置完成以后同步到主存，当前线程的flag并没有改变，导致线程无法中断；
 * 4、interrupt中断方法：
 * 4.1 interrupt方法作用不是中断线程，而是通知线程你要中断执行了；
 * 4.2 如果线程处于被阻塞状态（sleep、wait、join等），线程立刻退出等待状态，并抛出Interrupted异常，仅此而已；
 * 4.3 如果线程处于正常活动状态，会将改线程的中断标志设置为true，仅此而已。被设置中断标记的线程将继续执行，不受影响。
 *
 */
public class TestVolatile extends Thread {
    boolean flag = true;
    int count = 0;

    @Override
    public void run() {
        while (flag) {
            System.out.println(count++);
        }
    }

    public void toStop() {
        this.flag = false;
    }

    public static void main(String[] args) {
        TestVolatile testVolatile = new TestVolatile();
        testVolatile.start();
        try {
            Thread.sleep(2000);
            testVolatile.toStop();
//            testVolatile.interrupt();
            System.out.println("count = " + testVolatile.count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
