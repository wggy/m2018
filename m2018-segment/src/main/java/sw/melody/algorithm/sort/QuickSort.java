package sw.melody.algorithm.sort;

/**
 * @author ping
 * @create 2019-01-28 14:44
 * 快速排序：
 * 选择一个基准元素，把小于该元素的放到元素前面，大约该元素的放到后面
 * 这样数组被一分为而二，再分别对两个子数组进行上述操作
 * 直到子数组只有一个元素为止
 **/

public class QuickSort {
    public static void main(String[] args) {
        int[] arr = {9, 5, 12, 40, 30, 8};
        quickSort(arr, 0, arr.length - 1);

        for (int i : arr) {
            System.out.print(i + "   ");
        }
    }

    static void quickSort(int[] u, int start, int end) {
        if (u == null || u.length < 2) {
            return;
        }

        int i = start;
        int j = end;
        int base = u[start];
        while (i <= j) {
            while (i < end && u[i] < base) {
                i++;
            }
            while (j > start && u[j] > base) {
                j--;
            }
            if (i <= j) {
                int temp = u[i];
                u[i] = u[j];
                u[j] = temp;
                i++;
                j--;
            }
        }
        if (start < j) {
            quickSort(u, start, j);
        }
        if (end > i) {
            quickSort(u, i, end);
        }
    }
}
