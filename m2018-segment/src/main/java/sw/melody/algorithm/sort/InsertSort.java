package sw.melody.algorithm.sort;

/**
 * @author ping
 * @create 2019-01-23 10:19
 *
 * 直接插入排序
 * 具体流程如下：
 * 1.先比较数组的前两个元素，然后排序；
 * 2.再比较第三个元素，放在前面合适的位置，以此类推，知道最后一个元素
 *
 **/

public class InsertSort {

    public static void main(String[] args) {
        int[] arr = {100,9, 3, 20, 1, 7, 2, 11, -5, 33};
        insertSort(arr);
        for (int a : arr) {
            System.out.print(a + ", ");
        }
    }


    static void insertSort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        for (int i=1; i<array.length; i++) {
            int temp = array[i];
            int j=i-1;
            for (; j>=0; j--) {
                if (temp < array[j]) {
                    array[j+1] = array[j];
                } else {
                    break;
                }
            }
            array[j + 1] = temp;
        }
    }

}
