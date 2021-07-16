
// 有一个M*N的矩阵 matrix，每一格都是字符'1'或者'0'。写一个算法算出其中最大的全都为'1'组成的正方形的面积。

// 我们可以使用dynamic programming处理这个问题。
// 首先创建另一个M*N的矩阵用于存放中间结果，我们称之为DP。
// DP[i][j]记录着以matrix[i][j]作为起始位置（左上角）的最大的"全1"正方形的边长。
// 我们从DP的右下角开始逐渐填充DP，最后的答案即为DP中最大的那个数值的平方。
// DP[i][j]的值取决于[i+1][j]，[i][j+1] 和 [i+1][j+1] 这3个值，以及matrix[i][j]的值。
// 由于我们是从右下角开始遍历，[i+1][j]，[i][j+1] 和 [i+1][j+1] 这3个值在之前的计算中已经算好了，
// 所以我们可以利用之前计算的结果进行下一步计算，就像其他的DP问题一样。


class MaxSquare {
    public int run(char[][] matrix) {

        if (matrix.length == 0) return 0;

        int height = matrix.length;
        int width = matrix[0].length;
        int[][] dp = new int[height][width];
        int max = 0;

        for (int i = height-1; i >= 0; --i) {
            for (int j = width-1; j >= 0; --j) {
                if (matrix[i][j] == '1') {
                    if (i == height - 1) {
                        // corner case
                        dp[i][j] = 1;
                    } else if (j == width - 1) {
                        // corner case
                        dp[i][j] = 1;
                    } else {
                        // normal case, 1 + the minimal of that 3 values
                        dp[i][j] = 1 + Math.min(Math.min(dp[i+1][j], dp[i][j+1]), dp[i+1][j+1]);
                    }
                } else {
                    dp[i][j] = 0;
                }
                max = Math.max(max, dp[i][j]);
            }
        }

        return max*max;
    }

    // Test, I made a graph for this
    public static void main(String[] args) {
        char[] row_1 = new char[]{'1', '1', '1'};
        char[] row_2 = new char[]{'1', '1', '1'};
        char[] row_3 = new char[]{'0', '0', '1'};
        char[][] matrix = new char[][]{row_1, row_2, row_3};

        MaxSquare M = new MaxSquare();
        int res = M.run(matrix);
        System.out.println(res);
    }
}