package sw.melody.algorithm.leetcode1.array;

import java.math.BigDecimal;

/***
 * Created by ping on 2018-7-10
 */
public class MergeSortedArray {

    public static void main(String[] args) {
//        int[] nums1 = {0};
//        int[] nums2 = {1};
//        merge(nums1, 0, nums2, 1);
        BigDecimal china = BigDecimal.valueOf(13.17);
        BigDecimal usa = BigDecimal.valueOf(19.55);
        int year = 0;
        while (china.compareTo(usa) < 0) {
            china = china.multiply(BigDecimal.valueOf(1.065)) ;
            usa = usa.multiply(BigDecimal.valueOf(1.023)) ;
            year++;
        }
        System.out.println(year);
        System.out.println("China : " + china.toString());
        System.out.println("Usa : " + usa.toString());
    }

    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        if (nums1 == null || nums2 == null || nums2.length<=0 || m<0 || n<=0) return;
        int i=0,j=0;
        int time = m;
        while (i<m+n && j<n) {
            if (nums1[i] > nums2[j]) {
                //移动数组nums1
                for (int k=nums1.length-2; k>=i; k--) {
                    nums1[k+1] = nums1[k];
                }
                nums1[i] = nums2[j];
                i++;
                j++;
                time++;
            } else {
                i++;
            }
        }
        for (int k = j; k<n; k++) {
            nums1[time++] = nums2[k];
        }

    }
}
