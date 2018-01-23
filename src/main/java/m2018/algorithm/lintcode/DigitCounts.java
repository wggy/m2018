package m2018.algorithm.lintcode;

/***
 * 计算数字k在0到n中的出现的次数，k可能是0~9的一个值
 *
 * 例如n=12，k=1，在 [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]，我们发现1出现了5次 (1, 10, 11, 12)
 * 我的解法
 * 1.遍历n，将整形转换成字符串，通过indexOf判断k的包含关系，通过substring移动，判断每个数字包含几个k
 *
 */
public class DigitCounts {
    public static void main(String[] args) {
        System.out.println(new DigitCounts().digitCounts(1, 12));
//        System.out.println("21".indexOf("1"));
    }

    /*
     * @param : An integer
     * @param : An integer
     * @return: An integer denote the count of digit k in 1..n
     */
    public int digitCounts(int k, int n) {
        // write your code here
        int count = 0;
        String sk = String.valueOf(k);
        for (int i = 0; i <= n; i++) {
            String si = String.valueOf(i);
            int index = -1;
            int length = si.length();
            while ((index = si.indexOf(sk)) > -1 && index < length) {
                si = si.substring(index + 1);
                count++;
            }
        }
        return count;
    }

}
