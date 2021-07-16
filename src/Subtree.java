
/**
 * 判断二叉树 t 是否是二叉树 s 的子树。不是很复杂，使用递归的形式就可以完成。
 * 时间复杂度为M * N，其中 M 是 s 的节点数，N 是 t 的节点数。
 *
 * 我想这个题是会有一些follow up的，比如如何进一步缩减时间复杂度
 * 这里有一个思路：
 *      先遍历一遍 s，找出s中每一个节点的高度（叶节点高度为零，root高度最高），再计算出 t 的高度。然后重复之前的搜索过程，此时
 *      如果 s 中某节点的高度不等于t的高度，则无需在这个节点上进行递归搜索了。
 *      这样子我们只需要在 s 的某一层的节点上进行递归搜索就够了
 */

public class Subtree {
    // 判断 t 是否是 s 的子树
    public boolean isIdentical(TreeNode s, TreeNode t) {
        if (s == null && t == null) {
            return true;
        } else if (s == null || t == null) {
            return false;
        } else if (s.val != t.val) {
            return isIdentical(s.left, t) || isIdentical(s.right, t);
        } else {
            return isIdentical(s.left, t.left) && isIdentical(s.right, t.right);
        }
    }

    public boolean isSubtree(TreeNode s, TreeNode t) {
        return isIdentical(s, t) || isIdentical(s.left, t) || isIdentical(s.right, t);
    }

    // Test
    public static void main(String[] args) {
        Subtree S = new Subtree();
        TreeNode root = new TreeNode(1);
        TreeNode n2 = new TreeNode(2);
        TreeNode n3 = new TreeNode(3);
        TreeNode n4 = new TreeNode(4);
        TreeNode n5 = new TreeNode(5);
        TreeNode n6 = new TreeNode(6);
        TreeNode n7 = new TreeNode(7);
        TreeNode n8 = new TreeNode(8);
        TreeNode n9 = new TreeNode(9);
        root.left = n2;
        root.right = n3;
        n3.left = n4;
        n3.right = n5;
        n4.left = n6;
        n4.right = n7;
        n5.left = n8;
        n5.right = n9;

        TreeNode t4 = new TreeNode(4);
        TreeNode t6 = new TreeNode(6);
        TreeNode t7 = new TreeNode(7);
        t4.left = t6;
        t4.right = t7;

        System.out.println(S.isSubtree(root, t4));

    }
}

class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    TreeNode(int val) { this.val = val; };
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}