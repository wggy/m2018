package sw.melody.algorithm.leetcode1.string;

/***
 * Created by ping on 2018/4/18
 *
 * 请编写一个函数，其功能是将输入的字符串反转过来。
 *
 * 示例：
 * 输入：s = "hello"
 * 返回："olleh"
 */
public class ReverseString {
    public static void main(String[] args) {
        System.out.println(reverseString("wangping"));
    }

    public static String reverseString(String s) {
        if (s == null) {
            return null;
        }
        byte[] bytes = s.getBytes();
        int length = bytes.length;
        int mid = length / 2;
        for (int i=0; i<mid; i++) {
            byte temp = bytes[i];
            bytes[i] = bytes[length-i-1];
            bytes[length-i-1] = temp;
        }
        return new String(bytes);
    }
}
