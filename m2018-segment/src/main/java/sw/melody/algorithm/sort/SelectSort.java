package sw.melody.algorithm.sort;

/**
 * @author ping
 * @create 2019-01-23 17:11
 * 直接选择排序：
 * 1.找出最小的元素与第一个元素交换；
 * 2.找出第一个以后的最小的元素与第二个元素交换；
 * 3.以此类推，直到数组末尾，排序完成
 **/

public class SelectSort {
    public static void main(String[] args) {
        int[] arr = {100,9, 3, 20, 1, 7, 2, 11, -5, 33};
        sort(arr);
        for (int a : arr) {
            System.out.print(a + ", ");
        }
    }

    static void sort(int[] u) {
        if (u == null || u.length <= 1) {
            return;
        }
        int len = u.length;
        for (int i = 0; i < len - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < len; j++) {
                if (u[minIdx] > u[j]) {
                    minIdx = j;
                }
            }
            if (i != minIdx) {
                int temp = u[minIdx];
                u[minIdx] = u[i];
                u[i] = temp;
            }
        }
    }
}
