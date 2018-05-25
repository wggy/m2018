package sw.melody.algorithm.leetcode1.array;

/***
 * Created by ping on 2018/4/13
 */
public class RotateArrayLeft {

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5, 6, 7};
        resultSolution(nums, 3);
        System.out.println(1);
    }

    /***
     * 1. 暴力求解，数组变大，时间复杂度高，执行超时，无法通过。
     */
    private static void forceSolution(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return;
        }
        int len = nums.length;
        for (int step = 1; step <= k; step++) {
            int init = nums[len - 1];
            for (int i = len -2; i >=0; i--) {
                nums[i+1] = nums[i];
            }
            nums[0] = init;
        }
    }

    private static void resultSolution(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return;
        }
        int len = nums.length;
        if (len <= k) {
            k = k % len;
        }

        int[] kArray = new int[k];
        int[] sArray = new int[len - k];
        System.arraycopy(nums, len - k, kArray, 0, k);
        System.arraycopy(nums, 0, sArray, 0, len - k);
        System.arraycopy(kArray, 0, nums, 0, k);
        System.arraycopy(sArray, 0, nums, k, len - k);
    }
}
