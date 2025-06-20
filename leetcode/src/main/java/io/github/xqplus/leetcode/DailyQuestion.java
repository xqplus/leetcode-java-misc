package io.github.xqplus.leetcode;

import com.sun.jmx.snmp.SnmpUnknownModelLcdException;

import java.util.*;

public class DailyQuestion {

    /**
     * 3403. 从盒子中找出字典序最大的字符串 I
     * 中等
     * 给你一个字符串 word 和一个整数 numFriends。
     * Alice 正在为她的 numFriends 位朋友组织一个游戏。游戏分为多个回合，在每一回合中：
     * word 被分割成 numFriends 个 非空 字符串，且该分割方式与之前的任意回合所采用的都 不完全相同 。
     * 所有分割出的字符串都会被放入一个盒子中。
     * 在所有回合结束后，找出盒子中 字典序最大的 字符串。
     * 示例 1：
     * 输入: word = "dbca", numFriends = 2
     * 输出: "dbc"
     * 解释:
     * 所有可能的分割方式为：
     * "d" 和 "bca"。
     * "db" 和 "ca"。
     * "dbc" 和 "a"。
     * 示例 2：
     * 输入: word = "gggg", numFriends = 4
     * 输出: "g"
     * 解释:
     * 唯一可能的分割方式为："g", "g", "g", 和 "g"。
     * 提示:
     * 1 <= word.length <= 5 * 10^3
     * word 仅由小写英文字母组成。
     * 1 <= numFriends <= word.length
     */
    public static String answerString(String word, int numFriends) {
        if (numFriends == 1) {
            return word;
        }
        char[] cs = word.toCharArray();
        int k = cs.length - numFriends + 1, max = 0;

        // 找到所有最大字符下标
        List<Integer> maxIdxList = new ArrayList<>();
        for (int i = 0; i < cs.length; i++) {
            if (cs[i] > max) {
                max = cs[i];
                maxIdxList.clear();
                maxIdxList.add(i);
            } else if (cs[i] == max) {
                maxIdxList.add(i);
            }
        }

        // 比较
        int maxIdx = maxIdxList.get(0), maxLen = Math.min(k, cs.length - maxIdx);
        for (int i = 1; i < maxIdxList.size(); i++) {
            int idx = maxIdxList.get(i), len = Math.min(k, cs.length - idx);
            if (compare(cs, idx, maxIdx, len, maxLen)) {
                maxIdx = idx;
                maxLen = len;
            }
        }

        return word.substring(maxIdx, maxIdx + maxLen);
    }

    private static boolean compare(char[] cs, int i, int j, int li, int lj) {
        int min = Math.min(li, lj);
        int k = 1;
        while (k < min) {
            if (cs[i + k] > cs[j + k]) {
                return true;
            }
            if (cs[i + k] < cs[j + k]) {
                return false;
            }
            k++;
        }
        return li >= lj;
    }

