package sw.melody.algorithm.sort;


/**
 * @author ping
 * @create 2019-01-30 9:48
 * 归并排序：
 * 1.从头到尾按顺序两两排序
 * 2.从头到尾按顺序对两个已排序好的子数组排序
 * 3.直到最后一对已排序的数组排序完成，执毕。
 **/

public class MergeSort {

    public static void main(String[] args) {
        int[] arr = {100, 9, 3, 20, 1, 7, 2, 11, -5, 33};
        mergeSort(arr);
        for (int a : arr) {
            System.out.print(a + ", ");
        }
    }

    static void mergeSort(int[] u) {
        int len = u.length;
        int left = 0;
        int right = len - 1;
        int k = 1;
        while (k <= right) {
            int s = k;
            k = 2 * s;
            int i = left;
            while (i + k - 1 <= right) {
                merge(u, i, i + s - 1, i + (k - 1));
                i = i + k;
            }
            if (i + s - 1 <= right) {
                merge(u, i, i + s - 1, right);
            }
        }
    }

    static void merge(int[] u, int left, int mid, int right) {
        int len = u.length;
        int[] cpy = new int[len];
        int pl = left;
        int pr = mid + 1;
        int pc = left;
        while (pl <= mid && pr <= right) {
            if (u[pl] <= u[pr]) {
                cpy[pc++] = u[pl++];
            } else {
                cpy[pc++] = u[pr++];
            }
        }
        while (pl <= mid) {
            cpy[pc++] = u[pl++];
        }
        while (pr <= right) {
            cpy[pc++] = u[pr++];
        }
        for (int i = left; i <= right; i++) {
            u[i] = cpy[i];
        }
    }

}
