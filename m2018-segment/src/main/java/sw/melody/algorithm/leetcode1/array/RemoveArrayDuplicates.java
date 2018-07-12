package sw.melody.algorithm.leetcode1.array;

/***
 * Created by ping on 2018/4/13
 *
 * 给定一个排序数组，你需要在原地删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
 * 不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。
 * 示例：
 * 给定数组 nums = [1,1,2],
 * 函数应该返回新的长度 2, 并且原数组 nums 的前两个元素必须是 1 和 2。
 * 你不需要考虑数组中超出新长度后面的元素。
 */
public class RemoveArrayDuplicates {

    public static void main(String[] args) {
        int[] nums = {0, 1, 2, 2, 3, 3};
        System.out.println(removeDuplicates(nums));
    }


    /***
     * 我的解法
     * @param nums
     * @return
     */
    public static int myRemoveDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int length = nums.length;
        int size = 0;
        for (int i=0; i<length-1; ) {
            if (allDuplicates(nums, i)) {
                size = i+1;
                break;
            }
            if (nums[i] == nums[i+1]) {
                for (int j=i+1; j<length-1; j++) {
                    nums[j] = nums[j+1];
                }
            } else {
                i++;
            }
        }
        return size;
    }

    private static boolean allDuplicates(int[] nums, int index) {
        int init=nums[index];
        for (int i=index+1; i<nums.length; i++) {
            if (init != nums[i]) {
                return false;
            }
        }
        return true;
    }


    /***
     * 标准解法
     * @param nums
     * @return
     */
    public static int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int size = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[size] != nums[i]) {
                nums[++size] = nums[i];
            }
        }
        return ++size;
    }
}
