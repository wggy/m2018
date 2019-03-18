package sw.melody.algorithm.sort;

/**
 * @author ping
 * @create 2019-01-28 11:15
 * 冒泡排序：
 **/

public class BubbleSort {
    public static void main(String[] args) {
        int[] arr = {9, 5, 12, 40, 30, 8};
        bubbleSort(arr);

        for (int i : arr) {
            System.out.print(i + "   ");
        }
    }

    static void noBubbleSort(int[] u) {
        if (u == null || u.length < 2) {
            return;
        }
        int len = u.length;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (u[i] > u[j]) {
                    int temp = u[i];
                    u[i] = u[j];
                    u[j] = temp;
                }
            }
        }
    }

    static void bubbleSort(int[] u) {
        if (u == null || u.length < 2) {
            return;
        }
        int len = u.length;
        for (int i = 0; i < len; i++) {
            for (int j = len-1; j > i; j--) {
                if (u[j-1] > u[j]) {
                    int temp = u[j-1];
                    u[j-1] = u[j];
                    u[j] = temp;
                }
            }
        }
    }


}
