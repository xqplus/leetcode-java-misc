package io.github.xqplus.leetcode;

import java.util.*;

public class Simple2 {

    /**
     * 1608. 特殊数组的特征值
     * 给你一个非负整数数组 nums 。如果存在一个数 x ，使得 nums 中恰好有 x 个元素 大于或者等于 x ，
     * 那么就称 nums 是一个 特殊数组 ，而 x 是该数组的 特征值 。
     * 注意： x 不必 是 nums 的中的元素。
     * 如果数组 nums 是一个 特殊数组 ，请返回它的特征值 x 。否则，返回 -1 。可以证明的是，如果 nums 是特殊数组，那么其特征值 x 是 唯一的 。
     * 示例 1：
     * 输入：nums = [3,5]
     * 输出：2
     * 解释：有 2 个元素（3 和 5）大于或等于 2 。
     * 示例 2：
     * 输入：nums = [0,0]
     * 输出：-1
     * 解释：没有满足题目要求的特殊数组，故而也不存在特征值 x 。
     * 如果 x = 0，应该有 0 个元素 >= x，但实际有 2 个。
     * 如果 x = 1，应该有 1 个元素 >= x，但实际有 0 个。
     * 如果 x = 2，应该有 2 个元素 >= x，但实际有 0 个。
     * x 不能取更大的值，因为 nums 中只有两个元素。
     * 示例 3：
     * 输入：nums = [0,4,3,0,4]
     * 输出：3
     * 解释：有 3 个元素大于或等于 3 。
     * 示例 4：
     * 输入：nums = [3,6,7,7,0]
     * 输出：-1
     * 提示：
     * 1 <= nums.length <= 100
     * 0 <= nums[i] <= 1000
     */
    public int specialArray(int[] nums) {
        Arrays.sort(nums);
        int len = nums.length, x = len, n = 0;
        while (x > -1) {
            int i = len - 1 - n;
            while (i >= 0 && nums[i] >= x) {
                i--;
                n++;
            }
            if (n == x) {
                break;
            }
            x--;
        }
        return x;
    }

    /**
     * 2164. 对奇偶下标分别排序
     * 给你一个下标从 0 开始的整数数组 nums 。根据下述规则重排 nums 中的值：
     * 按 非递增 顺序排列 nums 奇数下标 上的所有值。
     * 举个例子，如果排序前 nums = [4,1,2,3] ，对奇数下标的值排序后变为 [4,3,2,1] 。奇数下标 1 和 3 的值按照非递增顺序重排。
     * 按 非递减 顺序排列 nums 偶数下标 上的所有值。
     * 举个例子，如果排序前 nums = [4,1,2,3] ，对偶数下标的值排序后变为 [2,1,4,3] 。偶数下标 0 和 2 的值按照非递减顺序重排。
     * 返回重排 nums 的值之后形成的数组。
     * 示例 1：
     * 输入：nums = [4,1,2,3]
     * 输出：[2,3,4,1]
     * 解释：
     * 首先，按非递增顺序重排奇数下标（1 和 3）的值。
     * 所以，nums 从 [4,1,2,3] 变为 [4,3,2,1] 。
     * 然后，按非递减顺序重排偶数下标（0 和 2）的值。
     * 所以，nums 从 [4,1,2,3] 变为 [2,3,4,1] 。
     * 因此，重排之后形成的数组是 [2,3,4,1] 。
     * 示例 2：
     * 输入：nums = [2,1]
     * 输出：[2,1]
     * 解释：
     * 由于只有一个奇数下标和一个偶数下标，所以不会发生重排。
     * 形成的结果数组是 [2,1] ，和初始数组一样。
     * 提示：
     * 1 <= nums.length <= 100
     * 1 <= nums[i] <= 100
     */
    public int[] sortEvenOdd(int[] nums) {
        if (nums.length == 1) {
            return nums;
        }
        countSortFill(nums, 0);
        countSortFill(nums, 1);
        return nums;
    }

    public void countSortFill(int[] nums, int start) {
        // 计数排序，适用于小范围整数
        int n = nums.length, max = 1, min = 100;
        for (int i = start; i < n; i += 2) {
            max = Math.max(max, nums[i]);
            min = Math.min(min, nums[i]);
        }
        int[] counts = new int[max - min + 1];
        for (int i = start; i < n; i += 2) {
            counts[nums[i] - min]++;
        }
        int idx = start;
        if (start == 0) {
            for (int i = 0; i < counts.length; i++) {
                while (counts[i]-- > 0) {
                    nums[idx] = i + min;
                    idx += 2;
                }
                if (idx > n) {
                    break;
                }
            }
        } else {
            for (int i = counts.length - 1; i >= 0; i--) {
                while (counts[i]-- > 0) {
                    nums[idx] = i + min;
                    idx += 2;
                }
                if (idx > n) {
                    break;
                }
            }
        }
    }

