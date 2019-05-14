package sw.melody.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * @author ping
 * @create 2019-03-20 15:44
 **/
@Slf4j
public class RecursiveActionTest {

    private static final int C_SIZE = 100;
    public static void main(String[] args) {
        int[] array = new int[C_SIZE];
        for (int i=0; i<C_SIZE; i++) {
            array[i] = (int) (Math.random() * 100);
        }
        Sorter.sort(array);
        System.out.println(Arrays.toString(array));
    }


    static class Sorter extends RecursiveAction {

        private int[] array;
        private int lo;
        private int hi;

        private static final int THRESHOLD = 10;

        private Sorter(int[] array, int lo, int hi) {
            this.array = array;
            this.lo = lo;
            this.hi = hi;
        }

        public static void sort(int[] array) {
            ForkJoinPool.commonPool().invoke(new Sorter(array, 0, array.length));
        }

        @Override
        protected void compute() {
            if (hi - lo < THRESHOLD) {
                Arrays.sort(array, lo, hi);
            } else {
                int mid = (hi + lo) >>> 1;
                Sorter left = new Sorter(array, lo, mid);
                Sorter right = new Sorter(array, mid, hi);
                invokeAll(left, right);
                merge(lo, mid, hi);
            }
        }

        private void merge(int lo, int mid, int hi) {
            int[] buf = Arrays.copyOfRange(array, lo, mid);
            for (int i = 0, j = lo, k = mid; i < buf.length; j++) {
                if (k == hi || buf[i] < array[k]) {
                    array[j] = buf[i++];
                } else {
                    array[j] = array[k++];
                }
            }
        }

    }

}
