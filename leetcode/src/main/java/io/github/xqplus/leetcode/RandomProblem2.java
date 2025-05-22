package io.github.xqplus.leetcode;

import java.math.BigDecimal;
import java.util.*;

/**
 * 随机问题类二
 *
 * @author chenq
 * @since 2025.5.7
 */
public class RandomProblem2 {

    /**
     * 2830. 销售利润最大化
     * 中等
     * 给你一个整数 n 表示数轴上的房屋数量，编号从 0 到 n - 1 。
     * 另给你一个二维整数数组 offers ，其中 offers[i] = [starti, endi, goldi]
     * 表示第 i 个买家想要以 goldi 枚金币的价格购买从 starti 到 endi 的所有房屋。
     * 作为一名销售，你需要有策略地选择并销售房屋使自己的收入最大化。
     * 返回你可以赚取的金币的最大数目。
     * 注意 同一所房屋不能卖给不同的买家，并且允许保留一些房屋不进行出售。
     * 示例 1：
     * 输入：n = 5, offers = [[0,0,1],[0,2,2],[1,3,2]]
     * 输出：3
     * 解释：
     * 有 5 所房屋，编号从 0 到 4 ，共有 3 个购买要约。
     * 将位于 [0,0] 范围内的房屋以 1 金币的价格出售给第 1 位买家，并将位于 [1,3] 范围内的房屋以 2 金币的价格出售给第 3 位买家。
     * 可以证明我们最多只能获得 3 枚金币。
     * 示例 2：
     * 输入：n = 5, offers = [[0,0,1],[0,2,10],[1,3,2]]
     * 输出：10
     * 解释：有 5 所房屋，编号从 0 到 4 ，共有 3 个购买要约。
     * 将位于 [0,2] 范围内的房屋以 10 金币的价格出售给第 2 位买家。
     * 可以证明我们最多只能获得 10 枚金币。
     * 提示：
     * 1 <= n <= 10^5
     * 1 <= offers.length <= 10^5
     * offers[i].length == 3
     * 0 <= starti <= endi <= n - 1
     * 1 <= goldi <= 10^3
     */
    public static int maximizeTheProfit(int n, List<List<Integer>> offers) {
        int m = offers.size();
        offers.sort((o1, o2) -> o2.get(1).compareTo(o1.get(1)));
        int[] dp = new int[m + 1]; // dp[i] 表示售卖给前i个买家的最大收入 dp[0] = 0
        for (int i = 0; i < m; i++) {
            // 找到上一条不冲突的买家
            int left = 0, right = i - 1, prev = 0;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (offers.get(mid).get(1) < offers.get(i).get(0)) {
                    prev = mid;
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            dp[i + 1] = Math.max(dp[i], dp[prev] + offers.get(i).get(2));
        }
        return dp[m];
    }

    /**
     * 2481. 分割圆的最少切割次数
     * 圆内一个 有效切割 ，符合以下二者之一：
     * 该切割是两个端点在圆上的线段，且该线段经过圆心。（直径）
     * 该切割是一端在圆心另一端在圆上的线段。（半径）
     * 给你一个整数 n ，请你返回将圆切割成相等的 n 等分的 最少 切割次数。
     * 示例 1：
     * 输入：n = 4
     * 输出：2
     * 示例 2：
     * 输入：n = 3
     * 输出：3
     * 解释：
     * 最少需要切割 3 次，将圆切成三等分。
     * 少于 3 次切割无法将圆切成大小相等面积相同的 3 等分。
     * 同时可以观察到，第一次切割无法将圆切割开。
     * 提示：
     * 1 <= n <= 100
     */
    public int numberOfCuts(int n) {
        return n == 1 ? 0 : n % 2 == 0 ? n / 2 : n;
    }

    /**
     * 1108. IP 地址无效化
     * 给你一个有效的 IPv4 地址 address，返回这个 IP 地址的无效化版本。
     * 所谓无效化 IP 地址，其实就是用 "[.]" 代替了每个 "."。
     * 示例 1：
     * 输入：address = "1.1.1.1"
     * 输出："1[.]1[.]1[.]1"
     * 示例 2：
     * 输入：address = "255.100.50.0"
     * 输出："255[.]100[.]50[.]0"
     * 提示：
     * 给出的 address 是一个有效的 IPv4 地址
     */
    public String defangIPaddr(String address) {
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < address.length(); i++) {
//            char c = address.charAt(i);
//            if (c == '.') {
//                sb.append("[.]");
//            } else {
//                sb.append(c);
//            }
//        }
//        return sb.toString();
        return address.replace(".", "[.]");
    }

    /**
     * 3226. 使两个整数相等的位更改次数
     * 给你两个正整数 n 和 k。
     * 你可以选择 n 的 二进制表示 中任意一个值为 1 的位，并将其改为 0。
     * 返回使得 n 等于 k 所需要的更改次数。如果无法实现，返回 -1。
     * 示例 1：
     * 输入： n = 13, k = 4
     * 输出： 2
     * 解释：
     * 最初，n 和 k 的二进制表示分别为 n = (1101)2 和 k = (0100)2，
     * 我们可以改变 n 的第一位和第四位。结果整数为 n = (0100)2 = k。
     * 示例 2：
     * 输入： n = 21, k = 21
     * 输出： 0
     * 解释：
     * n 和 k 已经相等，因此不需要更改。
     * 示例 3：
     * 输入： n = 14, k = 13
     * 输出： -1
     * 解释：1110 1101
     * 无法使 n 等于 k。
     * 1 <= n, k <= 106
     */
    public int minChanges(int n, int k) {
        // 位运算：n -> k 二进制 1 -> 0, 那么 n & k = k
        // 如满足上述要求，只需要计算两数相异的二进制位（异或）
        return (n & k) == k ? Integer.bitCount(n ^ k) : -1;
    }

    /**
     * 2278. 字母在字符串中的百分比
     * 给你一个字符串 s 和一个字符 letter ，返回在 s 中等于 letter 字符所占的 百分比 ，向下取整到最接近的百分比。
     * 示例 1：
     * 输入：s = "foobar", letter = "o"
     * 输出：33
     * 解释：
     * 等于字母 'o' 的字符在 s 中占到的百分比是 2 / 6 * 100% = 33% ，向下取整，所以返回 33 。
     * 示例 2：
     * 输入：s = "jjjj", letter = "k"
     * 输出：0
     * 解释：
     * 等于字母 'k' 的字符在 s 中占到的百分比是 0% ，所以返回 0 。
     * 1 <= s.length <= 100
     * s 由小写英文字母组成
     * letter 是一个小写英文字母
     */
    public int percentageLetter(String s, char letter) {
        int n = s.length(), cnt = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == letter) {
                cnt++;
            }
        }
        return cnt * 100 / n;
    }

    /**
     * 2124. 检查是否所有 A 都在 B 之前
     * 给你一个 仅 由字符 'a' 和 'b' 组成的字符串  s 。如果字符串中 每个 'a' 都出现在 每个 'b' 之前，返回 true ；否则，返回 false 。
     * 示例 1：
     * 输入：s = "aaabbb"
     * 输出：true
     * 解释：
     * 'a' 位于下标 0、1 和 2 ；而 'b' 位于下标 3、4 和 5 。
     * 因此，每个 'a' 都出现在每个 'b' 之前，所以返回 true 。
     * 示例 2：
     * 输入：s = "abab"
     * 输出：false
     * 解释：
     * 存在一个 'a' 位于下标 2 ，而一个 'b' 位于下标 1 。
     * 因此，不能满足每个 'a' 都出现在每个 'b' 之前，所以返回 false 。
     * 示例 3：
     * 输入：s = "bbb"
     * 输出：true
     * 解释：
     * 不存在 'a' ，因此可以视作每个 'a' 都出现在每个 'b' 之前，所以返回 true 。
     * 提示：
     * 1 <= s.length <= 100
     * s[i] 为 'a' 或 'b'
     */
    public static boolean checkString(String s) {
        boolean b = false; // 表示之前遇到过b了
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'a') {
                if (b) {
                    return false;
                }
            } else {
                b = true;
            }
        }
        return true;
    }

    /**
     * 1160. 拼写单词
     * 给定一个字符串数组 words 和一个字符串 chars。
     * 如果字符串可以由 chars 中的字符组成（每个字符在 每个 words 中只能使用一次），则认为它是好的。
     * 返回 words 中所有好的字符串的长度之和。
     * 示例 1：
     * 输入：words = ["cat","bt","hat","tree"], chars = "atach"
     * 输出：6
     * 解释：
     * 可以形成字符串 "cat" 和 "hat"，所以答案是 3 + 3 = 6。
     * 示例 2：
     * 输入：words = ["hello","world","leetcode"], chars = "welldonehoneyr"
     * 输出：10
     * 解释：
     * 可以形成字符串 "hello" 和 "world"，所以答案是 5 + 5 = 10。
     * 提示：
     * 1 <= words.length <= 1000
     * 1 <= words[i].length, chars.length <= 100
     * words[i] 和 chars 中都仅包含小写英文字母
     */
    public int countCharacters(String[] words, String chars) {
        int cnt = 0;
        int[] charsCounts = new int[26];
        for (int i = 0; i < chars.length(); i++) {
            charsCounts[chars.charAt(i) - 'a']++;
        }
        for (String word : words) {
            int[] wordCounts = new int[26];
            boolean good = true;
            for (int i = 0; i < word.length(); i++) {
                int idx = word.charAt(i) - 'a';
                wordCounts[idx]++;
                if (wordCounts[idx] > charsCounts[idx]) {
                    good = false;
                    break;
                }
            }
            if (good) {
                cnt += word.length();
            }
        }
        return cnt;
    }

    /**
     * 2190. 数组中紧跟 key 之后出现最频繁的数字
     * 给你一个下标从 0 开始的整数数组 nums ，同时给你一个整数 key ，它在 nums 出现过。
     * 统计 在 nums 数组中紧跟着 key 后面出现的不同整数 target 的出现次数。换言之，target 的出现次数为满足以下条件的 i 的数目：
     * 0 <= i <= n - 2
     * nums[i] == key 且
     * nums[i + 1] == target 。
     * 请你返回出现 最多 次数的 target 。测试数据保证出现次数最多的 target 是唯一的。
     * 示例 1：
     * 输入：nums = [1,100,200,1,100], key = 1
     * 输出：100
     * 解释：对于 target = 100 ，在下标 1 和 4 处出现过 2 次，且都紧跟着 key 。
     * 没有其他整数在 key 后面紧跟着出现，所以我们返回 100 。
     * 示例 2：
     * 输入：nums = [2,2,2,2,3], key = 2
     * 输出：2
     * 解释：对于 target = 2 ，在下标 1 ，2 和 3 处出现过 3 次，且都紧跟着 key 。
     * 对于 target = 3 ，在下标 4 出出现过 1 次，且紧跟着 key 。
     * target = 2 是紧跟着 key 之后出现次数最多的数字，所以我们返回 2 。
     * 2 <= nums.length <= 1000
     * 1 <= nums[i] <= 1000
     * 测试数据保证答案是唯一的。
     */
    public static int mostFrequent(int[] nums, int key) {
        int ans = 0, max = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == key) {
                int n = map.getOrDefault(nums[i + 1], 0) + 1;
                if (n > max) {
                    max = n;
                    ans = nums[i + 1];
                }
                map.put(nums[i + 1], n);
            }
        }
        return ans;
    }

    /**
     * 2404. 出现最频繁的偶数元素
     * 给你一个整数数组 nums ，返回出现最频繁的偶数元素。
     * 如果存在多个满足条件的元素，只需要返回 最小 的一个。如果不存在这样的元素，返回 -1 。
     * 示例 1：
     * 输入：nums = [0,1,2,2,4,4,1]
     * 输出：2
     * 解释：
     * 数组中的偶数元素为 0、2 和 4 ，在这些元素中，2 和 4 出现次数最多。
     * 返回最小的那个，即返回 2 。
     * 示例 2：
     * 输入：nums = [4,4,4,9,2,4]
     * 输出：4
     * 解释：4 是出现最频繁的偶数元素。
     * 示例 3：
     * 输入：nums = [29,47,21,41,13,37,25,7]
     * 输出：-1
     * 解释：不存在偶数元素。
     * 提示：
     * 1 <= nums.length <= 2000
     * 0 <= nums[i] <= 10^5
     */
    public int mostFrequentEven(int[] nums) {
        int ans = -1, max = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            if (num % 2 == 0) {
                int n = map.getOrDefault(num, 0) + 1;
                if (n > max || (n == max && num < ans)) {
                    max = n;
                    ans = num;
                }
                map.put(num, n);
            }
        }
        return ans;

        // [8154,9139,8194,3346,5450,9190,133,8239,4606,8671,8412,6290]
    }

    /**
     * 2027. 转换字符串的最少操作次数
     * 给你一个字符串 s ，由 n 个字符组成，每个字符不是 'X' 就是 'O' 。
     * 一次 操作 定义为从 s 中选出 三个连续字符 并将选中的每个字符都转换为 'O' 。注意，如果字符已经是 'O' ，只需要保持 不变 。
     * 返回将 s 中所有字符均转换为 'O' 需要执行的 最少 操作次数。
     * 示例 1：
     * 输入：s = "XXX"
     * 输出：1
     * 解释：XXX -> OOO
     * 一次操作，选中全部 3 个字符，并将它们转换为 'O' 。
     * 示例 2：
     * 输入：s = "XXOX"
     * 输出：2
     * 解释：XXOX -> OOOX -> OOOO
     * 第一次操作，选择前 3 个字符，并将这些字符转换为 'O' 。
     * 然后，选中后 3 个字符，并执行转换。最终得到的字符串全由字符 'O' 组成。
     * 示例 3：
     * 输入：s = "OOOO"
     * 输出：0
     * 解释：s 中不存在需要转换的 'X' 。
     * 提示：
     * 3 <= s.length <= 1000
     * s[i] 为 'X' 或 'O'
     */
    public int minimumMoves(String s) {
        int ans = 0, n = s.length(), idx = 0;
        while (idx < n) {
            if (s.charAt(idx) == 'X') {
                ans++;
                idx += 3;
            } else {
                idx++;
            }
        }
        return ans;
    }

    /**
     * 557. 反转字符串中的单词 III
     * 给定一个字符串 s ，你需要反转字符串中每个单词的字符顺序，同时仍保留空格和单词的初始顺序。
     * 示例 1：
     * 输入：s = "Let's take LeetCode contest"
     * 输出："s'teL ekat edoCteeL tsetnoc"
     * 示例 2:
     * 输入： s = "Mr Ding"
     * 输出："rM gniD"
     * 提示：
     * 1 <= s.length <= 5 * 10^4
     * s 包含可打印的 ASCII 字符。
     * s 不包含任何开头或结尾空格。
     * s 里 至少 有一个词。
     * s 中的所有单词都用一个空格隔开。
     */
    public String reverseWords(String s) {
        StringBuilder sb = new StringBuilder();
        for (String word : s.split(" ")) {
            for (int i = word.length() - 1; i >= 0; i--) {
                sb.append(word.charAt(i));
            }
            sb.append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 1346. 检查整数及其两倍数是否存在
     * 给你一个整数数组 arr，请你检查是否存在两个整数 N 和 M，满足 N 是 M 的两倍（即，N = 2 * M）。
     * 更正式地，检查是否存在两个下标 i 和 j 满足：
     * i != j
     * 0 <= i, j < arr.length
     * arr[i] == 2 * arr[j]
     * 示例 1：
     * 输入：arr = [10,2,5,3]
     * 输出：true
     * 解释：N = 10 是 M = 5 的两倍，即 10 = 2 * 5 。
     * 示例 2：
     * 输入：arr = [7,1,14,11]
     * 输出：true
     * 解释：N = 14 是 M = 7 的两倍，即 14 = 2 * 7 。
     * 示例 3：
     * 输入：arr = [3,1,7,11]
     * 输出：false
     * 解释：在该情况下不存在 N 和 M 满足 N = 2 * M 。
     * 提示：
     * 2 <= arr.length <= 500
     * -10^3 <= arr[i] <= 10^3
     */
    public static boolean checkIfExist(int[] arr) {
        Map<Integer, Boolean> map = new HashMap<>();
        for (int i : arr) {
            if (map.getOrDefault(i * 2, false)
                    || (i % 2 == 0 && map.getOrDefault(i / 2, false))) {
                return true;
            }
            map.put(i, true);
        }
        return false;
    }

    /**
     * LCP 50. 宝石补给
     * 欢迎各位勇者来到力扣新手村，在开始试炼之前，请各位勇者先进行「宝石补给」。
     * 每位勇者初始都拥有一些能量宝石， gem[i] 表示第 i 位勇者的宝石数量。
     * 现在这些勇者们进行了一系列的赠送，operations[j] = [x, y] 表示在第 j 次的赠送中 第 x 位勇者将自己一半的宝石（需向下取整）赠送给第 y 位勇者。
     * 在完成所有的赠送后，请找到拥有最多宝石的勇者和拥有最少宝石的勇者，并返回他们二者的宝石数量之差。
     * 注意：
     * 赠送将按顺序逐步进行。
     * 示例 1：
     * 输入：gem = [3,1,2], operations = [[0,2],[2,1],[2,0]]
     * 输出：2
     * 解释：
     * 第 1 次操作，勇者 0 将一半的宝石赠送给勇者 2， gem = [2,1,3]
     * 第 2 次操作，勇者 2 将一半的宝石赠送给勇者 1， gem = [2,2,2]
     * 第 3 次操作，勇者 2 将一半的宝石赠送给勇者 0， gem = [3,2,1]
     * 返回 3 - 1 = 2
     * 示例 2：
     * 输入：gem = [100,0,50,100], operations = [[0,2],[0,1],[3,0],[3,0]]
     * 输出：75
     * 解释：
     * 第 1 次操作，勇者 0 将一半的宝石赠送给勇者 2， gem = [50,0,100,100]
     * 第 2 次操作，勇者 0 将一半的宝石赠送给勇者 1， gem = [25,25,100,100]
     * 第 3 次操作，勇者 3 将一半的宝石赠送给勇者 0， gem = [75,25,100,50]
     * 第 4 次操作，勇者 3 将一半的宝石赠送给勇者 0， gem = [100,25,100,25]
     * 返回 100 - 25 = 75
     * 示例 3：
     * 输入：gem = [0,0,0,0], operations = [[1,2],[3,1],[1,2]]
     * 输出：0
     * 提示：
     * 2 <= gem.length <= 10^3
     * 0 <= gem[i] <= 10^3
     * 0 <= operations.length <= 10^4
     * operations[i].length == 2
     * 0 <= operations[i][0], operations[i][1] < gem.length
     */
    public int giveGem(int[] gem, int[][] operations) {
        for (int[] operation : operations) {
            int giveVal = gem[operation[0]] / 2;
            gem[operation[0]] -= giveVal;
            gem[operation[1]] += giveVal;
        }
        int max = 0, min = 1000;
        for (int i : gem) {
            if (i > max) {
                max = i;
            }
            if (i < min) {
                min = i;
            }
        }
        return max - min;
    }

    /**
     * 1399. 统计最大组的数目
     * 给定一个整数 n 。
     * 我们需要根据数字的数位和将 1 到 n 的数字分组。例如，数字 14 和 5 属于 同一 组，而数字 13 和 3 属于 不同 组。
     * 返回最大组的数字数量，即元素数量 最多 的组。
     * 示例 1：
     * 输入：n = 13
     * 输出：4
     * 解释：总共有 9 个组，将 1 到 13 按数位求和后这些组分别是：
     * [1,10]，[2,11]，[3,12]，[4,13]，[5]，[6]，[7]，[8]，[9]。总共有 4 个组拥有的数字并列最多。
     * 示例 2：
     * 输入：n = 2
     * 输出：2
     * 解释：总共有 2 个大小为 1 的组 [1]，[2]。
     * 提示：
     * 1 <= n <= 10^4
     */
    public static int countLargestGroup(int n) {
        int ans = 0, max = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            int d = i, ds = 0;
            while (d > 0) {
                ds += d % 10;
                d /= 10;
            }
            int m = map.getOrDefault(ds, 0) + 1;
            if (m > max) {
                max = m;
                ans = 1;
            } else if (m == max) {
                ans++;
            }
            map.put(ds, m);
        }
        return ans;
    }

    /**
     * 2566. 替换一个数字后的最大差值
     * 给你一个整数 num 。你知道 Danny Mittal 会偷偷将 0 到 9 中的一个数字 替换 成另一个数字。
     * 请你返回将 num 中 恰好一个 数字进行替换后，得到的最大值和最小值的差为多少。
     * 注意：
     * 当 Danny 将一个数字 d1 替换成另一个数字 d2 时，Danny 需要将 nums 中所有 d1 都替换成 d2 。
     * Danny 可以将一个数字替换成它自己，也就是说 num 可以不变。
     * Danny 可以将数字分别替换成两个不同的数字分别得到最大值和最小值。
     * 替换后得到的数字可以包含前导 0 。
     * Danny Mittal 获得周赛 326 前 10 名，让我们恭喜他。
     * 示例 1：
     * 输入：num = 11891
     * 输出：99009
     * 解释：
     * 为了得到最大值，我们将数字 1 替换成数字 9 ，得到 99899 。
     * 为了得到最小值，我们将数字 1 替换成数字 0 ，得到 890 。
     * 两个数字的差值为 99009 。
     * 示例 2：
     * 输入：num = 90
     * 输出：99
     * 解释：
     * 可以得到的最大值是 99（将 0 替换成 9），最小值是 0（将 9 替换成 0）。
     * 所以我们得到 99 。
     * 提示：
     * 1 <= num <= 10^8
     */
    public static int minMaxDifference(int num) {
        return replace(num, '9') - replace(num, '0');
    }

    private static int replace(int num, char replaceVal) {
        String s = String.valueOf(num);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != replaceVal) {
                String replace = s.replace(s.charAt(i), replaceVal);
                return Integer.parseInt(replace);
            }
        }
        return num;
    }

    /**
     * 257. 二叉树的所有路径
     * 给你一个二叉树的根节点 root ，按 任意顺序 ，返回所有从根节点到叶子节点的路径。
     * 叶子节点 是指没有子节点的节点。
     * 示例 1：
     * 输入：root = [1,2,3,null,5]
     * 输出：["1->2->5","1->3"]
     * 示例 2：
     * 输入：root = [1]
     * 输出：["1"]
     * 提示：
     * 树中节点的数目在范围 [1, 100] 内
     * -100 <= Node.val <= 100
     */
    public static List<String> binaryTreePaths(TreeNode root) {
        // dfs + backtrack
        List<String> ans = new ArrayList<>();
        dfs(ans, new ArrayList<>(), root);
        return ans;
    }

    private static void dfs(List<String> ans, List<Integer> path, TreeNode node) {
        path.add(node.val);
        if (node.left == null && node.right == null) { // 叶子节点
            ans.add(convertArrow(path));
            return;
        }
        if (node.left != null) {
            dfs(ans, path, node.left);
            path.remove(path.size() - 1);
        }
        if (node.right != null) {
            dfs(ans, path, node.right);
            path.remove(path.size() - 1);
        }
    }

    private static String convertArrow(List<Integer> path) {
        StringBuffer sb = new StringBuffer();
        int n = path.size() - 1;
        for (int i = 0; i <= n; i++) {
            sb.append(path.get(i));
            if (i != n) {
                sb.append("->");
            }
        }
        return sb.toString();
    }

    /**
     * 1603. 设计停车系统
     * 请你给一个停车场设计一个停车系统。停车场总共有三种不同大小的车位：大，中和小，每种尺寸分别有固定数目的车位。
     * 请你实现 ParkingSystem 类：
     * ParkingSystem(int big, int medium, int small) 初始化 ParkingSystem 类，三个参数分别对应每种停车位的数目。
     * bool addCar(int carType) 检查是否有 carType 对应的停车位。 carType 有三种类型：大，中，小，分别用数字 1， 2 和 3 表示。
     * 一辆车只能停在  carType 对应尺寸的停车位中。如果没有空车位，请返回 false ，否则将该车停入车位并返回 true 。
     * 示例 1：
     * 输入：
     * ["ParkingSystem", "addCar", "addCar", "addCar", "addCar"]
     * [[1, 1, 0], [1], [2], [3], [1]]
     * 输出：
     * [null, true, true, false, false]
     * 解释：
     * ParkingSystem parkingSystem = new ParkingSystem(1, 1, 0);
     * parkingSystem.addCar(1); // 返回 true ，因为有 1 个空的大车位
     * parkingSystem.addCar(2); // 返回 true ，因为有 1 个空的中车位
     * parkingSystem.addCar(3); // 返回 false ，因为没有空的小车位
     * parkingSystem.addCar(1); // 返回 false ，因为没有空的大车位，唯一一个大车位已经被占据了
     * 提示：
     * 0 <= big, medium, small <= 1000
     * carType 取值为 1， 2 或 3
     * 最多会调用 addCar 函数 1000 次
     */
    static class ParkingSystem {

        private int big;
        private int medium;
        private int small;

        public ParkingSystem(int big, int medium, int small) {
            this.big = big;
            this.medium = medium;
            this.small = small;
        }

        public boolean addCar(int carType) {
            if (carType == 1) {
                if (big > 0) {
                    big--;
                    return true;
                }
            } else if (carType == 2) {
                if (medium > 0) {
                    medium--;
                    return true;
                }
            } else if (carType == 3) {
                if (small > 0) {
                    small--;
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 3083. 字符串及其反转中是否存在同一子字符串
     * 给你一个字符串 s ，请你判断字符串 s 是否存在一个长度为 2 的子字符串，在 s 反转后的字符串中也出现。
     * 如果存在这样的子字符串，返回 true；如果不存在，返回 false 。
     * 示例 1：
     * 输入：s = "leetcode"
     * 输出：true
     * 解释：子字符串 "ee" 的长度为 2，它也出现在 reverse(s) == "edocteel" 中。
     * 示例 2：
     * 输入：s = "abcba"
     * 输出：true
     * 解释：所有长度为 2 的子字符串 "ab"、"bc"、"cb"、"ba" 也都出现在 reverse(s) == "abcba" 中。
     * 示例 3：
     * 输入：s = "abcd"
     * 输出：false
     * 解释：字符串 s 中不存在满足「在其反转后的字符串中也出现」且长度为 2 的子字符串。
     * 提示：
     * 1 <= s.length <= 100
     * 字符串 s 仅由小写英文字母组成。
     */
    public boolean isSubstringPresent(String s) {
        String rs = new StringBuffer(s).reverse().toString();
        for (int i = 0; i < s.length() - 1; i++) {
            String sub = s.substring(i, i + 2);
            if (rs.contains(sub)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 2185. 统计包含给定前缀的字符串
     * 给你一个字符串数组 words 和一个字符串 pref 。
     * 返回 words 中以 pref 作为 前缀 的字符串的数目。
     * 字符串 s 的 前缀 就是  s 的任一前导连续字符串。
     * 示例 1：
     * 输入：words = ["pay","attention","practice","attend"], pref = "at"
     * 输出：2
     * 解释：以 "at" 作为前缀的字符串有两个，分别是："attention" 和 "attend" 。
     * 示例 2：
     * 输入：words = ["leetcode","win","loops","success"], pref = "code"
     * 输出：0
     * 解释：不存在以 "code" 作为前缀的字符串。
     * 提示：
     * 1 <= words.length <= 100
     * 1 <= words[i].length, pref.length <= 100
     * words[i] 和 pref 由小写英文字母组成
     */
    public int prefixCount(String[] words, String pref) {
        int ans = 0;
        for (String word : words) {
            if (word.startsWith(pref)) {
                ans++;
            }
        }
        return ans;
    }

    /**
     * 2437. 有效时间的数目
     * 给你一个长度为 5 的字符串 time ，表示一个电子时钟当前的时间，格式为 "hh:mm" 。最早 可能的时间是 "00:00" ，最晚 可能的时间是 "23:59" 。
     * 在字符串 time 中，被字符 ? 替换掉的数位是 未知的 ，被替换的数字可能是 0 到 9 中的任何一个。
     * 请你返回一个整数 answer ，将每一个 ? 都用 0 到 9 中一个数字替换后，可以得到的有效时间的数目。
     * 示例 1：
     * 输入：time = "?5:00"
     * 输出：2
     * 解释：我们可以将 ? 替换成 0 或 1 ，得到 "05:00" 或者 "15:00" 。注意我们不能替换成 2 ，因为时间 "25:00" 是无效时间。所以我们有两个选择。
     * 示例 2：
     * 输入：time = "0?:0?"
     * 输出：100
     * 解释：两个 ? 都可以被 0 到 9 之间的任意数字替换，所以我们总共有 100 种选择。
     * 示例 3：
     * 输入：time = "??:??"
     * 输出：1440
     * 解释：小时总共有 24 种选择，分钟总共有 60 种选择。所以总共有 24 * 60 = 1440 种选择。 10 + 10 + 4
     * 提示：
     * time 是一个长度为 5 的有效字符串，格式为 "hh:mm" 。
     * "00" <= hh <= "23"
     * "00" <= mm <= "59"
     * 字符串中有的数位是 '?' ，需要用 0 到 9 之间的数字替换。
     */
    public int countTime(String time) {
        char h1 = time.charAt(0);
        char h2 = time.charAt(1);
        int h = 1;
        if (h1 == '?' && h2 == '?') {
            h = 24;
        } else if (h1 == '?') {
            h = h2 > '3' ? 2 : 3;
        } else if (h2 == '?') {
            h = h1 == '2' ? 4 : 10;
        }
        char m1 = time.charAt(3);
        char m2 = time.charAt(4);
        int m = 1;
        if (m1 == '?' && m2 == '?') {
            m = 60;
        } else if (m1 == '?') {
            m = 6;
        } else if (m2 == '?') {
            m = 10;
        }
        return h * m;
    }

    /**
     * 2717. 半有序排列
     * 给你一个下标从 0 开始、长度为 n 的整数排列 nums 。
     * 如果排列的第一个数字等于 1 且最后一个数字等于 n ，则称其为 半有序排列 。你可以执行多次下述操作，直到将 nums 变成一个 半有序排列 ：
     * 选择 nums 中相邻的两个元素，然后交换它们。
     * 返回使 nums 变成 半有序排列 所需的最小操作次数。
     * 排列 是一个长度为 n 的整数序列，其中包含从 1 到 n 的每个数字恰好一次。
     * 示例 1：
     * 输入：nums = [2,1,4,3]
     * 输出：2
     * 解释：可以依次执行下述操作得到半有序排列：
     * 1 - 交换下标 0 和下标 1 对应元素。排列变为 [1,2,4,3] 。
     * 2 - 交换下标 2 和下标 3 对应元素。排列变为 [1,2,3,4] 。
     * 可以证明，要让 nums 成为半有序排列，不存在执行操作少于 2 次的方案。
     * 示例 2：
     * 输入：nums = [2,4,1,3]
     * 输出：3
     * 解释：
     * 可以依次执行下述操作得到半有序排列：
     * 1 - 交换下标 1 和下标 2 对应元素。排列变为 [2,1,4,3] 。
     * 2 - 交换下标 0 和下标 1 对应元素。排列变为 [1,2,4,3] 。
     * 3 - 交换下标 2 和下标 3 对应元素。排列变为 [1,2,3,4] 。
     * 可以证明，要让 nums 成为半有序排列，不存在执行操作少于 3 次的方案。
     * 示例 3：
     * 输入：nums = [1,3,4,2,5]
     * 输出：0
     * 解释：这个排列已经是一个半有序排列，无需执行任何操作。
     * 提示：
     * 2 <= nums.length == n <= 50
     * 1 <= nums[i] <= 50
     * nums 是一个 排列
     */
    public int semiOrderedPermutation(int[] nums) {
        int n = nums.length, l = 0, r = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] == 1) {
                l = i;
            }
            if (nums[i] == n) {
                r = i;
            }
        }
        return l + n - r - (l > r ? 2 : 1);
    }

    /**
     * 728. 自除数
     * 自除数 是指可以被它包含的每一位数整除的数。
     * 例如，128 是一个 自除数 ，因为 128 % 1 == 0，128 % 2 == 0，128 % 8 == 0。
     * 自除数 不允许包含 0 。
     * 给定两个整数 left 和 right ，返回一个列表，列表的元素是范围 [left, right]（包括两个端点）内所有的 自除数 。
     * 示例 1：
     * 输入：left = 1, right = 22
     * 输出：[1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 15, 22]
     * 示例 2:
     * 输入：left = 47, right = 85
     * 输出：[48,55,66,77]
     * 提示：
     * 1 <= left <= right <= 10^4
     */
    public List<Integer> selfDividingNumbers(int left, int right) {
        List<Integer> ans = new ArrayList<>();
        for (int i = left; i <= right; i++) {
            boolean b = true;
            int k = i;
            while (k > 0) {
                int d = k % 10;
                if (d == 0 || i % d != 0) {
                    b = false;
                    break;
                }
                k /= 10;
            }
            if (b) {
                ans.add(i);
            }
        }
        return ans;
    }

    /**
     * 2739. 总行驶距离
     * 卡车有两个油箱。给你两个整数，mainTank 表示主油箱中的燃料（以升为单位），additionalTank 表示副油箱中的燃料（以升为单位）。
     * 该卡车每耗费 1 升燃料都可以行驶 10 km。每当主油箱使用了 5 升燃料时，如果副油箱至少有 1 升燃料，则会将 1 升燃料从副油箱转移到主油箱。
     * 返回卡车可以行驶的最大距离。
     * 注意：从副油箱向主油箱注入燃料不是连续行为。这一事件会在每消耗 5 升燃料时突然且立即发生。
     * 示例 1：
     * 输入：mainTank = 5, additionalTank = 10
     * 输出：60
     * 解释：
     * 在用掉 5 升燃料后，主油箱中燃料还剩下 (5 - 5 + 1) = 1 升，行驶距离为 50km 。
     * 在用掉剩下的 1 升燃料后，没有新的燃料注入到主油箱中，主油箱变为空。
     * 总行驶距离为 60km 。
     * 示例 2：
     * 输入：mainTank = 1, additionalTank = 2
     * 输出：10
     * 解释：
     * 在用掉 1 升燃料后，主油箱变为空。
     * 总行驶距离为 10km 。
     * 提示：
     * 1 <= mainTank, additionalTank <= 100
     */
    public static int distanceTraveled(int mainTank, int additionalTank) {
        int used = 0;
        while (mainTank >= 5) {
            used += 5;
            mainTank -= 5;
            if (additionalTank > 0) {
                mainTank++;
                additionalTank--;
            }
        }
        return (used + mainTank) * 10;
    }

    /**
     * 1974. 使用特殊打字机键入单词的最少时间
     * 有一个特殊打字机，它由一个 圆盘 和一个 指针 组成， 圆盘上标有小写英文字母 'a' 到 'z'。只有 当指针指向某个字母时，它才能被键入。
     * 指针 初始时 指向字符 'a' 。
     * 每一秒钟，你可以执行以下操作之一：
     * 将指针 顺时针 或者 逆时针 移动一个字符。
     * 键入指针 当前 指向的字符。
     * 给你一个字符串 word ，请你返回键入 word 所表示单词的 最少 秒数 。
     * 示例 1：
     * 输入：word = "abc"
     * 输出：5
     * 解释：
     * 单词按如下操作键入：
     * - 花 1 秒键入字符 'a' in 1 ，因为指针初始指向 'a' ，故不需移动指针。
     * - 花 1 秒将指针顺时针移到 'b' 。
     * - 花 1 秒键入字符 'b' 。
     * - 花 1 秒将指针顺时针移到 'c' 。
     * - 花 1 秒键入字符 'c' 。
     * 示例 2：
     * 输入：word = "bza"
     * 输出：7
     * 解释：
     * 单词按如下操作键入：
     * - 花 1 秒将指针顺时针移到 'b' 。
     * - 花 1 秒键入字符 'b' 。
     * - 花 2 秒将指针逆时针移到 'z' 。
     * - 花 1 秒键入字符 'z' 。
     * - 花 1 秒将指针顺时针移到 'a' 。
     * - 花 1 秒键入字符 'a' 。
     * 示例 3：
     * 输入：word = "zjpc"
     * 输出：34
     * 解释：
     * 单词按如下操作键入：
     * - 花 1 秒将指针逆时针移到 'z' 。
     * - 花 1 秒键入字符 'z' 。
     * - 花 10 秒将指针顺时针移到 'j' 。
     * - 花 1 秒键入字符 'j' 。
     * - 花 6 秒将指针顺时针移到 'p' 。
     * - 花 1 秒键入字符 'p' 。
     * - 花 13 秒将指针逆时针移到 'c' 。
     * - 花 1 秒键入字符 'c' 。
     * 提示：
     * 1 <= word.length <= 100
     * word 只包含小写英文字母。
     */
    public static int minTimeToType(String word) {
        int ans = 0;
        char p = 'a';
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            int diff = Math.abs(c - p);
            int min = Math.min(diff, 26 - diff);
            ans += min + 1;
            p = c;
        }
        return ans;
    }

    /**
     * 2423. 删除字符使频率相同
     * 给你一个下标从 0 开始的字符串 word ，字符串只包含小写英文字母。你需要选择 一个 下标并 删除 下标处的字符，使得 word 中剩余每个字母出现 频率 相同。
     * 如果删除一个字母后，word 中剩余所有字母的出现频率都相同，那么返回 true ，否则返回 false 。
     * 注意：
     * 字母 x 的 频率 是这个字母在字符串中出现的次数。
     * 你 必须 恰好删除一个字母，不能一个字母都不删除。
     * 示例 1：
     * 输入：word = "abcc"
     * 输出：true
     * 解释：选择下标 3 并删除该字母：word 变成 "abc" 且每个字母出现频率都为 1 。
     * 示例 2：
     * 输入：word = "aazz"
     * 输出：false
     * 解释：我们必须删除一个字母，所以要么 "a" 的频率变为 1 且 "z" 的频率为 2 ，要么两个字母频率反过来。所以不可能让剩余所有字母出现频率相同。
     * 提示：
     * 2 <= word.length <= 100
     * word 只包含小写英文字母。
     */
    public static boolean equalFrequency(String word) {
        int[] freq = new int[26];
        for (int i = 0; i < word.length(); i++) {
            freq[word.charAt(i) - 'a']++;
        }
        for (int i = 0; i < 26; i++) {
            if (freq[i] == 0) {
                continue;
            }
            freq[i]--;

            boolean equal = true;
            int v = 0;
            for (int f : freq) {
                if (f == 0) {
                    continue;
                }
                if (v == 0) {
                    v = f;
                } else if (f != v) {
                    equal = false;
                    break;
                }
            }
            if (equal) {
                return true;
            }

            freq[i]++;
        }
        return false;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(equalFrequency("aazz"));
    }
}
