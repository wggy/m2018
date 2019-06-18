package sw.melody.str;

import java.util.TreeSet;

/***
 *  create by wggy on 2019/6/18 23:39
 *  给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 *
 *  示例 1:
 *  输入: "abcabcbb"
 *  输出: 3
 *  解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 *
 *  示例 2:
 *  输入: "pwwkew"
 *  输出: 3
 *  解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *  请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 *
 **/
public class LengthOfLongestSubstring {

    public static void main(String[] args) {
        int l = longestSubstr("dvdf");
        System.out.println(l);
    }

    /***
     * 执行结果: 通过
     * 执行用时 : 343 ms, 在所有 Java 提交中击败了5.92%的用户
     * 内存消耗 : 67.8 MB,在所有 Java 提交中击败了12.65%的用户
     *
     * 可能性能出现在j的回溯上，鉴于kmp算法的回溯不是回到上次的下一位，此题可以借鉴
     * @param s
     * @return
     */
    static int longestSubstr(String s) {
        if (s == null || "".equals(s))
            return 0;
        TreeSet<Character> set = new TreeSet<>();
        int len = s.length();
        int max = 0;
        int i=0, j=0;
        while (i < len) {
            char c = s.charAt(i);
            if (!set.contains(c)) {
                set.add(c);
                if ((++i) == len)
                    max = max < set.size() ? set.size() : max;
            } else {
                max = max < set.size() ? set.size() : max;
                set.clear();
                i = (++j);
            }
        }
        return max;
    }
}