    /**
     * 1061. 按字典序排列最小的等效字符串
     * 给出长度相同的两个字符串s1 和 s2 ，还有一个字符串 baseStr 。
     * 其中  s1[i] 和 s2[i]  是一组等价字符。
     * 举个例子，如果 s1 = "abc" 且 s2 = "cde"，那么就有 'a' == 'c', 'b' == 'd', 'c' == 'e'。
     * 等价字符遵循任何等价关系的一般规则：
     * 自反性 ：'a' == 'a'
     * 对称性 ：'a' == 'b' 则必定有 'b' == 'a'
     * 传递性 ：'a' == 'b' 且 'b' == 'c' 就表明 'a' == 'c'
     * 例如， s1 = "abc" 和 s2 = "cde" 的等价信息和之前的例子一样，
     * 那么 baseStr = "eed" , "acd" 或 "aab"，这三个字符串都是等价的，而 "aab" 是 baseStr 的按字典序最小的等价字符串
     * 利用 s1 和 s2 的等价信息，找出并返回 baseStr 的按字典序排列最小的等价字符串。
     * 示例 1：
     * 输入：s1 = "parker", s2 = "morris", baseStr = "parser"
     * 输出："makkek"
     * 解释：根据 A 和 B 中的等价信息，我们可以将这些字符分为 [m,p], [a,o], [k,r,s], [e,i] 共 4 组。
     * 每组中的字符都是等价的，并按字典序排列。所以答案是 "makkek"。
     * 示例 2：
     * 输入：s1 = "hello", s2 = "world", baseStr = "hold"
     * 输出："hdld"
     * 解释：根据 A 和 B 中的等价信息，我们可以将这些字符分为 [h,w], [d,e,o], [l,r] 共 3 组。
     * 所以只有 S 中的第二个字符 'o' 变成 'd'，最后答案为 "hdld"。
     * 示例 3：
     * 输入：s1 = "leetcode", s2 = "programs", baseStr = "sourcecode"
     * 输出："aauaaaaada"
     * 解释：我们可以把 A 和 B 中的等价字符分为 [a,o,e,r,s,c], [l,p], [g,t] 和 [d,m] 共 4 组，
     * 因此 S 中除了 'u' 和 'd' 之外的所有字母都转化成了 'a'，最后答案为 "aauaaaaada"。
     * 提示：
     * 1 <= s1.length, s2.length, baseStr <= 1000
     * s1.length == s2.length
     * 字符串s1, s2, and baseStr 仅由从 'a' 到 'z' 的小写英文字母组成。
     */
    public static String smallestEquivalentString(String s1, String s2, String baseStr) {
        // 构建并查集，根据baseStr找根
        UnionFindSet unionFindSet = new UnionFindSet(26);
        for (int i = 0; i < s1.length(); i++) {
            unionFindSet.union(s1.charAt(i) - 'a', s2.charAt(i) - 'a');
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < baseStr.length(); i++) {
            int root = unionFindSet.findRoot(baseStr.charAt(i) - 'a');
            sb.append((char) ('a' + root));
        }
        return sb.toString();
    }

    /**
     * 2434. 使用机器人打印字典序最小的字符串
     * 给你一个字符串 s 和一个机器人，机器人当前有一个空字符串 t 。执行以下操作之一，直到 s 和 t 都变成空字符串：
     * 删除字符串 s 的 第一个 字符，并将该字符给机器人。机器人把这个字符添加到 t 的尾部。
     * 删除字符串 t 的 最后一个 字符，并将该字符给机器人。机器人将该字符写到纸上。
     * 请你返回纸上能写出的字典序最小的字符串。
     * 示例 1：
     * 输入：s = "zza"
     * 输出："azz"
     * 解释：用 p 表示写出来的字符串。
     * 一开始，p="" ，s="zza" ，t="" 。
     * 执行第一个操作三次，得到 p="" ，s="" ，t="zza" 。
     * 执行第二个操作三次，得到 p="azz" ，s="" ，t="" 。
     * 示例 2：
     * 输入：s = "bac"
     * 输出："abc"
     * 解释：用 p 表示写出来的字符串。
     * 执行第一个操作两次，得到 p="" ，s="c" ，t="ba" 。
     * 执行第二个操作两次，得到 p="ab" ，s="c" ，t="" 。
     * 执行第一个操作，得到 p="ab" ，s="" ，t="c" 。
     * 执行第二个操作，得到 p="abc" ，s="" ，t="" 。
     * 示例 3：
     * 输入：s = "bdda"
     * 输出："addb"
     * 解释：用 p 表示写出来的字符串。
     * 一开始，p="" ，s="bdda" ，t="" 。
     * 执行第一个操作四次，得到 p="" ，s="" ，t="bdda" 。
     * 执行第二个操作四次，得到 p="addb" ，s="" ，t="" 。
     * 提示：
     * 1 <= s.length <= 10^5
     * s 只包含小写英文字母。
     */
    public String robotWithString(String s) {
        char[] cs = s.toCharArray();
        // 统计每个字符出现的次数
        int[] cnts = new int[26];
        for (char c : cs) {
            cnts[c - 'a']++;
        }

        char min = 'a';
        Deque<Character> stack = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        for (char c : cs) {
            stack.push(c);
            cnts[c - 'a']--;
            // 找之后的最小字符
            while (min != 'z' && cnts[min - 'a'] == 0) {
                min++;
            }
            while (!stack.isEmpty() && stack.peek() <= min) {
                sb.append(stack.pop());
            }
        }
        return sb.toString();
    }

