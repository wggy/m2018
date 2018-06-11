package sw.melody.algorithm.leetcode1.array;

import java.util.HashSet;
import java.util.Set;

/***
 * Created by ping on 2018/6/5
 */
public class CheckSudoku {

    public static void main(String[] args) {

    }

    static boolean isValidSudoku(char[][] board) {
        if (board.length != 9) {
            return false;
        }
        for (char[] row : board) {
            if (row.length != 9) {
                return false;
            }
        }

        // 判断行
        for (int rowNum=0; rowNum<9; rowNum++) {
            Set<Character> rowSet = new HashSet<>();
            Set<Character> colSet = new HashSet<>();

            for (int colNum=0; colNum<9; colNum++) {
                // 判断行
                if (board[rowNum][colNum] != '.') {
                    if (rowSet.contains(board[rowNum][colNum])) {
                        return false;
                    }
                    rowSet.add(board[rowNum][colNum]);
                }

                // 判断列
                if (board[colNum][rowNum] != '.') {
                    if (rowSet.contains(board[colNum][rowNum])) {
                        return false;
                    }
                    colSet.add(board[colNum][rowNum]);
                }

                // 判断块
                if (rowNum % 3 == 0 || colNum % 3 == 0) {
                    Set<Character> blockSet = new HashSet<>();
                    for (int m = rowNum; m < rowNum + 3; m++) {
                        for (int k = colNum; k < colNum + 3; k++) {
                            if (board[m][k] != '.') {
                                if (blockSet.contains(board[m][k])) {
                                    return false;
                                }
                                blockSet.add(board[m][k]);
                            }
                        }
                    }
                }

            }

        }
        return true;
    }

    private static boolean isDuplicate(char[] nums) {
        int len = nums.length;
        for (int i=0; i<len; i++) {
            if ('.' == nums[i]) {
                continue;
            }
            for (int j=i+1; j<len; j++) {
                if ('.' != nums[j] && nums[i] == nums[j]) {
                    return true;
                }
            }
        }
        return false;
    }
}
