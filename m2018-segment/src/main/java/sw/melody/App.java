package sw.melody;

import lombok.extern.slf4j.Slf4j;

/**
 * Hello world!
 * <p>
 * …or create a new repository on the command line
 * <p>
 * git提交方式
 * <p>
 * 1.从本地仓库提交
 * 创建本地仓库
 * git init
 * 提交本地分支到暂存区
 * git add README.md
 * 提交本地分支
 * git commit -m "first commit"
 * 本地分支关联github远程仓库
 * git remote add origin https://github.com/year201412/m2018.git
 * 提交到github仓库
 * git push -u origin master
 * <p>
 * 2.从本地仓库提交到已知github仓库
 * …or push an existing repository from the command line
 * <p>
 * 关联githu远程仓库
 * git remote add origin https://github.com/year201412/m2018.git
 * 提交到github仓库
 * git push -u origin master
 */
@Slf4j
public class App {
    public static void main(String[] args) {
        findPairs();
    }

    static void findPair() {
        int max = 999, init = 3;
        for (int i = init; i < max; i++) {
            for (int j = init; i < max; j++) {
                if (isPrime(i) && isPrime(j) && i * j < max && i * j > 100) {
                    log.info("第一个质数：{}， 第二个质数：{}", i, j);
                }
            }
        }
    }

    static void findPairs() {
        long val = 7140229933L;
        for (long i = 2; i < val; i++) {
            if (isPrime(i) && val % i == 0) {
                long m = val / i;
                if (isPrime(m)) {
                    log.info("第一个质数：{}， 第二个质数：{}", i, m);
                    break;
                }
            }
        }
    }

    static boolean isPrime(long num) {
        boolean isPrime = true;
        long len = num / 2;
        for (int i = 2; i < len; i++) {
            if (num % i == 0) {
                isPrime = false;
                break;
            }
        }
        return isPrime;
    }
}