    /**
     * 440. 字典序的第K小数字
     * 困难
     * 给定整数 n 和 k，返回  [1, n] 中字典序第 k 小的数字。
     * 示例 1:
     * 输入: n = 13, k = 2
     * 输出: 10
     * 解释: 字典序的排列是 [1, 10, 11, 12, 13, 2, 3, 4, 5, 6, 7, 8, 9]，所以第二小的数字是 10。
     * 示例 2:
     * 输入: n = 1, k = 1
     * 输出: 1
     * 提示:
     * 1 <= k <= n <= 10^9
     */
    private static int ans = 0;
    private static int gk = 0;

    public static int findKthNumber(int n, int k) {
        gk = k;
        dfs(0, n);
        return ans;
    }

    private static int dfs(int num, int n) {
        if (num > 0 && --gk == 0) { // num > 0跳过根节点
            ans = num;
            return 1;
        }
        int res = 0;
        for (int i = num == 0 ? 1 : 0; i <= 9; i++) {
            int nextNum = num * 10 + i;
            if (nextNum > n || (res = dfs(nextNum, n)) == 1) {
                break;
            }
        }
        return res;
    }

    /**
     * 3442. 奇偶频次间的最大差值 I
     * 简单
     * 给你一个由小写英文字母组成的字符串 s 。
     * 请你找出字符串中两个字符 a1 和 a2 的出现频次之间的 最大 差值 diff = a1 - a2，这两个字符需要满足：
     * a1 在字符串中出现 奇数次 。
     * a2 在字符串中出现 偶数次 。
     * 返回 最大 差值。
     * 示例 1：
     * 输入：s = "aaaaabbc"
     * 输出：3
     * 解释：
     * 字符 'a' 出现 奇数次 ，次数为 5 ；字符 'b' 出现 偶数次 ，次数为 2 。
     * 最大差值为 5 - 2 = 3 。
     * 示例 2：
     * 输入：s = "abcabcab"
     * 输出：1
     * 解释：
     * 字符 'a' 出现 奇数次 ，次数为 3 ；字符 'c' 出现 偶数次 ，次数为 2 。
     * 最大差值为 3 - 2 = 1 。
     * 提示：
     * 3 <= s.length <= 100
     * s 仅由小写英文字母组成。
     * s 至少由一个出现奇数次的字符和一个出现偶数次的字符组成。
     */
    public static int maxDifference(String s) {
        int[] counts = new int[26];
        for (char c : s.toCharArray()) {
            counts[c - 'a']++;
        }
        int maxOddCnt = 0, minEvenCnt = 100;
        for (int cnt : counts) {
            if (cnt == 0) {
                continue;
            }
            if ((cnt & 1) == 1) {
                if (cnt > maxOddCnt) {
                    maxOddCnt = cnt;
                }
            } else {
                if (cnt < minEvenCnt) {
                    minEvenCnt = cnt;
                }
            }
        }
        return maxOddCnt - minEvenCnt;
    }

