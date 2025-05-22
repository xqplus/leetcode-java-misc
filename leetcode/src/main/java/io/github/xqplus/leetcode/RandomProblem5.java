package io.github.xqplus.leetcode;

import java.util.*;

/**
 * 随机问题类五
 *
 * @author chenq
 * @since 2025.5.20
 */
public class RandomProblem5 {

    /**
     * 1573. 分割字符串的方案数
     * 给你一个二进制串 s  （一个只包含 0 和 1 的字符串），我们可以将 s 分割成 3 个 非空 字符串 s1, s2, s3 （s1 + s2 + s3 = s）。
     * 请你返回分割 s 的方案数，满足 s1，s2 和 s3 中字符 '1' 的数目相同。
     * 由于答案可能很大，请将它对 10^9 + 7 取余后返回。
     * 示例 1：
     * 输入：s = "10101"
     * 输出：4
     * 解释：总共有 4 种方法将 s 分割成含有 '1' 数目相同的三个子字符串。
     * "1|010|1"
     * "1|01|01"
     * "10|10|1"
     * "10|1|01"
     * 示例 2：
     * 输入：s = "1001"
     * 输出：0
     * 示例 3：
     * 输入：s = "0000"
     * 输出：3
     * 解释：总共有 3 种分割 s 的方法。
     * "0|0|00"
     * "0|00|0"
     * "00|0|0"
     * 示例 4：
     * 输入：s = "100100010100110"
     * 输出：12
     * 提示：
     * s[i] == '0' 或者 s[i] == '1'
     * 3 <= s.length <= 10^5
     */
    public static int numWays(String s) {
        // 先计算1的个数，如果 num % 3 > 0 则不能按题目规则分割
        // 然后从左到右、从右到左遍历 num / 3 个1起， num / 3 + 1个1止，然后所得乘积就是答案
        // int num = Integer.bitCount(Integer.parseInt(s, 2)); // 不能用 会超长
        int num = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1') {
                num++;
            }
        }
        if (num == 0) {
            long ways = (long) (s.length() - 1) * (s.length() - 2) / 2;
            return (int) (ways % 1000000007);
        }
        if (num % 3 != 0) {
            return 0;
        }

        int n = num / 3;
        int i = 0, m = 0;
        long l = 0;
        while (m <= n) {
            if (s.charAt(i) == '1') {
                m++;
            }
            if (m == n) {
                l++;
            }
            i++;
        }

        i = s.length() - 1;
        m = 0;
        long r = 0;
        while (m <= n) {
            if (s.charAt(i) == '1') {
                m++;
            }
            if (m == n) {
                r++;
            }
            i--;
        }
        return (int) ((l * r) % 1000000007);
    }

    /**
     * 454. 四数相加 II
     * 给你四个整数数组 nums1、nums2、nums3 和 nums4 ，数组长度都是 n ，请你计算有多少个元组 (i, j, k, l) 能满足：
     * 0 <= i, j, k, l < n
     * nums1[i] + nums2[j] + nums3[k] + nums4[l] == 0
     * 示例 1：
     * 输入：nums1 = [1,2], nums2 = [-2,-1], nums3 = [-1,2], nums4 = [0,2]
     * 输出：2
     * 解释：
     * 两个元组如下：
     * 1. (0, 0, 0, 1) -> nums1[0] + nums2[0] + nums3[0] + nums4[1] = 1 + (-2) + (-1) + 2 = 0
     * 2. (1, 1, 0, 0) -> nums1[1] + nums2[1] + nums3[0] + nums4[0] = 2 + (-1) + (-1) + 0 = 0
     * 示例 2：
     * 输入：nums1 = [0], nums2 = [0], nums3 = [0], nums4 = [0]
     * 输出：1
     * n == nums1.length
     * n == nums2.length
     * n == nums3.length
     * n == nums4.length
     * 1 <= n <= 200
     * -2^28 <= nums1[i], nums2[i], nums3[i], nums4[i] <= 2^28
     */
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : nums1) {
            for (int j : nums2) {
                int sum = i + j;
                map.put(sum, map.getOrDefault(sum, 0) + 1);
            }
        }
        int ans = 0;
        for (int i : nums3) {
            for (int j : nums4) {
                int sum = i + j;
                Integer val = map.get(-sum);
                if (val != null) {
                    ans += val;
                }
            }
        }
        return ans;
    }

    /**
     * 1785. 构成特定和需要添加的最少元素
     * 给你一个整数数组 nums ，和两个整数 limit 与 goal 。数组 nums 有一条重要属性：abs(nums[i]) <= limit 。
     * 返回使数组元素总和等于 goal 所需要向数组中添加的 最少元素数量 ，添加元素 不应改变 数组中 abs(nums[i]) <= limit 这一属性。
     * 注意，如果 x >= 0 ，那么 abs(x) 等于 x ；否则，等于 -x 。
     * 示例 1：
     * 输入：nums = [1,-1,1], limit = 3, goal = -4
     * 输出：2
     * 解释：可以将 -2 和 -3 添加到数组中，数组的元素总和变为 1 - 1 + 1 - 2 - 3 = -4 。
     * 示例 2：
     * 输入：nums = [1,-10,9,1], limit = 100, goal = 0
     * 输出：1
     * 提示：
     * 1 <= nums.length <= 10^5
     * 1 <= limit <= 10^6
     * -limit <= nums[i] <= limit
     * -10^9 <= goal <= 10^9
     */
    public static int minElements(int[] nums, int limit, int goal) {
        long sum = 0;
        for (int num : nums) {
            sum += num;
        }
        long diff = Math.abs(goal - sum);
        return (int) (diff / limit + (diff % limit > 0 ? 1 : 0));
    }

    /**
     * 2652. 倍数求和
     * 给你一个正整数 n ，请你计算在 [1，n] 范围内能被 3、5、7 整除的所有整数之和。
     * 返回一个整数，用于表示给定范围内所有满足约束条件的数字之和。
     * 示例 1：
     * 输入：n = 7
     * 输出：21
     * 解释：在 [1, 7] 范围内能被 3、5、7 整除的所有整数分别是 3、5、6、7 。数字之和为 21。
     * 示例 2：
     * 输入：n = 10
     * 输出：40
     * 解释：在 [1, 10] 范围内能被 3、5、7 整除的所有整数分别是 3、5、6、7、9、10 。数字之和为 40。
     * 示例 3：
     * 输入：n = 9
     * 输出：30
     * 解释：在 [1, 9] 范围内能被 3、5、7 整除的所有整数分别是 3、5、6、7、9 。数字之和为 30。
     * 提示：
     * 1 <= n <= 10^3
     */
    public static int sumOfMultiples(int n) {
        // 暴力解法
        int ans = 0;
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 || i % 5 == 0 || i % 7 == 0) {
                ans += i;
            }
        }
        return ans;

        // 3 5 6 7 9 10 12
        // 18 + 15 + 7 - 公倍数 15 30 45  60    21 42 63 84   35 70
        // 等差数列
        //return sn1(n, 3) + sn1(n, 5) + sn1(n, 7) - sn1(n, 15) - sn1(n, 21) - sn1(n, 35) + sn1(n, 105);
    }

    private int sn1(int n, int b) {
        int nn = n / b;
        int e = nn * b;
        return (b + e) * nn / 2;
    }

    /**
     * 1909. 删除一个元素使数组严格递增
     * 给你一个下标从 0 开始的整数数组 nums ，如果 恰好 删除 一个 元素后，数组 严格递增 ，那么请你返回 true ，否则返回 false 。
     * 如果数组本身已经是严格递增的，请你也返回 true 。
     * 数组 nums 是 严格递增 的定义为：对于任意下标的 1 <= i < nums.length 都满足 nums[i - 1] < nums[i] 。
     * 示例 1：
     * 输入：nums = [1,2,10,5,7]
     * 输出：true
     * 解释：从 nums 中删除下标 2 处的 10 ，得到 [1,2,5,7] 。
     * [1,2,5,7] 是严格递增的，所以返回 true 。
     * 示例 2：
     * 输入：nums = [2,3,1,2]
     * 输出：false
     * 解释：
     * [3,1,2] 是删除下标 0 处元素后得到的结果。
     * [2,1,2] 是删除下标 1 处元素后得到的结果。
     * [2,3,2] 是删除下标 2 处元素后得到的结果。
     * [2,3,1] 是删除下标 3 处元素后得到的结果。
     * 没有任何结果数组是严格递增的，所以返回 false 。
     * 示例 3：
     * 输入：nums = [1,1,1]
     * 输出：false
     * 解释：删除任意元素后的结果都是 [1,1] 。
     * [1,1] 不是严格递增的，所以返回 false 。
     * 示例 4：
     * 输入：nums = [1,2,3]
     * 输出：true
     * 解释：[1,2,3] 已经是严格递增的，所以返回 true 。
     * 提示：
     * 2 <= nums.length <= 1000
     * 1 <= nums[i] <= 1000
     */
    public static boolean canBeIncreasing(int[] nums) {
        // 5 6 7 4 9 10 11 11 12 13
        // 删除一个元素，那么最多存在两个严格递增子序列
        int n = nums.length, left = -1;
        for (int i = 1; i < n; i++) {
            if (nums[i] <= nums[i - 1]) {
                left = i;
                break;
            }
        }
        if (left == -1) {
            return true;
        }
        int right = -1;
        for (int i = n - 2; i >= 0; i--) {
            if (nums[i] >= nums[i + 1]) {
                right = i;
                break;
            }
        }
        if (left < right) { // 证明存在超过两个子序列
            return false;
        }
        if (left < n - 1 && right > 0) { // 左右存在序列
            return nums[left - 1] < nums[left + 1] || nums[right - 1] < nums[right + 1];
        }
        return true;
    }

    /**
     * LCR 161. 连续天数的最高销售额
     * 某公司每日销售额记于整数数组 sales，请返回所有 连续 一或多天销售额总和的最大值。
     * 要求实现时间复杂度为 O(n) 的算法。
     * 示例 1：
     * 输入：sales = [-2,1,-3,4,-1,2,1,-5,4]
     * 输出：6
     * 解释：[4,-1,2,1] 此连续四天的销售总额最高，为 6。
     * 示例 2：
     * 输入：sales = [5,4,-1,7,8]
     * 输出：23
     * 解释：[5,4,-1,7,8] 此连续五天的销售总额最高，为 23。
     * 提示：
     * 1 <= arr.length <= 10^5
     * -100 <= arr[i] <= 100
     */
    public int maxSales(int[] sales) {
        // [-2,1,-3,4,-1,2,1,-5,4]
        // -2 1 -2 4 3 5 6 1 5
        int prevMax = sales[0], ans = prevMax;
        for (int i = 1; i < sales.length; i++) {
            prevMax = Math.max(prevMax + sales[i], sales[i]);
            ans = Math.max(ans, prevMax);
        }
        return ans;
    }

    /**
     * 2235. 两整数相加
     * 给你两个整数 num1 和 num2，返回这两个整数的和。
     * 示例 1：
     * 输入：num1 = 12, num2 = 5
     * 输出：17
     * 解释：num1 是 12，num2 是 5 ，它们的和是 12 + 5 = 17 ，因此返回 17 。
     * 示例 2：
     * 输入：num1 = -10, num2 = 4
     * 输出：-6
     * 解释：num1 + num2 = -6 ，因此返回 -6 。
     * 提示：
     * -100 <= num1, num2 <= 100
     */
    public int sum(int num1, int num2) {
        return num1 + num2;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 10, 5, 7};
        System.out.println(canBeIncreasing(nums));
    }
}
