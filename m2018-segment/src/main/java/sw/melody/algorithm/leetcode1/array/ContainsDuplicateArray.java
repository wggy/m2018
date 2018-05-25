package sw.melody.algorithm.leetcode1.array;

import java.util.HashMap;
import java.util.Map;

/***
 * Created by ping on 2018/4/16
 *
 * 给定一个整数数组，判断是否存在重复元素。
 * 如果任何值在数组中出现至少两次，函数应该返回 true。如果每个元素都不相同，则返回 false。
 */
public class ContainsDuplicateArray {

    public static void main(String[] args) {
        int[] nums = {3, 3};
        mapDuplicate(nums);
    }

    private static boolean mapDuplicate(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i=0; i<nums.length; i++) {
            if (map.containsValue(nums[i])) {
                return true;
            }
            map.put(i, nums[i]);
        }
        return false;
    }
    public static boolean containsDuplicate(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        int len = nums.length;
        for (int i=0; i < len-1; i++) {
            for (int j=i+1; j < len; j++) {
                if (nums[i] == nums[j]) {
                    return true;
                }
            }
        }
        return false;
    }
}
