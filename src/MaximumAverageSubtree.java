
// 在一个binary tree中寻找平均值最大的子树，并返回这个平均值
// 树中的每一个节点都有一个整数的value，所谓紫薯的平均值就是指这个子树中所有节点value相加再除以节点的个数

// 就像大部分树的问题一样，我们可以用递归的方法解决这个问题。
// 我们可以定义一个名为analyze的方法，它的作用就是分析一个节点，然后返回两个数据：
//  1 以该节点作为根的子树包含多少节点
//  2 以该节点作为根的子树的平均值
// 我们可以维护一个max变量作为最大的平均值，有必要的话就更新它
// 从root开始，用递归的形式调用我们的analyze方法，确保在每一个节点上，analyze都会运行一次

class MaximumAverageSubtree {

    double max = 0;

    public double maximumAverageSubtree(TreeNode root) {

        double[] r = this.analyze(root);

        return max;
    }

    private double[] analyze (TreeNode node) {

        if (node == null) {
            // corner case
            return new double[]{0, 0};
        } else {
            // normal case
            double[] Res_L = this.analyze(node.left);
            double[] Res_R = this.analyze(node.right);

            int counter = 1 + (int)Res_L[0] + (int)Res_R[0];
            double sum = node.val + Res_L[0]*Res_L[1] + Res_R[0]*Res_R[1];
            double avg = sum/counter;
            if (avg > this.max) this.max = avg;

            // the final result is an array with 2 elements:
            //   1. the number of the nodes in a subtree
            //   2. average value of those nodes
            return new double[]{counter, avg};
        }
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}

