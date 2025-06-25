package io.github.xqplus.leetcode;

import java.util.*;

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
     * 3317. 安排活动的方案数
     * 给你三个整数 n ，x 和 y 。
     * 一个活动总共有 n 位表演者。每一位表演者会 被安排 到 x 个节目之一，有可能有节目 没有 任何表演者。
     * 所有节目都安排完毕后，评委会给每一个 有表演者的 节目打分，分数是一个 [1, y] 之间的整数。
     * 请你返回 总 的活动方案数。
     * 答案可能很大，请你将它对 109 + 7 取余 后返回。
     * 注意 ，如果两个活动满足以下条件 之一 ，那么它们被视为 不同 的活动：
     * 存在 一个表演者在不同的节目中表演。
     * 存在 一个节目的分数不同。
     * 示例 1：
     * 输入：n = 1, x = 2, y = 3
     * 输出：6
     * 解释：
     * 表演者可以在节目 1 或者节目 2 中表演。
     * 评委可以给这唯一一个有表演者的节目打分 1 ，2 或者 3 。
     * 示例 2：
     * 输入：n = 5, x = 2, y = 1
     * 输出：32
     * 解释：
     * 每一位表演者被安排到节目 1 或者 2 。
     * 所有的节目分数都为 1 。
     * 示例 3：
     * 输入：n = 3, x = 3, y = 4 3*3*3=27  111 112 113 121 122 123 131 132 133  | 211 212 213 221 222 223 231 232 233 | 3..
     * 输出：684
     * 提示：
     * 1 <= n, x, y <= 1000
     */
    private static final int MOD = 1_000_000_007;
    private static final int MX = 1001;
    private static final int[][] s = new int[MX][MX];

    static {
        s[0][0] = 1;
        for (int i = 1; i < MX; i++) {
            for (int j = 1; j <= i; j++) {
                s[i][j] = (int) ((s[i - 1][j - 1] + (long) j * s[i - 1][j]) % MOD);
            }
        }
    }

    public int numberOfWays(int n, int x, int y) {
        long ans = 0;
        long perm = 1;
        long powY = 1;
        for (int i = 1; i <= Math.min(n, x); i++) {
            perm = perm * (x + 1 - i) % MOD;
            powY = powY * y % MOD;
            ans = (ans + perm * s[n][i] % MOD * powY) % MOD;
        }
        return (int) ans;
    }

    /**
     * 480. 滑动窗口中位数
     * 中位数是有序序列最中间的那个数。如果序列的长度是偶数，则没有最中间的数；此时中位数是最中间的两个数的平均数。
     * 例如：
     * [2,3,4]，中位数是 3
     * [2,3]，中位数是 (2 + 3) / 2 = 2.5
     * 给你一个数组 nums，有一个长度为 k 的窗口从最左端滑动到最右端。窗口中有 k 个数，每次窗口向右移动 1 位。
     * 你的任务是找出每次窗口移动后得到的新窗口中元素的中位数，并输出由它们组成的数组。
     * 示例：
     * 给出 nums = [1,3,-1,-3,5,3,6,7]，以及 k = 3。
     * 窗口位置                      中位数
     * ---------------               -----
     * [1  3  -1] -3  5  3  6  7       1
     * 1 [3  -1  -3] 5  3  6  7      -1
     * 1  3 [-1  -3  5] 3  6  7      -1
     * 1  3  -1 [-3  5  3] 6  7       3
     * 1  3  -1  -3 [5  3  6] 7       5
     * 1  3  -1  -3  5 [3  6  7]      6
     * 因此，返回该滑动窗口的中位数数组 [1,-1,-1,3,5,6]。
     * 提示：
     * 你可以假设 k 始终有效，即：k 始终小于等于输入的非空数组的元素个数。
     * 与真实值误差在 10 ^ -5 以内的答案将被视作正确答案。
     */
    private static Queue<Integer> spq = new PriorityQueue<>();
    private static Queue<Integer> lpq = new PriorityQueue<>();

    public static double[] medianSlidingWindow(int[] nums, int k) {
//        int n = nums.length;
//        double[] ans = new double[n - k + 1];
//        Map<Integer, Integer> map = new TreeMap<>();
//        for (int i = 0; i < k - 1; i++) {
//            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
//        }
//        int cnt1 = k + 1 >> 1, cnt2 = (k & 1) == 1 ? cnt1 : cnt1 + 1;
//        for (int i = 0; i <= n - k; i++) {
//            if (i > 0) {
//                int npre = map.get(nums[i - 1]);
//                if (npre == 1) {
//                    map.remove(nums[i - 1]);
//                } else {
//                    map.put(nums[i - 1], npre - 1);
//                }
//            }
//            map.put(nums[i + k - 1], map.getOrDefault(nums[i + k - 1], 0) + 1);
//            double num1 = 0, num2 = 0;
//            int cnt = 0;
//            boolean num1Set = false;
//            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
//                cnt += entry.getValue();
//                if (!num1Set && cnt >= cnt1) { // cnt1 <= cnt2, num1Set解决的是两个中位数位于两个不同数的情况，避免重置num1
//                    num1 = entry.getKey();
//                    num1Set = true;
//                }
//                if (cnt >= cnt2) {
//                    num2 = entry.getKey();
//                    break;
//                }
//            }
//            ans[i] = (num1 + num2) / 2;
//        }
//        return ans; // 一个用例超时

        // 思路：维护较小的一部分数（k/2+k%2）做大根堆spq，另一部分数（k/2）做小根堆lqp
        // tops为spq堆顶元素，topl同理，则每个窗口中位数出现在tops或者(tops+topl)/2D
        // 每次窗口滑动需要新增一个新元素，insert(int num)，移除一个spq中最小元素，remove(int num)

        return null;
    }

    /**
     * 3260. 找出最大的 N 位 K 回文数
     * 给你两个 正整数 n 和 k。
     * 如果整数 x 满足以下全部条件，则该整数是一个 k 回文数：
     * x 是一个 回文数。
     * x 可以被 k 整除。
     * 以字符串形式返回 最大的  n 位 k 回文数。
     * 注意，该整数 不 含前导零。
     * 示例 1：
     * 输入： n = 3, k = 5
     * 输出： "595"
     * 解释：
     * 595 是最大的 3 位 k 回文数。
     * 示例 2：
     * 输入： n = 1, k = 4
     * 输出： "8"
     * 解释：
     * 1 位 k 回文数只有 4 和 8。
     * 示例 3：
     * 输入： n = 5, k = 6
     * 输出： "89898"
     * 提示：
     * 1 <= n <= 10^5
     * 1 <= k <= 9
     */
    public static String largestPalindrome(int n, int k) {
        // k=1,3,9 构造n个9
        // k=2 保证为偶数且回文，首尾8，中间9
        // k=4 保证最后两位能被4整除，且回文，首尾88，中间9
        // k=5 最后一位取0，5，要求回文，不能前导0，则首尾5，中间9
        // k=6 6 66 888 两位及以下n个6，大于两位：位数奇数时首尾各一位8，中间一位8，其他9；位数偶数时首尾各一位8，中间两位7，其他9
        // k=7 7 77 ？
        // k=8 最后三位能被8整除，最大首尾888，中间9

        String ans = "";
        switch (k) {
            case 1:
            case 3:
            case 9:
                ans = processK(n, 0);
                break;
            case 2:
            case 4:
            case 8:
                ans = processK(n, k);
                break;
            case 5:
                StringBuilder sb = new StringBuilder();
                sb.append('5');
                if (n > 1) {
                    for (int i = 0; i < n - 2; i++) {
                        sb.append('9');
                    }
                    sb.append('5');
                }
                ans = sb.toString();
                break;
            case 6:
                StringBuilder sb6 = new StringBuilder();
                if (n <= 2) {
                    for (int i = 0; i < n; i++) {
                        sb6.append('6');
                    }
                } else {
                    // 5 89898 n-3/2
                    // 6 897798 n-4/2
                    boolean oddN = (n & 1) == 1;
                    int n9 = (n - (oddN ? 3 : 4)) >> 1;
                    String mid = oddN ? "8" : "77";
                    sb6.append('8');
                    for (int i = 0; i < n9; i++) {
                        sb6.append('9');
                    }
                    sb6.append(mid);
                    for (int i = 0; i < n9; i++) {
                        sb6.append('9');
                    }
                    sb6.append('8');
                }
                ans = sb6.toString();
                break;
            case 7:
                StringBuilder sb7 = new StringBuilder();
                if (n <= 2) {
                    for (int i = 0; i < n; i++) {
                        sb7.append('7');
                    }
                } else {
                    int m = n >> 1;
                    boolean oddN = (n & 1) == 1;
                    for (int i = 9; i >= 0; i--) {
                        int s = 0;
                        for (int j = 0; j < n; j++) {
                            if ((oddN && j == m) || (!oddN && (j == m - 1 || j == m))) {
                                s = (s * 10 + i) % k;
                            } else {
                                s = (s * 10 + 9) % k;
                            }
                        }
                        if (s == 0) {
                            if (oddN) {
                                for (int j = 0; j < m; j++) {
                                    sb7.append('9');
                                }
                                sb7.append(i);
                                for (int j = 0; j < m; j++) {
                                    sb7.append('9');
                                }
                            } else {
                                int n7 = n - 2 >> 1;
                                for (int j = 0; j < n7; j++) {
                                    sb7.append('9');
                                }
                                sb7.append(i);
                                sb7.append(i);
                                for (int j = 0; j < n7; j++) {
                                    sb7.append('9');
                                }
                            }
                            break;
                        }
                    }
                }
                ans = sb7.toString();
                break;
        }
        return ans;
    }

    private static String processK(int n, int k) {
        StringBuilder sb = new StringBuilder();
        int n8 = k == 0 ? 0 : (int) (Math.log10(k) / Math.log10(2));
        if (n <= n8 * 2) {
            for (int i = 0; i < n; i++) {
                sb.append('8');
            }
        } else {
            for (int i = 0; i < n8; i++) {
                sb.append('8');
            }
            for (int i = 0; i < n - n8 * 2; i++) {
                sb.append('9');
            }
            for (int i = 0; i < n8; i++) {
                sb.append('8');
            }
        }
        return sb.toString();
    }

    /**
     * 1298. 你能从盒子里获得的最大糖果数
     * 给你 n 个盒子，每个盒子的格式为 [status, candies, keys, containedBoxes] ，其中：
     * 状态字 status[i]：整数，如果 box[i] 是开的，那么是 1 ，否则是 0 。
     * 糖果数 candies[i]: 整数，表示 box[i] 中糖果的数目。
     * 钥匙 keys[i]：数组，表示你打开 box[i] 后，可以得到一些盒子的钥匙，每个元素分别为该钥匙对应盒子的下标。
     * 内含的盒子 containedBoxes[i]：整数，表示放在 box[i] 里的盒子所对应的下标。
     * 给你一个整数数组 initialBoxes，包含你最初拥有的盒子。你可以拿走每个 已打开盒子 里的所有糖果，并且可以使用其中的钥匙去开启新的盒子，
     * 并且可以使用在其中发现的其他盒子。
     * 请你按照上述规则，返回可以获得糖果的 最大数目 。
     * 示例 1：
     * 输入：status = [1,0,1,0], candies = [7,5,4,100], keys = [[],[],[1],[]], containedBoxes = [[1,2],[3],[],[]], initialBoxes = [0]
     * 输出：16
     * 解释：
     * 一开始你有盒子 0 。你将获得它里面的 7 个糖果和盒子 1 和 2。
     * 盒子 1 目前状态是关闭的，而且你还没有对应它的钥匙。所以你将会打开盒子 2 ，并得到里面的 4 个糖果和盒子 1 的钥匙。
     * 在盒子 1 中，你会获得 5 个糖果和盒子 3 ，但是你没法获得盒子 3 的钥匙所以盒子 3 会保持关闭状态。
     * 你总共可以获得的糖果数目 = 7 + 4 + 5 = 16 个。
     * 示例 2：
     * 输入：status = [1,0,0,0,0,0], candies = [1,1,1,1,1,1], keys = [[1,2,3,4,5],[],[],[],[],[]],
     * containedBoxes = [[1,2,3,4,5],[],[],[],[],[]], initialBoxes = [0]
     * 输出：6
     * 解释：
     * 你一开始拥有盒子 0 。打开它你可以找到盒子 1,2,3,4,5 和它们对应的钥匙。
     * 打开这些盒子，你将获得所有盒子的糖果，所以总糖果数为 6 个。
     * 示例 3：
     * 输入：status = [1,1,1], candies = [100,1,100], keys = [[],[0,2],[]], containedBoxes = [[],[],[]], initialBoxes = [1]
     * 输出：1
     * 示例 4：
     * 输入：status = [1], candies = [100], keys = [[]], containedBoxes = [[]], initialBoxes = []
     * 输出：0
     * 示例 5：
     * 输入：status = [1,1,1], candies = [2,3,2], keys = [[],[],[]], containedBoxes = [[],[],[]], initialBoxes = [2,1,0]
     * 输出：7
     * 提示：
     * 1 <= status.length <= 1000
     * status.length == candies.length == keys.length == containedBoxes.length == n
     * status[i] 要么是 0 要么是 1 。
     * 1 <= candies[i] <= 1000
     * 0 <= keys[i].length <= status.length
     * 0 <= keys[i][j] < status.length
     * keys[i] 中的值都是互不相同的。
     * 0 <= containedBoxes[i].length <= status.length
     * 0 <= containedBoxes[i][j] < status.length
     * containedBoxes[i] 中的值都是互不相同的。
     * 每个盒子最多被一个盒子包含。
     * 0 <= initialBoxes.length <= status.length
     * 0 <= initialBoxes[i] < status.length
     */
    public static int maxCandies(int[] status, int[] candies, int[][] keys, int[][] containedBoxes, int[] initialBoxes) {
        Queue<Integer> queue = new LinkedList<>();
        for (int initialBox : initialBoxes) {
            queue.add(initialBox);
        }
        int ans = 0;
        int[] key = new int[status.length]; // 钥匙数量
        List<Integer> preBoxes = new ArrayList<>();
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> curBoxes = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Integer box = queue.poll();
                curBoxes.add(box);
                if (status[box] == 0) {
                    if (key[box] == 0) {
                        // 当前盒子关闭，也没有钥匙，继续放回队列
                        queue.add(box);
                        continue;
                    }
                    key[box]--; // 有钥匙，更新钥匙数量
                }
                ans += candies[box];
                for (int containedBox : containedBoxes[box]) {
                    // 将当前盒子下开出的盒子放入队列
                    queue.offer(containedBox);
                }
                for (int keyBox : keys[box]) {
                    // 累计当前盒子下的钥匙
                    key[keyBox]++;
                }
            }
            if (preBoxes.equals(curBoxes)) {
                // 避免死循环，如果上一轮盒子跟这一轮盒子一致，说明剩余的盒子无法开启
                break;
            }
            preBoxes = curBoxes;
        }
        return ans;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        int[] status = {1, 0, 1, 0}, candies = {7, 5, 4, 100};

        int[][] keys = new int[4][];
        keys[0] = new int[0];
        keys[1] = new int[0];
        keys[2] = new int[]{1};
        keys[3] = new int[0];

        int[][] containedBoxes = new int[4][];
        containedBoxes[0] = new int[]{1, 2};
        containedBoxes[1] = new int[]{3};
        containedBoxes[2] = new int[0];
        containedBoxes[3] = new int[0];

        int[] initialBoxes = {0};
        System.out.println(maxCandies(status, candies, keys, containedBoxes, initialBoxes));
    }
}
