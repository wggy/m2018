package sw.melody.algorithm.basic;

/***
 * Created by ping on 2018/3/29
 *  测试java同步关键字synchronized
 * 1.修饰普通方法，锁是当前对象；
 * 2.修饰静态防范，锁是当前类的class对象；
 * 3.修饰代码块；锁是括号里的对象
 *
 */
public class TestSynchronized {

    public synchronized void test1() {
        System.out.println(1);
    }

    public static synchronized void test2() {
        System.out.println(2);
    }

    public void test3() {
        synchronized (this) {
            System.out.println(3);
        }
    }

    public static void main(String[] args) {

    }

}