    /**
     * 3445. 奇偶频次间的最大差值 II
     * 困难
     * 给你一个字符串 s 和一个整数 k 。请你找出 s 的子字符串 subs 中两个字符的出现频次之间的 最大 差值，freq[a] - freq[b] ，其中：
     * subs 的长度 至少 为 k 。
     * 字符 a 在 subs 中出现奇数次。
     * 字符 b 在 subs 中出现偶数次。
     * 返回 最大 差值。
     * 注意 ，subs 可以包含超过 2 个 互不相同 的字符。.
     * 子字符串 是字符串中的一个连续字符序列。
     * 示例 1：
     * 输入：s = "12233", k = 4
     * 输出：-1
     * 解释：
     * 对于子字符串 "12233" ，'1' 的出现次数是 1 ，'3' 的出现次数是 2 。差值是 1 - 2 = -1 。
     * 示例 2：
     * 输入：s = "1122211", k = 3
     * 输出：1
     * 解释：
     * 对于子字符串 "11222" ，'2' 的出现次数是 3 ，'1' 的出现次数是 2 。差值是 3 - 2 = 1 。
     * 示例 3：
     * 输入：s = "110", k = 3
     * 输出：-1
     * 提示：
     * 3 <= s.length <= 3 * 10^4
     * s 仅由数字 '0' 到 '4' 组成。
     * 输入保证至少存在一个子字符串是由一个出现奇数次的字符和一个出现偶数次的字符组成。
     * 1 <= k <= s.length
     */
    // (x)
    public int maxDifference(String s, int k) {
        int n = s.length();
        int ans = Integer.MIN_VALUE;
        for (char a = '0'; a <= '4'; ++a) {
            for (char b = '0'; b <= '4'; ++b) {
                if (a == b) {
                    continue;
                }
                int[] best = new int[4];
                Arrays.fill(best, Integer.MAX_VALUE);
                int cnt_a = 0, cnt_b = 0;
                int prev_a = 0, prev_b = 0;
                int left = -1;

                for (int right = 0; right < n; ++right) {
                    cnt_a += (s.charAt(right) == a) ? 1 : 0;
                    cnt_b += (s.charAt(right) == b) ? 1 : 0;

                    while (right - left >= k && cnt_b - prev_b >= 2) {
                        int left_status = getStatus(prev_a, prev_b);
                        best[left_status] = Math.min(best[left_status], prev_a - prev_b);
                        ++left;
                        prev_a += (s.charAt(left) == a) ? 1 : 0;
                        prev_b += (s.charAt(left) == b) ? 1 : 0;
                    }

                    int right_status = getStatus(cnt_a, cnt_b);
                    if (best[right_status ^ 0b10] != Integer.MAX_VALUE) {
                        ans = Math.max(ans, cnt_a - cnt_b - best[right_status ^ 0b10]);
                    }
                }
            }
        }
        return ans;
    }

    private int getStatus(int cnt_a, int cnt_b) {
        return ((cnt_a & 1) << 1) | (cnt_b & 1);
    }

    /**
     * 3423. 循环数组中相邻元素的最大差值
     * 给你一个 循环 数组 nums ，请你找出相邻元素之间的 最大 绝对差值。
     * 注意：一个循环数组中，第一个元素和最后一个元素是相邻的。
     * 示例 1：
     * 输入：nums = [1,2,4]
     * 输出：3
     * 解释：
     * 由于 nums 是循环的，nums[0] 和 nums[2] 是相邻的，它们之间的绝对差值是最大值 |4 - 1| = 3 。
     * 示例 2：
     * 输入：nums = [-5,-10,-5]
     * 输出：5
     * 解释：
     * 相邻元素 nums[0] 和 nums[1] 之间的绝对差值为最大值 |-5 - (-10)| = 5 。
     * 提示：
     * 2 <= nums.length <= 100
     * -100 <= nums[i] <= 100
     */
    public int maxAdjacentDistance(int[] nums) {
        int n = nums.length, ans = Math.abs(nums[0] - nums[n - 1]);
        for (int i = 1; i < n; i++) {
            ans = Math.max(ans, Math.abs(nums[i] - nums[i - 1]));
        }
        return ans;
    }

