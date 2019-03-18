package sw.melody.algorithm.sort;

/**
 * @author ping
 * @create 2019-01-30 17:46
 **/

public class CountSort {
    private static int[] countSort(int[] array, int k) {
        int[] C = new int[k + 1];
        int length = array.length, sum = 0;
        int[] B = new int[length];
        for (int i = 0; i < length; i++) {
            C[array[i]] += 1;
        }
        for (int i = 0; i < k + 1; i++) {
            sum += C[i];
            C[i] = sum;
        }
        for (int i = length - 1; i >= 0; i--) {

            B[C[array[i]] - 1] = array[i];
            C[array[i]]--;

        }
        return B;
    }

    public static void main(String[] args) {
        int[] A = new int[]{2, 5, 3, 0, 2, 3, 0, 3};
        int[] B = countSort(A, 5);
        for (int i = 0; i < A.length; i++) {
            System.out.println((i + 1) + "th:" + B[i]);
        }
    }
}
