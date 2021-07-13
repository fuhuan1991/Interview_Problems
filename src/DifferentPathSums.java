
// 在一个binary tree中每一个节点都有一个整数的value。
// 从一个叶节点出发到另外一个叶节点可以形成一个路径，计算每个路径中节点values的和。
// 我们可以把这样的和称作"完全和"，最终目标是找出所有不同的完全和。

// 就像大部分树的问题一样，我们可以用递归的方法解决这个问题。
// 我们可以定义一个名为analyze的方法，它接受一个Treenode作为参数，返回值是一个HashSet，其中包含了从该节点出发，到下方每一个叶节点的路径values之和。
// 从一个非叶节点到一个叶节点的路径和，我们可以称之为"部分和"

// analyze方法会以递归的方式在左右child上调用自己，按照计划会得到两个HashSet。
// 然后根据这两个HashSet计算所有可能的完全和。方法是从左边的HashSet中去一个部分和，加上当前节点的value，再加上右边节点中的一个部分和。
// 可以把当前的节点视为一个桥梁，连接了两个不完全的路径，最后形成了一个叶节点到叶节点的完全路径。
// 我们可以把所有得到的万泉河存放在一个HashSet中，可以消除重复值


import java.util.HashSet;

class DifferentPathSums {

    public HashSet<Integer> DifferentSums = new HashSet<>();

    public HashSet<Integer> run(TreeNode root) {
        this.analyze(root);
        return this.DifferentSums;
    }

    private HashSet<Integer> analyze (TreeNode node) {

        if (node == null) {
            // corner case
            return null;
        } else {
            // normal case
            HashSet<Integer> Res_L = this.analyze(node.left);
            HashSet<Integer> Res_R = this.analyze(node.right);

            HashSet<Integer> res = new HashSet<>();

            if (Res_L != null && Res_R != null) {
                // current node has both left child and right child

                for (int sum_L : Res_L) {
                    for (int sum_R : Res_R) {
                        DifferentSums.add(sum_L + sum_R + node.val);
                    }
                }

                for (int sum : Res_L) {
                    res.add(sum + node.val);
                }

                for (int sum : Res_R) {
                    res.add(sum + node.val);
                }
            } else if (Res_L != null) {
                // current node has only left child
                for (int sum : Res_L) {
                    res.add(sum + node.val);
                }
            } else if (Res_R != null) {
                // current node has only right child
                for (int sum : Res_R) {
                    res.add(sum + node.val);
                }
            } else {
                // current node is a leaf
                res.add(node.val);
            }

            return res;
        }
    }

    public static class TreeNode {
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

    // Test
    public static void main(String[] args) {
        DifferentPathSums D = new DifferentPathSums();

        // build a tree. I made a graph for it
        TreeNode root = new TreeNode(3);
        TreeNode n_1_1 = new TreeNode(12);
        TreeNode n_1_2 = new TreeNode(3);
        TreeNode n_2_1 = new TreeNode(1);
        TreeNode n_2_2 = new TreeNode(2);
        TreeNode n_2_3 = new TreeNode(2);
        TreeNode n_2_4 = new TreeNode(6);
        TreeNode n_3_1 = new TreeNode(3);
        TreeNode n_3_2 = new TreeNode(1);
        root.left = n_1_1;
        root.right = n_1_2;
        n_1_1.left = n_2_1;
        n_1_1.right = n_2_2;
        n_1_2.left = n_2_3;
        n_1_2.right = n_2_4;
        n_2_3.left = n_3_1;
        n_2_3.right = n_3_2;

        HashSet<Integer> res = D.run(root);
        System.out.println(res);
    }
}

