package sw.melody.algorithm.classic;


import java.util.Arrays;

/***
 * 快速排序
 *
 * 是冒泡排序的升级版。
 * 基本思想：
 * 1.选取基准元素，一般默认为第一个数组元素
 * 2.分别从数组其实位置、结束位置与基准元素比对，小于基准元素放左边，大于基准元素放右边
 *
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] a = {1, 2, 4, 5, 7, 4, 5 ,3 ,9 ,0};
        System.out.println(Arrays.toString(a));
        quickSort(a, 0, a.length-1);
        System.out.println(Arrays.toString(a));
    }

    private static void quickSort(int[] nums, int low, int high) {
        if (low > high) {
            return;
        }
        int key = nums[low];
        int leftIndex = low;
        int rightIndex = high;
        while (leftIndex < rightIndex) {
            while (leftIndex < rightIndex && nums[rightIndex] > key) {
                rightIndex--;
            }
            while (leftIndex < rightIndex && nums[leftIndex] <= key) {
                leftIndex++;
            }

            if (leftIndex < rightIndex) {
                int temp = nums[leftIndex];
                nums[leftIndex] = nums[rightIndex];
                nums[rightIndex] = temp;
            }
        }
        int temp = nums[leftIndex];
        nums[leftIndex] = nums[low];
        nums[low] = temp;

        quickSort(nums, low, leftIndex-1);
        quickSort(nums, leftIndex+1, high);
    }
}
