package sw.melody.algorithm.sort;

/**
 * @author ping
 * @create 2019-01-24 11:01
 * <p>
 * 堆排序：
 * 1.构建大顶堆，首先把最大的元素交换到数组头部
 * 2.再把数组收尾交换，最大元素在最后一个位置，从倒二位置构建到数组头部计算最大元素，放在头部
 * 3.重复执行第二步，直到计算到头部元素，执毕。
 **/

public class HeapSort {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        heapSort(arr);

        for (int i : arr) {
            System.out.print(i + "   ");
        }

    }


    static void putHeapTop(int[] arr, int i, int len) {
        int max = arr[i];
        for (int j = 2 * i + 1; j <= len-1; j = 2 * j + 1) {
            if (j < len -1 && arr[j] < arr[j + 1]) {
                j = j + 1;
            }
            if (arr[j] > max) {
                arr[i] = arr[j];
                i = j;
            } else {
                break;
            }
        }
        arr[i] = max;
    }

    static void heapSort(int[] arr) {
        int len = arr.length;

        for (int i = (len - 1) / 2; i >= 0; i--) {
            putHeapTop(arr, i, len);
        }
        for (int i = len - 1; i > 0; i--) {
            int temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;
            putHeapTop(arr, 0, i);
        }
    }

    public int[] mergeSortedArray(int[] A, int[] B) {
        // write your code here
        int[] arr = new int[A.length + B.length];
        int pa=0;
        int pb=0;
        int i=0;

        while (pa <= A.length-1 && pb <= B.length-1) {
            if (A[pa] <= B[pb]) {
                arr[i++] = A[pa++];
            } else {
                arr[i++] = B[pb++];
            }
        }
        if (pa <= A.length-1) {
            arr[i++] = A[pa++];
        }
        if (pb <= B.length-1) {
            arr[i++] = B[pb++];
        }
        return arr;

    }

    static int kthLargestElement(int[] nums) {
        // write your code here
        if (nums == null) {
            return -1;
        }
        int len = nums.length;
//        if (n > len) {
//            return -1;
//        }
        for (int i=(len - 1)/2; i>=0; i--) {
            putMaxTop(nums, i, len);
        }
        for (int i=len-1; i>=(len-len); i--) {
            int temp = nums[i];
            nums[i] = nums[0];
            nums[0] = temp;
            putMaxTop(nums, 0, i-1);
        }
        return nums[nums.length-len];
    }


    static void putMaxTop(int[] u, int idx, int len) {
        int max = u[idx];
        for (int m=2*idx+1; m<=len-1; m=2*m+1) {
            if (m < len-1 && u[m] < u[m+1]) {
                m = m + 1;
            }
            if (max >= u[m]) {
                break;
            } else {
                u[idx] = u[m];
                idx = m;
            }
        }
        u[idx] = max;
    }




//    public static void main(String[] args) {
//        //定义整型数组
//        int[] arr = {1, 2, 3, 4, 5, 6, 8, 9, 10, 7};
//        //调用堆排序数组
//        HeapSort(arr);
//        //输出排序后的数组
//        for (int i = 0; i < arr.length; i++) {
//            System.out.print(arr[i] + "  ");
//        }
//    }
//
//    //堆排序函数
//    public static void HeapSort(int[] arr) {
//        int n = arr.length - 1;
//        for (int i = (n - 1) / 2; i >= 0; i--) {
//            //构造大顶堆，从下往上构造
//            //i为最后一个根节点，n为数组最后一个元素的下标
//            HeapAdjust(arr, i, n);
//        }
//        for (int i = n; i > 0; i--) {
//            //把最大的数，也就是顶放到最后
//            //i每次减一，因为要放的位置每次都不是固定的
//            swap(arr, i);
//            //再构造大顶堆
//            HeapAdjust(arr, 0, i - 1);
//        }
//    }
//;
//    //构造大顶堆函数，parent为父节点，length为数组最后一个元素的下标
//    public static void HeapAdjust(int[] arr, int parent, int length) {
//        //定义临时变量存储父节点中的数据，防止被覆盖
//        int temp = arr[parent];
//        //2*parent+1是其左孩子节点
//        for (int i = parent * 2 + 1; i <= length; i = i * 2 + 1) {
//            //如果左孩子大于右孩子，就让i指向右孩子
//            if (i < length && arr[i] < arr[i + 1]) {
//                i++;
//            }
//            //如果父节点大于或者等于较大的孩子，那就退出循环
//            if (temp >= arr[i]) {
//                break;
//            }
//            //如果父节点小于孩子节点，那就把孩子节点放到父节点上
//            arr[parent] = arr[i];
//            //把孩子节点的下标赋值给parent
//            //让其继续循环以保证大根堆构造正确
//            parent = i;
//        }
//        //将刚刚的父节点中的数据赋值给新位置
//        arr[parent] = temp;
//    }
//
//    //定义swap函数
//    //功能：将跟元素与最后位置的元素交换
//    //注意这里的最后是相对最后，是在变化的
//    public static void swap(int[] arr, int i) {
//        int temp = arr[0];
//        arr[0] = arr[i];
//        arr[i] = temp;
//    }


}
