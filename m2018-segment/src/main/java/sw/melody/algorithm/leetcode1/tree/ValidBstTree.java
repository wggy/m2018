package sw.melody.algorithm.leetcode1.tree;

/***
 * Created by ping on 2018-7-16
 *
 * 给定一个二叉树，判断其是否是一个有效的二叉搜索树。
 *
 * 一个二叉搜索树具有如下特征：
 * 节点的左子树只包含小于当前节点的数。
 * 节点的右子树只包含大于当前节点的数。
 * 所有左子树和右子树自身必须也是二叉搜索树。
 * 示例 1:
 * 输入:
  2
 / \
 1   3
 输出: true
 示例 2:

 输入:
  5
 / \
 1   4
 / \
 3  6
 输出: false
 解释: 输入为: [5,1,4,null,null,3,6]。
 根节点的值为 5 ，但是其右子节点值为 4 。

 */
public class ValidBstTree {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(15);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(20);
        isValidBST(root, true);
    }

    static void isValidBST(TreeNode root, boolean flag) {

        if (root == null) return;
        if (root.left != null) {
            if (root.val <= root.left.val) {
                return;
            }
            isValidBST(root.left, false);
        }
        if (root.right != null) {
            isValidBST(root.right, false);
            if (root.val >= root.right.val) {
                return;
            }
        }
    }
}
