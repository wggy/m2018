package sw.melody.algorithm.leetcode1.array;

/***
 * Created by ping on 2018/5/24
 *
 *
 * 给定一个非负整数组成的非空数组，在该数的基础上加一，返回一个新的数组。
 * 最高位数字存放在数组的首位， 数组中每个元素只存储一个数字。
 * 你可以假设除了整数 0 之外，这个整数不会以零开头。
 *
 * 示例 1:
 *
 * 输入: [1,2,3]
 * 输出: [1,2,4]
 * 解释: 输入数组表示数字 123。
 */
public class ArrayPlusOne {


    public static void main(String[] args) {
        int[] ints = {9, 9, 9};
        int[] rr = plusOne2(ints);
        for (int i = 0; i < rr.length; i++) {
            System.out.print(rr[i]);
        }
        System.out.println();
    }

    static int[] plusOne(int[] digits) {
        int result = 0;
        int length = digits.length;
        if (digits[length - 1] < 9) {
            digits[length - 1] += 1;
            return digits;
        }
        for (int i = 0; i < length; i++) {
            int n = 1;
            for (int j = 0; j < length - i - 1; j++) {
                n *= 10;
            }
            result += n * digits[i];
        }
        result += 1;
        int rCopy = result;
        int nCnt = 1;
        while (rCopy / 10 > 0) {
            nCnt++;
            rCopy /= 10;
        }
        int[] rr = new int[nCnt];
        for (int i = 0; i < nCnt; i++) {
            int n = 1;
            for (int j = 0; j < nCnt - i - 1; j++) {
                n *= 10;
            }
            rr[i] = result / n;
            result %= n;
        }
        return rr;
    }

    static int[] plusOne2(int[] digits) {
        if (digits == null || digits.length == 0) {
            return new int[0];
        }
        int length = digits.length;
        digits[length - 1] += 1;
        for (int i=length-1; i>0; i--) {
            if (digits[i] == 10) {
                digits[i] = 0;
                digits[i-1] += 1;
            } else {
                break;
            }
        }
        if (digits[0] == 10) {
            int[] array = new int[length + 1];
            array[0] = 1;
            array[1] = 0;
            for (int i=1; i < length; i++) {
                array[i+1] = digits[i];
            }
            return array;
        }
        return digits;
    }
}