    /**
     * 2616. 最小化数对的最大差值
     * 中等
     * 给你一个下标从 0 开始的整数数组 nums 和一个整数 p 。
     * 请你从 nums 中找到 p 个下标对，每个下标对对应数值取差值，你需要使得这 p 个差值的 最大值 最小。
     * 同时，你需要确保每个下标在这 p 个下标对中最多出现一次。
     * 对于一个下标对 i 和 j ，这一对的差值为 |nums[i] - nums[j]| ，其中 |x| 表示 x 的 绝对值 。
     * 请你返回 p 个下标对对应数值 最大差值 的 最小值 。
     * 示例 1：
     * 输入：nums = [10,1,2,7,1,3], p = 2   1 1 2 3 7 10
     * 输出：1
     * 解释：第一个下标对选择 1 和 4 ，第二个下标对选择 2 和 5 。
     * 最大差值为 max(|nums[1] - nums[4]|, |nums[2] - nums[5]|) = max(0, 1) = 1 。所以我们返回 1 。
     * 示例 2：
     * 输入：nums = [4,2,1,2], p = 1
     * 输出：0
     * 解释：选择下标 1 和 3 构成下标对。差值为 |2 - 2| = 0 ，这是最大差值的最小值。
     * 提示：
     * 1 <= nums.length <= 10^5
     * 0 <= nums[i] <= 10^9
     * 0 <= p <= (nums.length)/2
     */
    public int minimizeMax(int[] nums, int p) {
        // 贪心
        // 先排序，差值最小值一定出现相邻元素，从最大差值二分
        Arrays.sort(nums);
        int n = nums.length, left = 0, right = nums[n - 1] - nums[0];
        while (left < right) {
            int mid = left + (right - left) / 2, count = 0;
            for (int i = 0; i < n - 1; i++) {
                if (nums[i + 1] - nums[i] <= mid) {
                    count++;
                    i++;
                }
            }
            if (count >= p) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    /**
     * 1432. 改变一个整数能得到的最大差值
     * 中等 25/6/15
     * 给你一个整数 num 。你可以对它进行以下步骤共计 两次：
     * 选择一个数字 x (0 <= x <= 9).
     * 选择另一个数字 y (0 <= y <= 9) 。数字 y 可以等于 x 。
     * 将 num 中所有出现 x 的数位都用 y 替换。
     * 令两次对 num 的操作得到的结果分别为 a 和 b 。
     * 请你返回 a 和 b 的 最大差值 。
     * 注意，新的整数（a 或 b）必须不能 含有前导 0，并且 非 0。
     * 示例 1：
     * 输入：num = 555
     * 输出：888
     * 解释：第一次选择 x = 5 且 y = 9 ，并把得到的新数字保存在 a 中。
     * 第二次选择 x = 5 且 y = 1 ，并把得到的新数字保存在 b 中。
     * 现在，我们有 a = 999 和 b = 111 ，最大差值为 888
     * 示例 2：
     * 输入：num = 9
     * 输出：8
     * 解释：第一次选择 x = 9 且 y = 9 ，并把得到的新数字保存在 a 中。
     * 第二次选择 x = 9 且 y = 1 ，并把得到的新数字保存在 b 中。
     * 现在，我们有 a = 9 和 b = 1 ，最大差值为 8
     * 示例 3：
     * 输入：num = 123456
     * 输出：820000
     * 示例 4：
     * 输入：num = 10000
     * 输出：80000
     * 示例 5：
     * 输入：num = 9288
     * 输出：8700
     * 提示：
     * 1 <= num <= 10^8
     */
    public static int maxDiff(int num) {
        List<Integer> bitList = new ArrayList<>();
        while (num > 0) {
            bitList.add(num % 10);
            num /= 10;
        }
        int a = 0, ao = -1, b = 0, bo = -1, bt = -1, n = bitList.size();
        for (int i = n - 1; i >= 0; i--) {
            int bit = bitList.get(i);
            if (ao == -1 && bit < 9) {
                ao = bit;
            }
            a = a * 10 + (bit == ao ? 9 : bit);
            if (bo == -1) {
                if (i == n - 1) {
                    if (bit > 1) {
                        bo = bit;
                        bt = 1;
                    }
                } else {
                    if (bit > 0 && bit != bitList.get(n - 1)) {
                        bo = bit;
                        bt = 0;
                    }
                }
            }
            b = b * 10 + (bit == bo ? bt : bit);
        }
        return a - b;
    }

    /**
     * 2016. 增量元素之间的最大差值
     * 简单 25/6/16
     * 给你一个下标从 0 开始的整数数组 nums ，该数组的大小为 n ，请你计算 nums[j] - nums[i] 能求得的 最大差值 ，其中 0 <= i < j < n 且 nums[i] < nums[j] 。
     * 返回 最大差值 。如果不存在满足要求的 i 和 j ，返回 -1 。
     * 示例 1：
     * 输入：nums = [7,1,5,4]
     * 输出：4
     * 解释：
     * 最大差值出现在 i = 1 且 j = 2 时，nums[j] - nums[i] = 5 - 1 = 4 。
     * 注意，尽管 i = 1 且 j = 0 时 ，nums[j] - nums[i] = 7 - 1 = 6 > 4 ，但 i > j 不满足题面要求，所以 6 不是有效的答案。
     * 示例 2：
     * 输入：nums = [9,4,3,2]
     * 输出：-1
     * 解释：
     * 不存在同时满足 i < j 和 nums[i] < nums[j] 这两个条件的 i, j 组合。
     * 示例 3：
     * 输入：nums = [1,5,2,10]
     * 输出：9
     * 解释：
     * 最大差值出现在 i = 0 且 j = 3 时，nums[j] - nums[i] = 10 - 1 = 9 。
     * 提示：
     * n == nums.length
     * 2 <= n <= 1000
     * 1 <= nums[i] <= 10^9
     */
    public static int maximumDifference(int[] nums) {
        int max = nums[0], min = nums[0], ans = -1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < min) {
                if (max - min > 0) {
                    ans = Math.max(max - min, ans);
                }
                min = nums[i];
                max = nums[i];
            } else if (nums[i] > max) {
                max = nums[i];
            }
        }
        if (max - min > 0) {
            ans = Math.max(max - min, ans);
        }
        return ans;
    }

