package sw.melody.recursion;

/***
 *  create by wggy on 2019/6/17 0:05 
 **/
public class Fibonacci {

    public static void main(String[] args) {
        long st = System.currentTimeMillis();
        int ret = recursion(40);
        long en = System.currentTimeMillis();
        System.out.println(String.format("ret=%d, time=%d", ret, (en-st)));
        st = System.currentTimeMillis();
        ret = loop(40);
        en = System.currentTimeMillis();
        System.out.println(String.format("ret=%d, time=%d", ret, (en-st)));
    }

    static int recursion(int num) {
        if (num <= 0)
            return 0;
        if (num == 1 || num == 2)
            return 1;
        return recursion(num - 1) + recursion(num - 2);
    }

    static int loop(int num) {
        if (num <= 0)
            return 0;
        if (num == 1 || num == 2)
            return 1;
        int first = 1, second = 1;
        int next = 0;
        for (int i=3; i<=num; i++) {
            next = first + second;
            second = first;
            first = next;
        }
        return next;
    }
}
