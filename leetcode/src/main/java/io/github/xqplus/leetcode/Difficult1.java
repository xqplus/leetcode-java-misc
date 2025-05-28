package io.github.xqplus.leetcode;

import java.util.Arrays;

public class Difficult1 {

    /**
     * 1312. 让字符串成为回文串的最少插入次数
     * 给你一个字符串 s ，每一次操作你都可以在字符串的任意位置插入任意字符。
     * 请你返回让 s 成为回文串的 最少操作次数 。
     * 「回文串」是正读和反读都相同的字符串。
     * 示例 1：
     * 输入：s = "zzazz"
     * 输出：0
     * 解释：字符串 "zzazz" 已经是回文串了，所以不需要做任何插入操作。
     * 示例 2：
     * 输入：s = "mbadm"
     * 输出：2
     * 解释：字符串可变为 "mbdadbm" 或者 "mdbabdm" 。
     * 示例 3：
     * 输入：s = "leetcode"
     * 输出：5
     * 解释：插入 5 个字符后字符串变为 "leetcodocteel" 。
     * 提示：
     * 1 <= s.length <= 500
     * s 中所有字符都是小写字母。
     */
    public static int minInsertions(String s) {
        int n = s.length();
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int prev = 0;
            for (int j = 1; j <= n; j++) {
                int temp = dp[j];
                if (s.charAt(i - 1) == s.charAt(s.length() - j)) {
                    dp[j] = prev + 1;
                } else {
                    dp[j] = Math.max(dp[j], dp[j - 1]);
                }
                prev = temp;
            }
        }
        return n - dp[n];
    }

    private static int minIns(String s, int l, int r, int cnt) {
        if (l >= r) {
            return cnt;
        }
        if (s.charAt(l) == s.charAt(r)) {
            return minIns(s, l + 1, r - 1, cnt);
        }
        return Math.min(minIns(s, l, r - 1, cnt + 1), minIns(s, l + 1, r, cnt + 1));

    }

    /**
     * 689. 三个无重叠子数组的最大和
     * 给你一个整数数组 nums 和一个整数 k ，找出三个长度为 k 、互不重叠、且全部数字和最大的子数组，并返回这三个子数组。
     * 以下标的数组形式返回结果，数组中的每一项分别指示每个子数组的起始位置（下标从 0 开始）。如果有多个结果，返回字典序最小的一个。
     * 示例 1：
     * 输入：nums = [1,2,1,2,6,7,5,1], k = 2
     * 输出：[0,3,5]
     * 解释：子数组 [1, 2], [2, 6], [7, 5] 对应的起始下标为 [0, 3, 5]。
     * 也可以取 [2, 1], 但是结果 [1, 3, 5] 在字典序上更小。
     * 示例 2：
     * 输入：nums = [1,2,1,2,1,2,1,2,1], k = 2
     * 输出：[0,2,4]
     * 提示：
     * 1 <= nums.length <= 2 * 10^4
     * 1 <= nums[i] < 2^16
     * 1 <= k <= floor(nums.length / 3)
     */
    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        int[] ans = new int[3];
        int sum1 = 0, maxSum1 = 0, maxSum1Idx = 0;
        int sum2 = 0, maxSum12 = 0, maxSum12Idx1 = 0, maxSum12Idx2 = 0;
        int sum3 = 0, maxTotal = 0;
        for (int i = k * 2; i < nums.length; ++i) {
            sum1 += nums[i - k * 2];
            sum2 += nums[i - k];
            sum3 += nums[i];
            if (i >= k * 3 - 1) {
                if (sum1 > maxSum1) {
                    maxSum1 = sum1;
                    maxSum1Idx = i - k * 3 + 1;
                }
                if (maxSum1 + sum2 > maxSum12) {
                    maxSum12 = maxSum1 + sum2;
                    maxSum12Idx1 = maxSum1Idx;
                    maxSum12Idx2 = i - k * 2 + 1;
                }
                if (maxSum12 + sum3 > maxTotal) {
                    maxTotal = maxSum12 + sum3;
                    ans[0] = maxSum12Idx1;
                    ans[1] = maxSum12Idx2;
                    ans[2] = i - k + 1;
                }
                sum1 -= nums[i - k * 3 + 1];
                sum2 -= nums[i - k * 2 + 1];
                sum3 -= nums[i - k + 1];
            }
        }
        return ans;
    }

    /**
     * 1982. 从子集的和还原数组
     * 存在一个未知数组需要你进行还原，给你一个整数 n 表示该数组的长度。
     * 另给你一个数组 sums ，由未知数组中全部 2^n 个 子集的和 组成（子集中的元素没有特定的顺序）。
     * 返回一个长度为 n 的数组 ans 表示还原得到的未知数组。如果存在 多种 答案，只需返回其中 任意一个 。
     * 如果可以由数组 arr 删除部分元素（也可能不删除或全删除）得到数组 sub ，那么数组 sub 就是数组 arr 的一个 子集 。
     * sub 的元素之和就是 arr 的一个 子集的和 。一个空数组的元素之和为 0 。
     * 注意：生成的测试用例将保证至少存在一个正确答案。
     * 示例 1：
     * 输入：n = 3, sums = [-3,-2,-1,0,0,1,2,3]
     * 输出：[1,2,-3]
     * 解释：[1,2,-3] 能够满足给出的子集的和：
     * - []：和是 0
     * - [1]：和是 1
     * - [2]：和是 2
     * - [1,2]：和是 3
     * - [-3]：和是 -3
     * - [1,-3]：和是 -2
     * - [2,-3]：和是 -1
     * - [1,2,-3]：和是 0
     * 注意，[1,2,-3] 的任何排列和 [-1,-2,3] 的任何排列都会被视作正确答案。
     * 示例 2：
     * 输入：n = 2, sums = [0,0,0,0]
     * 输出：[0,0]
     * 解释：唯一的正确答案是 [0,0] 。
     * 示例 3：
     * 输入：n = 4, sums = [0,0,5,5,4,-1,4,9,9,-1,4,3,4,8,3,8]
     * 输出：[0,-1,4,5]
     * 解释：[0,-1,4,5] 能够满足给出的子集的和。
     * 提示：
     * 1 <= n <= 15
     * sums.length == 2^n
     * -10^4 <= sums[i] <= 10^4
     */
    public int[] recoverArray(int n, int[] sums) {
        // 输入：n = 3, sums = [-3,-2,-1,0,0,1,2,3] min=-3 max=3
        // 结果数组的每一项一定出现在 sums中，如果sums中存在负数，那么结果集一定存在负数，否则和不可能为负数
        // 则可以先取负数作为一项，暴力尝试
        // 先取 -3 -2，最后一个可取的有 -1 0 1 2 3
        // -1：0x
        // 0: -1x
        // 1: -1 -2+1 0x
        // 2: -1 -3+2 0 -2+2 1x
        // 3: -1x

        // 取 -3 -1 可取 0 1 2 3
        // 0: -2

        // -1 + -2 + -3 = -6
        // -2 + -3 =-5


        // 3 4 2 7
        // 2 + 3 = 5
        // 2 + 3 + 4 = 9

        // TODO
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

    }
}