    /**
     * 3405. 统计恰好有 K 个相等相邻元素的数组数目
     * 困难 25/6/17
     * 给你三个整数 n ，m ，k 。长度为 n 的 好数组 arr 定义如下：
     * arr 中每个元素都在 闭 区间 [1, m] 中。
     * 恰好 有 k 个下标 i （其中 1 <= i < n）满足 arr[i - 1] == arr[i] 。
     * 请你返回可以构造出的 好数组 数目。
     * 由于答案可能会很大，请你将它对 10^9 + 7 取余 后返回。
     * 示例 1：
     * 输入：n = 3, m = 2, k = 1
     * 输出：4
     * 解释：
     * 总共有 4 个好数组，分别是 [1, 1, 2] ，[1, 2, 2] ，[2, 1, 1] 和 [2, 2, 1] 。
     * 所以答案为 4 。
     * 示例 2：
     * 输入：n = 4, m = 2, k = 2
     * 输出：6
     * 解释：
     * 好数组包括 [1, 1, 1, 2] ，[1, 1, 2, 2] ，[1, 2, 2, 2] ，[2, 1, 1, 1] ，[2, 2, 1, 1] 和 [2, 2, 2, 1] 。
     * 所以答案为 6 。
     * 示例 3：
     * 输入：n = 5, m = 2, k = 0
     * 输出：2
     * 解释：
     * 好数组包括 [1, 2, 1, 2, 1] 和 [2, 1, 2, 1, 2] 。
     * 所以答案为 2 。
     * 提示：
     * 1 <= n <= 10^5
     * 1 <= m <= 10^5
     * 0 <= k <= n - 1
     */
    public static int countGoodArrays(int n, int m, int k) {
        // m*(m-1)^(n-1-k)*C(n-1)k
        int mod = 1000000007;
        int a = (int) Math.pow(m - 1, n - 1 - k) % mod;
        int b = (int) C(n - 1, k) % mod;
        return m * a * b;


        // 10 9 0
        // 8^9
    }

