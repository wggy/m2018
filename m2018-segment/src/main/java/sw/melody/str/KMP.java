package sw.melody.str;

import org.springframework.util.Assert;

/***
 *  create by wggy on 2019/6/15 23:22 
 **/
public class KMP {

    public static void main(String[] args) {
        String orign = "BBC ABCDAB ABCDABCDABDE";
        String sep = "ABCDABD";
        int index = force(orign, sep);
        int expect = orign.indexOf(sep);
        int kmpIndex = kmp(orign, sep);
        System.out.println(index);
        System.out.println(expect);
        System.out.println(kmpIndex);
        Assert.state(index == expect, "No.......");
        Assert.state(kmpIndex == expect, "No.......");

        int[] f = process(sep);
        for (int i = 0; i < f.length; i++) {
            System.out.println(f[i]);
        }

    }

    /***
     * 暴力求解，从左到右逐个字符串轮询
     * @param origin
     * @param sep
     * @return
     */
    static int force(String origin, String sep) {
        if (origin == null || "".equals(origin))
            return -1;
        if (sep == null || "".equals(sep))
            return -1;
        int originLength = origin.length();
        int sepLength = sep.length();
        for (int idx = 0; idx < originLength; idx++) {
            if (origin.charAt(idx) == sep.charAt(0)) {
                boolean flag = false;
                int temp = idx;
                for (int k = 0; k < sepLength; ) {
                    if (origin.charAt(temp++) != sep.charAt(k++)) {
                        flag = true;
                        break;
                    }
                }
                if (!flag)
                    return idx;
            }
        }
        return -1;
    }

    /***
     * 此法同上，也是暴力解法，利用双指针移动，只用一个循环就能解决问题，更为精妙
     * @param origin
     * @param sep
     * @return
     */
    static int force2(String origin, String sep) {
        if (origin == null || "".equals(origin))
            return -1;
        if (sep == null || "".equals(sep))
            return -1;
        int originLength = origin.length();
        int sepLength = sep.length();
        int i = 0, j = 0;
        while (i < originLength && j < sepLength) {
            if (origin.charAt(i) == sep.charAt(j)) {
                i++;
                j++;
            } else {
                i = i - j + 1;
                j = 0;
            }
        }
        if (j == sepLength)
            return i - j;
        return -1;
    }

    /***
     * 利用kmp算法，俗称看毛片，利用已经比较的字符串作为依据，
     * 根据子串结构，选出部分匹配表，避免不断回溯重复比较
     * @param origin
     * @param pattern
     * @return
     */
    static int kmp(String origin, String pattern) {

        int[] next = process(pattern);
        int i = 0, j = 0;
        int originLength = origin.length();
        int patternLength = pattern.length();
        while (i < originLength && j < patternLength) {
            if (j == -1 || origin.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }
        if (j == patternLength)
            return i - j;
        return -1;
    }

    static int[] process(String sep) {
        int[] next = new int[sep.length()];
        next[0] = -1;
        next[1] = 0;
        int k;
        for (int j = 2; j < sep.length(); j++) {
            k = next[j - 1];
            while (k != -1) {
                if (sep.charAt(j - 1) == sep.charAt(k)) {
                    next[j] = k + 1;
                    break;
                } else {
                    k = next[k];
                }
                next[j] = 0;  //当k==-1而跳出循环时，next[j] = 0，否则next[j]会在break之前被赋值
            }
        }
        return next;
    }

}
