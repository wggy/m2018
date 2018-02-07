package sw.melody.algorithm.lintcode;

/***
 * 在数组中找到第k大的元素
 *
 * 给出数组 [9,3,2,4,8]，第三大的元素是 4
 * 我的解法：
 * 利用升序的k长度数组遍历大数组，把比子数组kArrray[0]大的数保存进去，并挤掉第一个数字
 * 此方法的时间复杂度：O(k^2 + (n - k) * k) = O(kn)，空间复杂度：O(k)
 *
 * 挑战
 要求时间复杂度为O(n)，空间复杂度为O(1)
 */
public class KthLargestElement {

    public static void main(String[] args) {
        int k = 3;
        int[] nums = new int[]{9, 3, 2, 4, 8};
//        System.out.println(new KthLargestElement().kthLargestElement(k, nums));
        new KthLargestElement().quit(0, nums.length - 1, nums);
        for (int n : nums) {
            System.out.print(n + ", ");
        }
    }

    public int kthLargestElement(int k, int[] nums) {
        int length = nums.length;
        if (k < 1 || k > length) {
            throw new IllegalArgumentException("arg is illegal");
        }
        int[] kNum = new int[k];
        System.arraycopy(nums, 0, kNum, 0, k);
        for (int i = 0; i < k-1; i++) {
            for (int j=1; j < k; j++) {
                if (kNum[i] > kNum[j]) {
                    int temp = kNum[i];
                    kNum[i] = kNum[j];
                    kNum[j] = temp;
                }
            }
        }
        for (int i = k; i < nums.length; i++) {
            if (kNum[0] < nums[i]) {
                join(nums[i], kNum);
            }
        }

        return kNum[0];
    }

    private void join(int m, int[] kNum) {
        int location = -1;
        for (int i=0; i<kNum.length; i++) {
            if (m <= kNum[i]) {
                location = i;
            }
        }
        if (location > 0) {
            for (int i=1; i<=location-1; i++) {
                kNum[i-1] = kNum[i];
            }
            kNum[location - 1] = m;
        }
    }

    private void quit(int start, int end, int[] num) {
        if (start >= end) {
            return;
        }
        int left = start;
        int right = end;
        int x = num[start];

        while (true) {
            while (x < num[right--]) {

            }
            while (left < right && x > num[left++]) {

            }

            if (left < right) {
                int temp = num[left];
                num[left] = num[right];
                num[right] = temp;
            } else {
                break;
            }
        }
        quit(start, left -1, num);
        quit(left + 1, end, num);
    }

}
