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
        System.out.println(index);
        System.out.println(expect);
        Assert.state(index == expect, "No.......");
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
     * 利用kmp算法，俗称看毛片，利用已经比较的字符串作为依据，
     * 根据子串结构，选出部分匹配表，避免不断回溯重复比较
     * @param origin
     * @param sep
     * @return
     */
    static int kmp(String origin, String sep) {

        return -1;
    }


}