    private static long C(int n, int k) {
        // 优化：取 k 和 n-k 中的较小值
        k = Math.min(k, n - k);
        long result = 1;
        for (int i = 1; i <= k; i++) {
            result = result * (n - i + 1) / i;
        }
        return result;
    }

    /**
     * 2966. 划分数组并满足最大差限制
     * 给你一个长度为 n 的整数数组 nums，以及一个正整数 k 。
     * 将这个数组划分为 n / 3 个长度为 3 的子数组，并满足以下条件：
     * 子数组中 任意 两个元素的差必须 小于或等于 k 。
     * 返回一个 二维数组 ，包含所有的子数组。如果不可能满足条件，就返回一个空数组。如果有多个答案，返回 任意一个 即可。
     * 示例 1：
     * 输入：nums = [1,3,4,8,7,9,3,5,1], k = 2
     * 输出：[[1,1,3],[3,4,5],[7,8,9]]
     * 解释：
     * 每个数组中任何两个元素之间的差小于或等于 2。
     * 示例 2：
     * 输入：nums = [2,4,2,2,5,2], k = 2
     * 输出：[]
     * 解释：
     * 将 nums 划分为 2 个长度为 3 的数组的不同方式有：
     * [[2,2,2],[2,4,5]] （及其排列）
     * [[2,2,4],[2,2,5]] （及其排列）
     * 因为有四个 2，所以无论我们如何划分，都会有一个包含元素 2 和 5 的数组。因为 5 - 2 = 3 > k，条件无法被满足，所以没有合法的划分。
     * 示例 3：
     * 输入：nums = [4,2,9,8,2,12,7,12,10,5,8,5,5,7,9,2,5,11], k = 14
     * 输出：[[2,2,12],[4,8,5],[5,9,7],[7,8,5],[5,9,10],[11,12,2]]
     * 解释：
     * 每个数组中任何两个元素之间的差小于或等于 14。
     * 提示：
     * n == nums.length
     * 1 <= n <= 10^5
     * n 是 3 的倍数
     * 1 <= nums[i] <= 10^5
     * 1 <= k <= 10^5
     */
    public int[][] divideArray(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length;
        int[][] ans = new int[n / 3][3];
        for (int i = 0; i < n; i += 3) {
            if (nums[i + 2] - nums[i] > k) {
                return new int[n / 3][3];
            }
            int x = i / 3;
            ans[x][0] = nums[i];
            ans[x][1] = nums[i + 1];
            ans[x][2] = nums[i + 2];
        }
        return ans;
    }

    /**
     * 2294. 划分数组使最大差为 K
     * 给你一个整数数组 nums 和一个整数 k 。你可以将 nums 划分成一个或多个 子序列 ，使 nums 中的每个元素都 恰好 出现在一个子序列中。
     * 在满足每个子序列中最大值和最小值之间的差值最多为 k 的前提下，返回需要划分的 最少 子序列数目。
     * 子序列 本质是一个序列，可以通过删除另一个序列中的某些元素（或者不删除）但不改变剩下元素的顺序得到。
     * 示例 1：
     * 输入：nums = [3,6,1,2,5], k = 2  0 3 5
     * 输出：2
     * 解释：
     * 可以将 nums 划分为两个子序列 [3,1,2] 和 [6,5] 。
     * 第一个子序列中最大值和最小值的差值是 3 - 1 = 2 。
     * 第二个子序列中最大值和最小值的差值是 6 - 5 = 1 。
     * 由于创建了两个子序列，返回 2 。可以证明需要划分的最少子序列数目就是 2 。
     * 示例 2：
     * 输入：nums = [1,2,3], k = 1
     * 输出：2
     * 解释：
     * 可以将 nums 划分为两个子序列 [1,2] 和 [3] 。
     * 第一个子序列中最大值和最小值的差值是 2 - 1 = 1 。
     * 第二个子序列中最大值和最小值的差值是 3 - 3 = 0 。
     * 由于创建了两个子序列，返回 2 。注意，另一种最优解法是将 nums 划分成子序列 [1] 和 [2,3] 。
     * 示例 3：
     * 输入：nums = [2,2,4,5], k = 0
     * 输出：3
     * 解释：
     * 可以将 nums 划分为三个子序列 [2,2]、[4] 和 [5] 。
     * 第一个子序列中最大值和最小值的差值是 2 - 2 = 0 。
     * 第二个子序列中最大值和最小值的差值是 4 - 4 = 0 。
     * 第三个子序列中最大值和最小值的差值是 5 - 5 = 0 。
     * 由于创建了三个子序列，返回 3 。可以证明需要划分的最少子序列数目就是 3 。
     * 提示：
     * 1 <= nums.length <= 10^5
     * 0 <= nums[i] <= 10^5
     * 0 <= k <= 10^5
     */
    public int partitionArray(int[] nums, int k) {
        // 序列中最大值最小值差值要尽可能小
        Arrays.sort(nums);
        int n = nums.length, ans = 1, min = nums[0];
        for (int i = 1; i < n; i++) {
            if (nums[i] - min > k) {
                ans++;
                min = nums[i];
            }
        }
        return ans;
    }

