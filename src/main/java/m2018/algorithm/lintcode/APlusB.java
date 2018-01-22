package m2018.algorithm.lintcode;

/**
 * Created by slow on 2018/1/22 0022.
 *
 * 给出两个整数a和b, 求他们的和, 但不能使用 + 等数学运算符。
 *
 * 1.此题解法依据加法的特性
 * a）不考虑进位的情况，如：5+2，即 101 ^ 010 = 111，就是答案
 * b）考虑进位的情况，如：5+3，即 101 ^ 011 = 110;
 *    再单独算进位，101 & 011 << 1 = 001 << 1 = 010
 *
 *    第二次计算：
 *          110 ^ 010 = 100
 *          110 & 010 << 1 = 010 << 1 = 100
 *    第三次计算：
 *          100 ^ 100 = 000
 *          100 & 100 >> 1 = 1000
 *    第四次计算：
 *          0000 ^ 1000 = 10000
 *          0000 & 1000 = 00000
 *    结束
 *
 *    总结：按位异或代表求和操作，按位与、然后左移一位代表进位操作，
 *    因为进位可能出现在任何位置，因此可迭代进位标记，知道没有进位为止，则按位异或就是求和值
 *
 */
public class APlusB {

    public static void main(String[] args) {
        System.out.println(new APlusB().aplusb(10, 9));
    }

    /*
     * @param : An integer
     * @param : An integer
     * @return: The sum of a and b
     */
    public int aplusb(int a, int b) {
        if (b == 0) {
            return a;
        }
        int orElse = a ^ b;
        int and = a & b;

        return aplusb(orElse, and<<1);
    }
}
