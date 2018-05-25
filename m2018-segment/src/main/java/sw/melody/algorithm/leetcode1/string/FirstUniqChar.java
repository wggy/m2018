package sw.melody.algorithm.leetcode1.string;

/***
 * Created by ping on 2018/4/18
 * 给定一个字符串，找到它的第一个不重复的字符，并返回它的索引。如果不存在，则返回 -1。
 *
 * 案例:
 * s = "leetcode"
 * 返回 0.
 *
 * s = "loveleetcode",
 * 返回 2.
 *
 */
public class FirstUniqChar {
    public static void main(String[] args) {

    }

    private static int firstUniqChar(String s) {
        if (s == null || s.length() == 0) {
            return -1;
        }
        byte[] bytes = s.getBytes();
        for (int i=0; i<bytes.length; i++) {
        }

        return -1;
    }
}
