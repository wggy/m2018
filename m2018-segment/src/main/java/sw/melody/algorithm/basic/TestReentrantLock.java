package sw.melody.algorithm.basic;

/***
 * Created by ping on 2018/4/4
 * 可重入锁
 * ReenTrantLock显示锁功能同synchronized，但还有额外的功能
 * 1.可重入锁被最后一个成功获取的线程持有，且没有调用unlock释放；
 * 2.构造方法，传入布尔值，代表线程同步是否公平竞争，若true，优先等待最长的线程，否则，无法保证线程的获取锁的顺序；
 * 3.传入fairness为true，表示公平锁，倾向于被最长等待的线程持有，但是公平锁并不保证每个线程依次按计划执行，
 *   也可能一个线程多次获取锁，而其他线程并没有获取到锁。劣势在于，程序的吞吐低于非公平锁；
 *
 * ReentrantLock --> AbstractQueuedSynchronizer --> AbstractOwnableSynchronizer
 * AbstractQueuedSynchronizer:
 * 1.依据FIFO队列实现阻塞锁、同步器的基本框架，此类通过state状态值实现同步，是这类同步器的基本原理，可作为这类同步器的基类；
 * 子类调用setState改变state的状态值，state的值意味着object被成功占有或成功释放
 * 2.其他方法执行队列、阻塞操作
 * 3.
 */
public class TestReentrantLock {
}