    /**
     * 1897. 重新分配字符使所有字符串都相等
     * 给你一个字符串数组 words（下标 从 0 开始 计数）。
     * 在一步操作中，需先选出两个 不同 下标 i 和 j，其中 words[i] 是一个非空字符串，接着将 words[i] 中的 任一 字符移动到 words[j] 中的 任一 位置上。
     * 如果执行任意步操作可以使 words 中的每个字符串都相等，返回 true ；否则，返回 false 。
     * 示例 1：
     * 输入：words = ["abc","aabc","bc"]
     * 输出：true
     * 解释：将 words[1] 中的第一个 'a' 移动到 words[2] 的最前面。
     * 使 words[1] = "abc" 且 words[2] = "abc" 。
     * 所有字符串都等于 "abc" ，所以返回 true 。
     * 示例 2：
     * 输入：words = ["ab","a"]
     * 输出：false
     * 解释：执行操作无法使所有字符串都相等。
     * 提示：
     * 1 <= words.length <= 100
     * 1 <= words[i].length <= 100
     * words[i] 由小写英文字母组成
     */
    public boolean makeEqual(String[] words) {
        int[] counts = new int[26];
        for (String word : words) {
            for (char c : word.toCharArray()) {
                counts[c - 'a']++;
            }
        }
        for (int count : counts) {
            if (count % words.length != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 2068. 检查两个字符串是否几乎相等
     * 如果两个字符串 word1 和 word2 中从 'a' 到 'z' 每一个字母出现频率之差都 不超过 3 ，那么我们称这两个字符串 word1 和 word2 几乎相等 。
     * 给你两个长度都为 n 的字符串 word1 和 word2 ，如果 word1 和 word2 几乎相等 ，请你返回 true ，否则返回 false 。
     * 一个字母 x 的出现 频率 指的是它在字符串中出现的次数。
     * 示例 1：
     * 输入：word1 = "aaaa", word2 = "bccb"
     * 输出：false
     * 解释：字符串 "aaaa" 中有 4 个 'a' ，但是 "bccb" 中有 0 个 'a' 。
     * 两者之差为 4 ，大于上限 3 。
     * 示例 2：
     * 输入：word1 = "abcdeef", word2 = "abaaacc"
     * 输出：true
     * 解释：word1 和 word2 中每个字母出现频率之差至多为 3 ：
     * - 'a' 在 word1 中出现了 1 次，在 word2 中出现了 4 次，差为 3 。
     * - 'b' 在 word1 中出现了 1 次，在 word2 中出现了 1 次，差为 0 。
     * - 'c' 在 word1 中出现了 1 次，在 word2 中出现了 2 次，差为 1 。
     * - 'd' 在 word1 中出现了 1 次，在 word2 中出现了 0 次，差为 1 。
     * - 'e' 在 word1 中出现了 2 次，在 word2 中出现了 0 次，差为 2 。
     * - 'f' 在 word1 中出现了 1 次，在 word2 中出现了 0 次，差为 1 。
     * 示例 3：
     * 输入：word1 = "cccddabba", word2 = "babababab"
     * 输出：true
     * 解释：word1 和 word2 中每个字母出现频率之差至多为 3 ：
     * - 'a' 在 word1 中出现了 2 次，在 word2 中出现了 4 次，差为 2 。
     * - 'b' 在 word1 中出现了 2 次，在 word2 中出现了 5 次，差为 3 。
     * - 'c' 在 word1 中出现了 3 次，在 word2 中出现了 0 次，差为 3 。
     * - 'd' 在 word1 中出现了 2 次，在 word2 中出现了 0 次，差为 2 。
     * 提示：
     * n == word1.length == word2.length
     * 1 <= n <= 100
     * word1 和 word2 都只包含小写英文字母。
     */
    public boolean checkAlmostEquivalent(String word1, String word2) {
        int[] counts = new int[26];
        for (char c : word1.toCharArray()) {
            counts[c - 'a']++;
        }
        for (char c : word2.toCharArray()) {
            counts[c - 'a']--;
        }
        for (int count : counts) {
            if (Math.abs(count) > 3) {
                return false;
            }
        }
        return true;
    }

    /**
     * 2535. 数组元素和与数字和的绝对差
     * 给你一个正整数数组 nums 。
     * 元素和 是 nums 中的所有元素相加求和。
     * 数字和 是 nums 中每一个元素的每一数位（重复数位需多次求和）相加求和。
     * 返回 元素和 与 数字和 的绝对差。
     * 注意：两个整数 x 和 y 的绝对差定义为 |x - y| 。
     * 示例 1：
     * 输入：nums = [1,15,6,3]
     * 输出：9
     * 解释：
     * nums 的元素和是 1 + 15 + 6 + 3 = 25 。
     * nums 的数字和是 1 + 1 + 5 + 6 + 3 = 16 。
     * 元素和与数字和的绝对差是 |25 - 16| = 9 。
     * 示例 2：
     * 输入：nums = [1,2,3,4]
     * 输出：0
     * 解释：
     * nums 的元素和是 1 + 2 + 3 + 4 = 10 。
     * nums 的数字和是 1 + 2 + 3 + 4 = 10 。
     * 元素和与数字和的绝对差是 |10 - 10| = 0 。
     * 提示：
     * 1 <= nums.length <= 2000
     * 1 <= nums[i] <= 2000
     */
    public int differenceOfSum(int[] nums) {
        int esum = 0, nsum = 0;
        for (int num : nums) {
            esum += num;
            while (num > 0) {
                nsum += num % 10;
                num /= 10;
            }
        }
        return esum - nsum;
    }

    /**
     * 3498. 字符串的反转度
     * 给你一个字符串 s，计算其 反转度。
     * 反转度的计算方法如下：
     * 对于每个字符，将其在 反转 字母表中的位置（'a' = 26, 'b' = 25, ..., 'z' = 1）与其在字符串中的位置（下标从1 开始）相乘。
     * 将这些乘积加起来，得到字符串中所有字符的和。
     * 返回 反转度。
     * 示例 1：
     * 输入： s = "abc"
     * 输出： 148
     * 解释：
     * 字母	反转字母表中的位置	字符串中的位置	乘积
     * 'a'	26	1	26
     * 'b'	25	2	50
     * 'c'	24	3	72
     * 反转度是 26 + 50 + 72 = 148 。
     * 示例 2：
     * 输入： s = "zaza"
     * 输出： 160
     * 解释：
     * 字母	反转字母表中的位置	字符串中的位置	乘积
     * 'z'	1	1	1
     * 'a'	26	2	52
     * 'z'	1	3	3
     * 'a'	26	4	104
     * 反转度是 1 + 52 + 3 + 104 = 160 。
     * 提示：
     * 1 <= s.length <= 1000
     * s 仅包含小写字母。
     */
    public int reverseDegree(String s) {
        int ans = 0;
        for (int i = 0; i < s.length(); i++) {
            ans += (26 - s.charAt(i) + 'a') * (i + 1);
        }
        return ans;
    }

    /**
     * LCR 075. 数组的相对排序
     * 给定两个数组，arr1 和 arr2，
     * arr2 中的元素各不相同
     * arr2 中的每个元素都出现在 arr1 中
     * 对 arr1 中的元素进行排序，使 arr1 中项的相对顺序和 arr2 中的相对顺序相同。未在 arr2 中出现过的元素需要按照升序放在 arr1 的末尾。
     * 示例：
     * 输入：arr1 = [2,3,1,3,2,4,6,7,9,2,19], arr2 = [2,1,4,3,9,6]
     * 输出：[2,2,2,1,4,3,3,9,6,7,19]
     * 提示：
     * 1 <= arr1.length, arr2.length <= 1000
     * 0 <= arr1[i], arr2[i] <= 1000
     * arr2 中的元素 arr2[i] 各不相同
     * arr2 中的每个元素 arr2[i] 都出现在 arr1 中
     */
    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        if (arr1.length == 1) {
            return arr1;
        }
        // 计数排序
        int max = 0, min = 1000;
        for (int num : arr1) {
            max = Math.max(max, num);
            min = Math.min(min, num);
        }
        int[] counts = new int[max - min + 1];
        for (int num : arr1) {
            counts[num - min]++;
        }
        int idx = 0;
        for (int num : arr2) {
            int cIdx = num - min;
            while (counts[cIdx]-- > 0) {
                arr1[idx++] = num;
            }
        }
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] <= 0) {
                continue;
            }
            int num = i + min;
            while (counts[i]-- > 0) {
                arr1[idx++] = num;
            }
        }
        return arr1;
    }

    /**
     * LCR 068. 搜索插入位置
     * 给定一个排序的整数数组 nums 和一个整数目标值 target ，请在数组中找到 target ，并返回其下标。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
     * 请必须使用时间复杂度为 O(log n) 的算法。
     * 示例 1：
     * 输入: nums = [1,3,5,6], target = 5
     * 输出: 2
     * 示例 2：
     * 输入: nums = [1,3,5,6], target = 2
     * 输出: 1
     * 示例 3：
     * 输入: nums = [1,3,5,6], target = 7
     * 输出: 4
     * 示例 4：
     * 输入: nums = [1,3,5,6], target = 0
     * 输出: 0
     * 示例 5：
     * 输入: nums = [1], target = 0
     * 输出: 0
     * 提示：
     * 1 <= nums.length <= 10^4
     * -10^4 <= nums[i] <= 10^4
     * nums 为无重复元素的升序排列数组
     * -10^4 <= target <= 10^4
     */
    public int searchInsert(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (nums[mid] < target) {
                l = mid + 1;
            } else if (nums[mid] > target) {
                r = mid - 1;
            } else {
                return mid;
            }
        }
        return l;
    }

    /**
     * 100. 相同的树
     * 给你两棵二叉树的根节点 p 和 q ，编写一个函数来检验这两棵树是否相同。
     * 如果两个树在结构上相同，并且节点具有相同的值，则认为它们是相同的。
     * 示例 1：
     * 输入：p = [1,2,3], q = [1,2,3]
     * 输出：true
     * 示例 2：
     * 输入：p = [1,2], q = [1,null,2]
     * 输出：false
     * 示例 3：
     * 输入：p = [1,2,1], q = [1,1,2]
     * 输出：false
     * 提示：
     * 两棵树上的节点数目都在范围 [0, 100] 内
     * -10^4 <= Node.val <= 10^4
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        } else if (p == null || q == null) {
            return false;
        } else if (p.val != q.val) {
            return false;
        }
        if (!isSameTree(p.left, q.left)) {
            return false;
        }
        return isSameTree(p.right, q.right);
    }

    /**
     * 面试题 05.01. 插入
     * 给定两个整型数字 N 与 M，以及表示比特位置的 i 与 j（i <= j，且从 0 位开始计算）。
     * 编写一种方法，使 M 对应的二进制数字插入 N 对应的二进制数字的第 i ~ j 位区域，不足之处用 0 补齐。具体插入过程如图所示。
     * 题目保证从 i 位到 j 位足以容纳 M， 例如： M = 10011，则 i～j 区域至少可容纳 5 位。
     * 示例 1：
     * 输入：N = 1024(10000000000), M = 19(10011), i = 2, j = 6
     * 输出：N = 1100(10001001100)
     * 示例 2：
     * 输入：N = 0, M = 31(11111), i = 0, j = 4
     * 输出：N = 31(11111)
     */
    public int insertBits(int N, int M, int i, int j) {
        int l = N >> j >> 1 << 1 << j; // 右移j+1再左移j+1,将j位（包含）之前的位置0
        int r = ((1 << i) - 1) & N; // (1 << i) - 1为了构造i个值位1的位，&N得到了后半截
        int m = M << i;
        return l | r | m;
    }

    /**
     * 258. 各位相加
     * 给定一个非负整数 num，反复将各个位上的数字相加，直到结果为一位数。返回这个结果。
     * 示例 1:
     * 输入: num = 38
     * 输出: 2
     * 解释: 各位相加的过程为：
     * 38 --> 3 + 8 --> 11
     * 11 --> 1 + 1 --> 2
     * 由于 2 是一位数，所以返回 2。
     * 示例 2:
     * 输入: num = 0
     * 输出: 0
     * 提示：
     * 0 <= num <= 2^31 - 1
     * 进阶：你可以不使用循环或者递归，在 O(1) 时间复杂度内解决这个问题吗？
     */
    public int addDigits(int num) {
        return num % 9 == 0 ? num == 0 ? 0 : 9 : num % 9;
    }

    /**
     * LCP 28. 采购方案
     * 小力将 N 个零件的报价存于数组 nums。小力预算为 target，假定小力仅购买两个零件，要求购买零件的花费不超过预算，请问他有多少种采购方案。
     * 注意：答案需要以 1e9 + 7 (1000000007) 为底取模，如：计算初始结果为：1000000008，请返回 1
     * 示例 1：
     * 输入：nums = [2,5,3,5], target = 6
     * 输出：1
     * 解释：预算内仅能购买 nums[0] 与 nums[2]。
     * 示例 2：
     * 输入：nums = [2,2,1,9], target = 10
     * 输出：4
     * 解释：符合预算的采购方案如下： nums[0] + nums[1] = 4 nums[0] + nums[2] = 3 nums[1] + nums[2] = 3 nums[2] + nums[3] = 10
     * 提示：
     * 2 <= nums.length <= 10^5
     * 1 <= nums[i], target <= 10^5
     */
    public int purchasePlans(int[] nums, int target) {
        Arrays.sort(nums);
        int ans = 0, mod = 1000000007, left = 0, right = nums.length - 1;
        while (left < right) {
            if (nums[left] + nums[right] > target) {
                right--;
            } else {
                ans = (ans + right - left) % mod;
                left++;
            }
        }
        return ans;
    }

    /**
     * 1337. 矩阵中战斗力最弱的 K 行
     * 给你一个大小为 m * n 的矩阵 mat，矩阵由若干军人和平民组成，分别用 1 和 0 表示。
     * 请你返回矩阵中战斗力最弱的 k 行的索引，按从最弱到最强排序。
     * 如果第 i 行的军人数量少于第 j 行，或者两行军人数量相同但 i 小于 j，那么我们认为第 i 行的战斗力比第 j 行弱。
     * 军人 总是 排在一行中的靠前位置，也就是说 1 总是出现在 0 之前。
     * 示例 1：
     * 输入：mat =
     * [[1,1,0,0,0],
     * [1,1,1,1,0],
     * [1,0,0,0,0],
     * [1,1,0,0,0],
     * [1,1,1,1,1]],
     * k = 3
     * 输出：[2,0,3]
     * 解释：
     * 每行中的军人数目：
     * 行 0 -> 2
     * 行 1 -> 4
     * 行 2 -> 1
     * 行 3 -> 2
     * 行 4 -> 5
     * 从最弱到最强对这些行排序后得到 [2,0,3,1,4]
     * 示例 2：
     * 输入：mat =
     * [[1,0,0,0],
     * [1,1,1,1],
     * [1,0,0,0],
     * [1,0,0,0]],
     * k = 2
     * 输出：[0,2]
     * 解释：
     * 每行中的军人数目：
     * 行 0 -> 1
     * 行 1 -> 4
     * 行 2 -> 1
     * 行 3 -> 1
     * 从最弱到最强对这些行排序后得到 [0,2,3,1]
     * 提示：
     * m == mat.length
     * n == mat[i].length
     * 2 <= n, m <= 100
     * 1 <= k <= m
     * matrix[i][j] 不是 0 就是 1
     */
    public int[] kWeakestRows(int[][] mat, int k) {
        int[] ans = new int[k];
        int m = mat.length, n = mat[0].length, an = ans.length;
        boolean[] picked = new boolean[m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (mat[j][i] == 0 && !picked[j]) {
                    ans[an - k] = j;
                    picked[j] = true;
                    k--;
                    if (k == 0) {
                        return ans;
                    }
                }
            }
        }

        for (int i = 0; i < picked.length; i++) {
            if (!picked[i]) {
                ans[an - k] = i;
                k--;
                if (k == 0) {
                    break;
                }
            }
        }
        return ans;
    }

    /**
     * LCR 169. 招式拆解 II
     * 某套连招动作记作仅由小写字母组成的序列 arr，其中 arr[i] 第 i 个招式的名字。请返回第一个只出现一次的招式名称，如不存在请返回空格。
     * 示例 1：
     * 输入：arr = "abbccdeff"
     * 输出：'a'
     * 示例 2：
     * 输入：arr = "ccdd"
     * 输出：' '
     * 限制：
     * 0 <= arr.length <= 50000
     */
    public char dismantlingAction(String arr) {
        int[] counts = new int[26];
        List<Character> cList = new ArrayList<>();
        for (char c : arr.toCharArray()) {
            int i = c - 'a';
            if (counts[i] == 0) {
                cList.add(c);
            }
            counts[i]++;
        }
        for (char c : cList) {
            if (counts[c - 'a'] == 1) {
                return c;
            }
        }
        return ' ';
    }

    /**
     * LCR 120. 寻找文件副本
     * 设备中存有 n 个文件，文件 id 记于数组 documents。若文件 id 相同，则定义为该文件存在副本。请返回任一存在副本的文件 id。
     * 示例 1：
     * 输入：documents = [2, 5, 3, 0, 5, 0]
     * 输出：0 或 5
     * 提示：
     * 0 ≤ documents[i] ≤ n-1
     * 2 <= n <= 100000
     */
    public int findRepeatDocument(int[] documents) {
        Map<Integer, Boolean> map = new HashMap<>();
        for (int doc : documents) {
            if (map.containsKey(doc)) {
                return doc;
            }
            map.put(doc, true);
        }
        return -1;
    }

    /**
     * 1534. 统计好三元组
     * 给你一个整数数组 arr ，以及 a、b 、c 三个整数。请你统计其中好三元组的数量。
     * 如果三元组 (arr[i], arr[j], arr[k]) 满足下列全部条件，则认为它是一个 好三元组 。
     * 0 <= i < j < k < arr.length
     * |arr[i] - arr[j]| <= a
     * |arr[j] - arr[k]| <= b
     * |arr[i] - arr[k]| <= c
     * 其中 |x| 表示 x 的绝对值。
     * 返回 好三元组的数量 。
     * 示例 1：
     * 输入：arr = [3,0,1,1,9,7], a = 7, b = 2, c = 3
     * 输出：4
     * 解释：一共有 4 个好三元组：[(3,0,1), (3,0,1), (3,1,1), (0,1,1)] 。
     * 示例 2：
     * 输入：arr = [1,1,2,2,3], a = 0, b = 0, c = 1
     * 输出：0
     * 解释：不存在满足所有条件的三元组。
     * 提示：
     * 3 <= arr.length <= 100
     * 0 <= arr[i] <= 1000
     * 0 <= a, b, c <= 1000
     */
    public int countGoodTriplets(int[] arr, int a, int b, int c) {
        int n = arr.length, ans = 0;
        for (int i = 0; i <= n - 3; i++) {
            for (int j = i + 1; j <= n - 2; j++) {
                if (Math.abs(arr[i] - arr[j]) > a) {
                    continue;
                }
                for (int k = j + 1; k <= n - 1; k++) {
                    if (Math.abs(arr[j] - arr[k]) > b || Math.abs(arr[i] - arr[k]) > c) {
                        continue;
                    }
                    ans++;
                }
            }
        }
        return ans;
    }

    /**
     * 3264. K 次乘运算后的最终数组 I
     * 给你一个整数数组 nums ，一个整数 k  和一个整数 multiplier 。
     * 你需要对 nums 执行 k 次操作，每次操作中：
     * 找到 nums 中的 最小 值 x ，如果存在多个最小值，选择最 前面 的一个。
     * 将 x 替换为 x * multiplier 。
     * 请你返回执行完 k 次乘运算之后，最终的 nums 数组。
     * 示例 1：
     * 输入：nums = [2,1,3,5,6], k = 5, multiplier = 2
     * 输出：[8,4,6,5,6]
     * 解释：
     * 操作	结果
     * 1 次操作后	[2, 2, 3, 5, 6]
     * 2 次操作后	[4, 2, 3, 5, 6]
     * 3 次操作后	[4, 4, 3, 5, 6]
     * 4 次操作后	[4, 4, 6, 5, 6]
     * 5 次操作后	[8, 4, 6, 5, 6]
     * 示例 2：
     * 输入：nums = [1,2], k = 3, multiplier = 4
     * 输出：[16,8]
     * 解释：
     * 操作	结果
     * 1 次操作后	[4, 2]
     * 2 次操作后	[4, 8]
     * 3 次操作后	[16, 8]
     * 提示：
     * 1 <= nums.length <= 100
     * 1 <= nums[i] <= 100
     * 1 <= k <= 10
     * 1 <= multiplier <= 5
     */
    public int[] getFinalState(int[] nums, int k, int multiplier) {
        int n = nums.length;
        while (k > 0) {
            int mi = 0;
            for (int i = 1; i < n; i++) {
                if (nums[i] < nums[mi]) {
                    mi = i;
                }
            }
            nums[mi] *= multiplier;
            k--;
        }
        return nums;
    }

    /**
     * 3370. 仅含置位位的最小整数
     * 给你一个正整数 n。
     * 返回 大于等于 n 且二进制表示仅包含 置位 位的 最小 整数 x 。
     * 置位 位指的是二进制表示中值为 1 的位。
     * 示例 1：
     * 输入： n = 5
     * 输出： 7
     * 解释：
     * 7 的二进制表示是 "111"。
     * 示例 2：
     * 输入： n = 10
     * 输出： 15
     * 解释：
     * 15 的二进制表示是 "1111"。
     * 示例 3：
     * 输入： n = 3
     * 输出： 3
     * 解释：
     * 3 的二进制表示是 "11"。
     * 提示：
     * 1 <= n <= 1000
     */
    public int smallestNumber(int n) {
        int cnt = 0;
        while (n != 0) {
            n >>= 1;
            cnt++;
        }
        return (1 << cnt) - 1;
    }

    /**
     * 999. 可以被一步捕获的棋子数
     * 给定一个 8 x 8 的棋盘，只有一个 白色的车，用字符 'R' 表示。棋盘上还可能存在白色的象 'B' 以及黑色的卒 'p'。空方块用字符 '.' 表示。
     * 车可以按水平或竖直方向（上，下，左，右）移动任意个方格直到它遇到另一个棋子或棋盘的边界。如果它能够在一次移动中移动到棋子的方格，则能够 吃掉 棋子。
     * 注意：车不能穿过其它棋子，比如象和卒。这意味着如果有其它棋子挡住了路径，车就不能够吃掉棋子。
     * 返回白车 攻击 范围内 兵的数量。
     * 示例 1：
     * 输入：
     * [
     * [".",".",".",".",".",".",".","."],
     * [".",".",".","p",".",".",".","."],
     * [".",".",".","R",".",".",".","p"],
     * [".",".",".",".",".",".",".","."],
     * [".",".",".",".",".",".",".","."],
     * [".",".",".","p",".",".",".","."],
     * [".",".",".",".",".",".",".","."],
     * [".",".",".",".",".",".",".","."]
     * ]
     * 输出：3
     * 解释：
     * 在本例中，车能够吃掉所有的卒。
     * 示例 2：
     * 输入：
     * [
     * [".",".",".",".",".",".",".","."],
     * [".","p","p","p","p","p",".","."],
     * [".","p","p","B","p","p",".","."],
     * [".","p","B","R","B","p",".","."],
     * [".","p","p","B","p","p",".","."],
     * [".","p","p","p","p","p",".","."],
     * [".",".",".",".",".",".",".","."],
     * [".",".",".",".",".",".",".","."]
     * ]
     * 输出：0
     * 解释：
     * 象阻止了车吃掉任何卒。
     * 示例 3：
     * 输入：
     * [
     * [".",".",".",".",".",".",".","."],
     * [".",".",".","p",".",".",".","."],
     * [".",".",".","p",".",".",".","."],
     * ["p","p",".","R",".","p","B","."],
     * [".",".",".",".",".",".",".","."],
     * [".",".",".","B",".",".",".","."],
     * [".",".",".","p",".",".",".","."],
     * [".",".",".",".",".",".",".","."]
     * ]
     * 输出：3
     * 解释：
     * 车可以吃掉位置 b5，d6 和 f5 的卒。
     * 提示：
     * board.length == 8
     * board[i].length == 8
     * board[i][j] 可以是 'R'，'.'，'B' 或 'p'
     * 只有一个格子上存在 board[i][j] == 'R'
     */
    private static final int[][] DIRS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    public int numRookCaptures(char[][] board) {
        // 先找到 R
        int ans = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 'R') {
                    for (int[] dir : DIRS) {
                        int x = i + dir[0], y = j + dir[1];
                        while (x >= 0 && x < 8 && y >= 0 && y < 8) {
                            if (board[x][y] != '.') {
                                if (board[x][y] == 'p') {
                                    ans++;
                                }
                                break;
                            }
                            x += dir[0];
                            y += dir[1];
                        }
                    }
                    break;
                }
            }
        }
        return ans;
    }

    /**
     * LCR 181. 字符串中的单词反转
     * 你在与一位习惯从右往左阅读的朋友发消息，他发出的文字顺序都与正常相反但单词内容正确，为了和他顺利交流你决定写一个转换程序，
     * 把他所发的消息 message 转换为正常语序。
     * 注意：输入字符串 message 中可能会存在前导空格、尾随空格或者单词间的多个空格。返回的结果字符串中，单词间应当仅用单个空格分隔，且不包含任何额外的空格。
     * 示例 1：
     * 输入: message = "the sky is blue"
     * 输出: "blue is sky the"
     * 示例 2：
     * 输入: message = "  hello world!  "
     * 输出: "world! hello"
     * 解释: 输入字符串可以在前面或者后面包含多余的空格，但是反转后的字符不能包括。
     * 示例 3：
     * 输入: message = "a good   example"
     * 输出: "example good a"
     * 解释: 如果两个单词间有多余的空格，将反转后单词间的空格减少到只含一个。
     * 提示：
     * 0 <= message.length <= 10^4
     * message 中包含英文大小写字母、空格和数字
     */
    public String reverseMessage(String message) {
        String[] words = message.trim().split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = words.length - 1; i >= 0; i--) {
            if ("".equals(words[i])) {
                continue;
            }
            sb.append(words[i]);
            if (i > 0) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    /**
     * 1768. 交替合并字符串
     * 给你两个字符串 word1 和 word2 。请你从 word1 开始，通过交替添加字母来合并字符串。如果一个字符串比另一个字符串长，就将多出来的字母追加到合并后字符串的末尾。
     * 返回 合并后的字符串 。
     * 示例 1：
     * 输入：word1 = "abc", word2 = "pqr"
     * 输出："apbqcr"
     * 解释：字符串合并情况如下所示：
     * word1：  a   b   c
     * word2：    p   q   r
     * 合并后：  a p b q c r
     * 示例 2：
     * 输入：word1 = "ab", word2 = "pqrs"
     * 输出："apbqrs"
     * 解释：注意，word2 比 word1 长，"rs" 需要追加到合并后字符串的末尾。
     * word1：  a   b
     * word2：    p   q   r   s
     * 合并后：  a p b q   r   s
     * 示例 3：
     * 输入：word1 = "abcd", word2 = "pq"
     * 输出："apbqcd"
     * 解释：注意，word1 比 word2 长，"cd" 需要追加到合并后字符串的末尾。
     * word1：  a   b   c   d
     * word2：    p   q
     * 合并后：  a p b q c   d
     * 提示：
     * 1 <= word1.length, word2.length <= 100
     * word1 和 word2 由小写英文字母组成
     */
    public String mergeAlternately(String word1, String word2) {
        StringBuilder sb = new StringBuilder();
        int len1 = word1.length(), len2 = word2.length();
        for (int i = 0; i < Math.max(len1, len2); i++) {
            if (i < len1) {
                sb.append(word1.charAt(i));
            }
            if (i < len2) {
                sb.append(word2.charAt(i));
            }
        }
        return sb.toString();
    }

    /**
     * LCR 139. 训练计划 I
     * 教练使用整数数组 actions 记录一系列核心肌群训练项目编号。为增强训练趣味性，需要将所有奇数编号训练项目调整至偶数编号训练项目之前。
     * 请将调整后的训练项目编号以 数组 形式返回。
     * 示例 1：
     * 输入：actions = [1,2,3,4,5]
     * 输出：[1,3,5,2,4]
     * 解释：为正确答案之一
     * 提示：
     * 0 <= actions.length <= 50000
     * 0 <= actions[i] <= 10000
     */
    public int[] trainingPlan(int[] actions) {
        int idx = 0;
        for (int i = 0; i < actions.length; i++) {
            while ((actions[i] & 1) == 1 && i != idx) {
                int tmp = actions[idx];
                actions[idx++] = actions[i];
                actions[i] = tmp;
            }
        }
        return actions;
    }

    /**
     * 2639. 查询网格图中每一列的宽度
     * 给你一个下标从 0 开始的 m x n 整数矩阵 grid 。矩阵中某一列的宽度是这一列数字的最大 字符串长度 。
     * 比方说，如果 grid = [[-10], [3], [12]] ，那么唯一一列的宽度是 3 ，因为 -10 的字符串长度为 3 。
     * 请你返回一个大小为 n 的整数数组 ans ，其中 ans[i] 是第 i 列的宽度。
     * 一个有 len 个数位的整数 x ，如果是非负数，那么 字符串长度 为 len ，否则为 len + 1 。
     * 示例 1：
     * 输入：grid = [[1],[22],[333]]
     * 输出：[3]
     * 解释：第 0 列中，333 字符串长度为 3 。
     * 示例 2：
     * 输入：grid = [[-15,1,3],[15,7,12],[5,6,-2]]
     * 输出：[3,1,2]
     * 解释：
     * 第 0 列中，只有 -15 字符串长度为 3 。
     * 第 1 列中，所有整数的字符串长度都是 1 。
     * 第 2 列中，12 和 -2 的字符串长度都为 2 。
     * 提示：
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 100
     * -10^9 <= grid[r][c] <= 10^9
     */
    public int[] findColumnWidth(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            int maxWidth = 0;
            for (int j = 0; j < m; j++) {
                maxWidth = Math.max(maxWidth, getWidth(grid[j][i]));
            }
            ans[i] = maxWidth;
        }
        return ans;
    }

    private int getWidth(int num) {
        int width = num <= 0 ? 1 : 0;
        while (num != 0) {
            num /= 10;
            width++;
        }
        return width;
    }

    /**
     * LCR 133. 位 1 的个数
     * 编写一个函数，输入是一个无符号整数（以二进制串的形式），返回其二进制表达式中数字位数为 '1' 的个数（也被称为 汉明重量).）。
     * 提示：
     * 请注意，在某些语言（如 Java）中，没有无符号整数类型。在这种情况下，输入和输出都将被指定为有符号整数类型，
     * 并且不应影响您的实现，因为无论整数是有符号的还是无符号的，其内部的二进制表示形式都是相同的。
     * 在 Java 中，编译器使用 二进制补码 记法来表示有符号整数。因此，在上面的 示例 3 中，输入表示有符号整数 -3。
     * 示例 1：
     * 输入：n = 11 (控制台输入 00000000000000000000000000001011)
     * 输出：3
     * 解释：输入的二进制串 00000000000000000000000000001011 中，共有三位为 '1'。
     * 示例 2：
     * 输入：n = 128 (控制台输入 00000000000000000000000010000000)
     * 输出：1
     * 解释：输入的二进制串 00000000000000000000000010000000 中，共有一位为 '1'。
     * 示例 3：
     * 输入：n = 4294967293 (控制台输入 11111111111111111111111111111101，部分语言中 n = -3）
     * 输出：31
     * 解释：输入的二进制串 11111111111111111111111111111101 中，共有 31 位为 '1'。
     * 提示：
     * 输入必须是长度为 32 的 二进制串 。
     */
    // you need to treat n as an unsigned value
    public int hammingWeight(int n) {
        return Integer.bitCount(n);
    }

    /**
     * LCR 141. 训练计划 III
     * 给定一个头节点为 head 的单链表用于记录一系列核心肌群训练编号，请将该系列训练编号 倒序 记录于链表并返回。
     * 示例 1：
     * 输入：head = [1,2,3,4,5]
     * 输出：[5,4,3,2,1]
     * 示例 2：
     * 输入：head = [1,2]
     * 输出：[2,1]
     * 示例 3：
     * 输入：head = []
     * 输出：[]
     * 提示：
     * 链表中节点的数目范围是 [0, 5000]
     * -5000 <= Node.val <= 5000
     */
    public ListNode trainningPlan(ListNode head) {
        ListNode pre = null, next;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    /**
     * 1475. 商品折扣后的最终价格
     * 给你一个数组 prices ，其中 prices[i] 是商店里第 i 件商品的价格。
     * 商店里正在进行促销活动，如果你要买第 i 件商品，那么你可以得到与 prices[j] 相等的折扣，
     * 其中 j 是满足 j > i 且 prices[j] <= prices[i] 的 最小下标 ，如果没有满足条件的 j ，你将没有任何折扣。
     * 请你返回一个数组，数组中第 i 个元素是折扣后你购买商品 i 最终需要支付的价格。
     * 示例 1：
     * 输入：prices = [8,4,6,2,3]
     * 输出：[4,2,4,2,3]
     * 解释：
     * 商品 0 的价格为 price[0]=8 ，你将得到 prices[1]=4 的折扣，所以最终价格为 8 - 4 = 4 。
     * 商品 1 的价格为 price[1]=4 ，你将得到 prices[3]=2 的折扣，所以最终价格为 4 - 2 = 2 。
     * 商品 2 的价格为 price[2]=6 ，你将得到 prices[3]=2 的折扣，所以最终价格为 6 - 2 = 4 。
     * 商品 3 和 4 都没有折扣。
     * 示例 2：
     * 输入：prices = [1,2,3,4,5]
     * 输出：[1,2,3,4,5]
     * 解释：在这个例子中，所有商品都没有折扣。
     * 示例 3：
     * 输入：prices = [10,1,1,6]
     * 输出：[9,0,1,6]
     * 提示：
     * 1 <= prices.length <= 500
     * 1 <= prices[i] <= 10^3
     */
    public int[] finalPrices(int[] prices) {
        int n = prices.length;
        int[] ans = new int[n];
        Deque<Integer> stack = new LinkedList<>();
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && stack.peek() > prices[i]) {
                stack.pop();
            }
            int discount = stack.isEmpty() ? 0 : stack.peek();
            ans[i] = prices[i] - discount;
            stack.push(prices[i]);
        }
        return ans;
    }

    /**
     * 3304. 找出第 K 个字符 I
     * Alice 和 Bob 正在玩一个游戏。最初，Alice 有一个字符串 word = "a"。
     * 给定一个正整数 k。
     * 现在 Bob 会要求 Alice 执行以下操作 无限次 :
     * 将 word 中的每个字符 更改 为英文字母表中的 下一个 字符来生成一个新字符串，并将其 追加 到原始的 word。
     * 例如，对 "c" 进行操作生成 "cd"，对 "zb" 进行操作生成 "zbac"。
     * 在执行足够多的操作后， word 中 至少 存在 k 个字符，此时返回 word 中第 k 个字符的值。
     * 注意，在操作中字符 'z' 可以变成 'a'。
     * 示例 1:
     * 输入：k = 5
     * 输出："b"
     * 解释：
     * 最初，word = "a"。需要进行三次操作:
     * 生成的字符串是 "b"，word 变为 "ab"。
     * 生成的字符串是 "bc"，word 变为 "abbc"。
     * 生成的字符串是 "bccd"，word 变为 "abbcbccd"。
     * 示例 2:
     * 输入：k = 10
     * 输出："c"
     * 提示：
     * <p>
     * 1 <= k <= 500
     */
    public char kthCharacter(int k) {
        // a ab abbc abbcbccd abbcbccdbccdcdde abbcbccdbccdcdde-bccd
        if (k == 1) {
            return 'a';
        }
        char[] cs = new char[k];
        cs[0] = 'a';
        int idx = 1, n = 1;
        while (idx < k) {
            for (int j = 0; j < n && idx < k; j++) {
                cs[idx++] = (char) (cs[j] + 1);
            }
            n *= 2;
        }
        return cs[k - 1];
    }

    /**
     * 2144. 打折购买糖果的最小开销
     * 一家商店正在打折销售糖果。每购买 两个 糖果，商店会 免费 送一个糖果。
     * 免费送的糖果唯一的限制是：它的价格需要小于等于购买的两个糖果价格的 较小值 。
     * 比方说，总共有 4 个糖果，价格分别为 1 ，2 ，3 和 4 ，一位顾客买了价格为 2 和 3 的糖果，那么他可以免费获得价格为 1 的糖果，但不能获得价格为 4 的糖果。
     * 给你一个下标从 0 开始的整数数组 cost ，其中 cost[i] 表示第 i 个糖果的价格，请你返回获得 所有 糖果的 最小 总开销。
     * 示例 1：
     * 输入：cost = [1,2,3]
     * 输出：5
     * 解释：我们购买价格为 2 和 3 的糖果，然后免费获得价格为 1 的糖果。
     * 总开销为 2 + 3 = 5 。这是开销最小的 唯一 方案。
     * 注意，我们不能购买价格为 1 和 3 的糖果，并免费获得价格为 2 的糖果。
     * 这是因为免费糖果的价格必须小于等于购买的 2 个糖果价格的较小值。
     * 示例 2：
     * 输入：cost = [6,5,7,9,2,2]
     * 输出：23
     * 解释：最小总开销购买糖果方案为：
     * - 购买价格为 9 和 7 的糖果
     * - 免费获得价格为 6 的糖果
     * - 购买价格为 5 和 2 的糖果
     * - 免费获得价格为 2 的最后一个糖果
     * 因此，最小总开销为 9 + 7 + 5 + 2 = 23 。
     * 示例 3：
     * 输入：cost = [5,5]
     * 输出：10
     * 解释：由于只有 2 个糖果，我们需要将它们都购买，而且没有免费糖果。
     * 所以总最小开销为 5 + 5 = 10 。
     * 提示：
     * 1 <= cost.length <= 100
     * 1 <= cost[i] <= 100
     */
    public int minimumCost(int[] cost) {
        Arrays.sort(cost);
        int idx = cost.length - 1, ans = 0;
        while (idx >= 0) {
            ans += cost[idx--];
            if (idx >= 0) {
                ans += cost[idx--];
            }
            idx--;
        }
        return ans;
    }

    /**
     * 387. 字符串中的第一个唯一字符
     * 给定一个字符串 s ，找到 它的第一个不重复的字符，并返回它的索引 。如果不存在，则返回 -1 。
     * 示例 1：
     * 输入: s = "leetcode"
     * 输出: 0
     * 示例 2:
     * 输入: s = "loveleetcode"
     * 输出: 2
     * 示例 3:
     * 输入: s = "aabb"
     * 输出: -1
     * 提示:
     * 1 <= s.length <= 10^5
     * s 只包含小写字母
     */
    public int firstUniqChar(String s) {
        int[] counts = new int[26];
        char[] cs = s.toCharArray();
        for (char c : cs) {
            counts[c - 'a']++;
        }
        for (int i = 0; i < cs.length; i++) {
            if (counts[cs[i] - 'a'] == 1) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
    }
}