    /**
     * 3443. K 次修改后的最大曼哈顿距离
     * 给你一个由字符 'N'、'S'、'E' 和 'W' 组成的字符串 s，其中 s[i] 表示在无限网格中的移动操作：
     * 'N'：向北移动 1 个单位。
     * 'S'：向南移动 1 个单位。
     * 'E'：向东移动 1 个单位。
     * 'W'：向西移动 1 个单位。
     * 初始时，你位于原点 (0, 0)。你 最多 可以修改 k 个字符为任意四个方向之一。
     * 请找出在 按顺序 执行所有移动操作过程中的 任意时刻 ，所能达到的离原点的 最大曼哈顿距离 。
     * 曼哈顿距离 定义为两个坐标点 (xi, yi) 和 (xj, yj) 的横向距离绝对值与纵向距离绝对值之和，即 |xi - xj| + |yi - yj|。
     * 示例 1：
     * 输入：s = "NWSE", k = 1
     * 输出：3
     * 解释：
     * 将 s[2] 从 'S' 改为 'N' ，字符串 s 变为 "NWNE" 。
     * 移动操作	位置 (x, y)	曼哈顿距离	最大值
     * s[0] == 'N'	(0, 1)	0 + 1 = 1	1
     * s[1] == 'W'	(-1, 1)	1 + 1 = 2	2
     * s[2] == 'N'	(-1, 2)	1 + 2 = 3	3
     * s[3] == 'E'	(0, 2)	0 + 2 = 2	3
     * 执行移动操作过程中，距离原点的最大曼哈顿距离是 3 。
     * 示例 2：
     * 输入：s = "NSWWEW", k = 3
     * 输出：6
     * 解释：
     * 将 s[1] 从 'S' 改为 'N' ，将 s[4] 从 'E' 改为 'W' 。字符串 s 变为 "NNWWWW" 。
     * 执行移动操作过程中，距离原点的最大曼哈顿距离是 6 。
     * 提示：
     * 1 <= s.length <= 10^5
     * 0 <= k <= s.length
     * s 仅由 'N'、'S'、'E' 和 'W' 。
     */
    public int maxDistance(String s, int k) {
        int ans = 0;
        int north = 0, south = 0, east = 0, west = 0;
        for (char it : s.toCharArray()) {
            switch (it) {
                case 'N':
                    north++;
                    break;
                case 'S':
                    south++;
                    break;
                case 'E':
                    east++;
                    break;
                case 'W':
                    west++;
                    break;
            }
            int times1 = Math.min(Math.min(north, south), k);
            int times2 = Math.min(Math.min(east, west), k - times1);
            ans = Math.max(ans, count(north, south, times1) + count(east, west, times2));
        }
        return ans;
    }

    private int count(int drt1, int drt2, int times) {
        return Math.abs(drt1 - drt2) + times * 2;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(countGoodArrays(10, 9, 0));
    }
}
