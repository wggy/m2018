package sw.melody.algorithm.leetcode1.array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/***
 * Created by ping on 2018/4/16
 * 给定两个数组，写一个方法来计算它们的交集。

 例如:
 给定 nums1 = [1, 2, 2, 1], nums2 = [2, 2], 返回 [2, 2].

 注意：

 输出结果中每个元素出现的次数，应与元素在两个数组中出现的次数一致。
 我们可以不考虑输出结果的顺序。
 跟进:

 如果给定的数组已经排好序呢？你将如何优化你的算法？
 如果 nums1 的大小比 nums2 小很多，哪种方法更优？
 如果nums2的元素存储在磁盘上，内存是有限的，你不能一次加载所有的元素到内存中，你该怎么办？
 */
public class IntersectArray {

    public static void main(String[] args) {
        int[] nums1 = {3, 4, 6, 3};
        int[] nums2 = {2, 5, 7, 3, 4, 4, 4};
        intersect(nums1, nums2);
    }

    private static int[] intersect(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0) {
            return new int[0];
        }

        List<Integer> list = new ArrayList<>();
        for (int i1=0; i1<nums1.length; i1++) {
            for (int i2=0; i2<nums2.length; i2++) {
                if (nums1[i1] == nums2[i2]) {
                    list.add(nums1[i1]);
                    break;
                }
            }
        }
        List<Integer> nums2List = new ArrayList<>();
        for (int i=0; i<nums2.length; i++) {
            nums2List.add(nums2[i]);
        }
        List<Integer> result = new ArrayList<>();
        for (Integer n : list) {
            if (nums2List.contains(n)) {
                result.add(n);
                nums2List.remove(n);
            }
        }
        if (result.size() == 0) {
            return new int[0];
        }
        int[] r = new int[result.size()];
        int index = 0;
        for (Integer re : result) {
            r[index++] = re;
        }
        return r;
    }
}
