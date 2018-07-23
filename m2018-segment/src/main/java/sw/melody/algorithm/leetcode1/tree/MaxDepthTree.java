package sw.melody.algorithm.leetcode1.tree;

/***
 * Created by ping on 2018-7-16
 *
 * 给定一个二叉树，找出其最大深度。

 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。

 说明: 叶子节点是指没有子节点的节点。

 示例：
 给定二叉树 [3,9,20,null,null,15,7]，

 3
 / \
 9  20
 /  \
 15   7
 返回它的最大深度 3 。
 */
public class MaxDepthTree {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);

        after(root);
    }

    static int maxDepth(TreeNode root, int depth) {
        if (root == null) {
            return depth;
        } else {
            depth++;
            int d1 = maxDepth(root.left, depth);
            int d2 = maxDepth(root.right, depth);
            return d1 > d2 ? d1 : d2;
        }
    }

    static void before(TreeNode root) {
        if (root != null) {
            System.out.println(root.val);
            before(root.left);
            before(root.right);
        }
    }

    static void mid(TreeNode root) {
        if (root != null) {
            mid(root.left);
            System.out.println(root.val);
            mid(root.right);
        }
    }

    static void after(TreeNode root) {
        if (root != null) {
            after(root.left);
            after(root.right);
            System.out.println(root.val);
        }
    }
}
