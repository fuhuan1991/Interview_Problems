import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
   sliding window，一个经常会用到的算法。给一个数组 nums 和一个 sliding window 的长度k。
 现在假设一个window从左到右在数组上滑动，求出每一个时刻window中最大的元素。
 应该有 nums.length - k + 1 个最大值，把这些最大值放在一个数组中作为最终结果。
 */

/**
 * 为了实现O(n)的复杂度，我们需要建立并维护一个单调递减的队列，我们称之为queue，来记录 window 中所有可能成为最大值的元素。queue中只保存元素序号，而不是实际的值
 * 当window的最右端滑动到序号i时，nums[i]将进入window。在此之前，我们需要做两件事：
 *  1。 检查queue最左边的元素是否已经移出window范围，如果是的，将queue最左边的元素remove
 *  2。 检查queue最右边的元素是否小于nums[i]，如果是的话就remove，然后重复这一步，直到最右边的元素大于nums[i]为止
 * 之后，我们便可以把nums[i]加入到queue的最右边了。queue最左边的元素即为此时刻 window 中最大的元素，也就是答案。
 *
 * 这样做的原理：假如我们的window接触到一个新的元素nums[i]，它大于window中的一部分元素。那么那一部分元素在nums[i]被移出window之前没有可能成为最大值。
 * nums[i]被移出的情况有两种：1 nums[i]从左边划出了window。   2 nums[i]之后来了一个更大的元素。
 * 不论哪种情况，那些比nums[i]小的元素都没机会成为最大值了，所以remove它们是安全的。
 *
 * 虽然代码中存在循环的嵌套，但是时间复杂度仍然是O(n)
 */
class SlidingWindowMax {
    public int[] run(int[] nums, int k) {

        Deque<Integer> queue = new ArrayDeque<>();

        // initialization
        for (int i = 0; i < k; ++i) {
            int num = nums[i];
            while (queue.size() > 0 && nums[queue.peekLast()] <= num) {
                queue.removeLast();
            }
            queue.offerLast(i);
        }

        int[] result  = new int[nums.length - k + 1];
        result[0] = nums[queue.peekFirst()];

        for (int i = k; i < nums.length; ++i) {
            int newNumber = nums[i];

            // 检查queue最左边的元素是否已经移出window范围，如果是的，将queue最左边的元素remove
            if (queue.peekFirst() == i - k) queue.removeFirst();

            // 检查queue最右边的元素是否小于nums[i]，如果是的话就remove
            while (queue.size() > 0 && nums[queue.peekLast()] <= newNumber) {
                queue.removeLast();
            }
            queue.offerLast(i);
            result[i - k + 1] = nums[queue.peekFirst()];
        }

        return result;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{3, 1, 4, 2, 6, 2, 4, 2};
        int k = 3;

        SlidingWindowMax s = new SlidingWindowMax();
        int[] res = s.run(nums, k);
        System.out.println(Arrays.toString(res));
    }
}