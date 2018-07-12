package sw.melody.algorithm.leetcode1.array;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/***
 * Created by ping on 2018-7-6
 *
 * 输入[a,b]
 *
 * 输出[a,b],[a,*],[*,b],[*,*]
 */
public class OutputArray {

    private static Set<String> set = new HashSet<>();

    public static void main(String[] args) {
        String[] arr = {"a", "b", "c", "d"};
        output(arr);
        System.out.println(set);
    }

    private static void output(String[] array) {
        if (array == null || array.length <= 0) {
            return;
        }
        for (int i=0; i<array.length; i++) {
            set.add(print(array));
            if (!"*".equals(array[i])) {
                String[] copyArray = new String[array.length];
                System.arraycopy(array, 0, copyArray, 0, array.length);
                copyArray[i] = "*";
                output(copyArray);
            }
        }

    }

    private static String print(String[] array) {
        StringBuilder sb = new StringBuilder("[");
        for (String s : array) {
            sb.append(s).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }
}
