package sw.melody.algorithm.sort;

/**
 * @author ping
 * @create 2019-01-23 15:53
 * <p>
 * 希尔排序：
 * 是直接插入排序的升级版，又被称为缩小增量排序
 * 具体步骤如下：
 * 1.将n个元素的数组，分成n/2组，每组包含两个元素，对两个元素进行直接插入排序，实际上就是交换数字，针对插入排序的优化，先将小的数字交换到前排来
 * 2.然后再将数组分成n/2/2组，每组包含4个元素，然后插入排序
 * 3.以此类推，直到分成1组，再执行一次插入排序，执毕。
 **/

public class ShellSort {
    public static void main(String[] args) {
        int[] arr = {100, 9, 3, 20, 1, 7, 2, 11, -5, 33};
        sort(arr);
        for (int a : arr) {
            System.out.print(a + ", ");
        }
    }

    static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        int len = arr.length;
        for (int step = len / 2; step >= 1; step /= 2) {
            for (int i = step; i < len; i++) {
                int temp = arr[i];
                int j = i - step;
                for (; j >= 0; j -= step) {
                    if (temp < arr[j]) {
                        arr[j + step] = arr[j];
                    } else {
                        break;
                    }
                }
                arr[j + step] = temp;
            }
        }
    }
}
