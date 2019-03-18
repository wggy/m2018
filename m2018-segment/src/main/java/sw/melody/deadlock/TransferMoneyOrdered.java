package sw.melody.deadlock;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ping
 * @create 2019-02-15 11:05
 **/
@Slf4j
public class TransferMoneyOrdered {

    private static Object commObj = new Object();

    public static void transfer(Account from, Account to, int amount) throws Exception {
        int fromHash = System.identityHashCode(from);
        int toHash = System.identityHashCode(to);
        if (fromHash > toHash) {
            synchronized (from) {
                log.info("线程: 【{}】获取【{}】账户锁成功...", Thread.currentThread().getName(), from.name);
                synchronized (to) {
                    log.info("线程: 【{}】获取【{}】账户锁成功...", Thread.currentThread().getName(), to.name);
                    new Helper().transfer(from, to, amount);
                }
            }
        } else if (fromHash < toHash) {
            synchronized (to) {
                log.info("线程【{}】获取【{}】账户锁成功...", Thread.currentThread().getName(), to.name);
                synchronized (from) {
                    log.info("线程【{}】获取【{}】账户锁成功...", Thread.currentThread().getName(), from.name);
                    new Helper().transfer(from, to, amount);
                }
            }
        } else {
            synchronized (commObj) {
                synchronized (from) {
                    log.info("线程 【{}】获取【{}】账户锁成功...", Thread.currentThread().getName(), from.name);
                    synchronized (to) {
                        log.info("线程【{}】获取【{}】账户锁成功...", Thread.currentThread().getName(), to.name);
                        new Helper().transfer(from, to, amount);
                    }
                }
            }
        }
    }

    private static class Helper {
        public void transfer(Account from, Account to, int amount) throws Exception {
            if (from.balance < amount) {
                throw new Exception("余额不足");
            } else {
                from.debit(amount);
                to.credit(amount);
                log.info("线程:【{}】从【{}】账户转账到【{}】账户【{}】元钱成功", Thread.currentThread().getName(), from.name, to.name, amount);
            }
        }
    }
    private static class Account {
        String name;
        int balance;

        public Account(String name, int balance) {
            this.name = name;
            this.balance = balance;
        }
        void debit(int amount) {
            this.balance = balance - amount;
        }
        void credit(int amount) {
            this.balance = balance + amount;
        }
    }

    public static void main(String[] args) {
        Account A = new Account("A", 100);
        Account B = new Account("B", 200);
        Thread t1 = new Thread(() -> {
            for (int i=0; i<100; i++) {
                try {
                    transfer(A, B, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t1.setName("T1");

        Thread t2 = new Thread(() -> {
            for (int i=0; i<100; i++) {
                try {
                    transfer(B, A, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t2.setName("T2");

        t1.start();
        t2.start();
    }
}
