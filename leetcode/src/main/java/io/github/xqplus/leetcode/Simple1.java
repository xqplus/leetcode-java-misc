package io.github.xqplus.leetcode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Simple1 {

    /**
     * 704. 二分查找
     * 给定一个 n 个元素有序的（升序）整型数组 nums 和一个目标值 target  ，写一个函数搜索 nums 中的 target，如果目标值存在返回下标，否则返回 -1。
     * 示例 1:
     * 输入: nums = [-1,0,3,5,9,12], target = 9
     * 输出: 4
     * 解释: 9 出现在 nums 中并且下标为 4
     * 示例 2:
     * 输入: nums = [-1,0,3,5,9,12], target = 2
     * 输出: -1
     * 解释: 2 不存在 nums 中因此返回 -1
     * 提示：
     * 你可以假设 nums 中的所有元素是不重复的。
     * n 将在 [1, 10000]之间。
     * nums 的每个元素都将在 [-9999, 9999]之间。
     */
    public static int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 9. 回文数
     * 给你一个整数 x ，如果 x 是一个回文整数，返回 true ；否则，返回 false 。
     * 回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
     * 例如，121 是回文，而 123 不是。
     * 示例 1：
     * 输入：x = 121
     * 输出：true
     * 示例 2：
     * 输入：x = -121
     * 输出：false
     * 解释：从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
     * 示例 3：
     * 输入：x = 10
     * 输出：false
     * 解释：从右向左读, 为 01 。因此它不是一个回文数。
     * 提示：
     * -2^31 <= x <= 2^31 - 1
     * 进阶：你能不将整数转为字符串来解决这个问题吗？
     */
    public boolean isPalindrome(int x) {
        // 首位不为0，末尾为0，必不是回文
        if (x < 0 || (x != 0 && x % 10 == 0)) {
            return false;
        }
        // 通过维护反转数字交于digit数组少一半遍历
        int y = 0;
        while (x > y) {
            y = y * 10 + x % 10;
            x /= 10;
        }
        // 最后x只可能等于y或者比y少一位（标准中位数可以去除，不影响回文）
        return x == y || x == y / 10;
    }

    /**
     * 13. 罗马数字转整数
     * 罗马数字包含以下七种字符: I， V， X， L，C，D 和 M。
     * 字符          数值
     * I             1
     * V             5
     * X             10
     * L             50
     * C             100
     * D             500
     * M             1000
     * 例如， 罗马数字 2 写做 II ，即为两个并列的 1 。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
     * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。
     * 数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
     * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
     * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
     * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
     * 给定一个罗马数字，将其转换成整数。
     * 示例 1:
     * 输入: s = "III"
     * 输出: 3
     * 示例 2:
     * 输入: s = "IV"
     * 输出: 4
     * 示例 3:
     * 输入: s = "IX"
     * 输出: 9
     * 示例 4:
     * 输入: s = "LVIII"
     * 输出: 58
     * 解释: L = 50, V= 5, III = 3.
     * 示例 5:
     * 输入: s = "MCMXCIV"
     * 输出: 1994
     * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
     * 提示：
     * 1 <= s.length <= 15
     * s 仅含字符 ('I', 'V', 'X', 'L', 'C', 'D', 'M')
     * 题目数据保证 s 是一个有效的罗马数字，且表示整数在范围 [1, 3999] 内
     * 题目所给测试用例皆符合罗马数字书写规则，不会出现跨位等情况。
     * IL 和 IM 这样的例子并不符合题目要求，49 应该写作 XLIX，999 应该写作 CMXCIX 。
     */
    public int romanToInt(String s) {
        Map<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);

        int i = 0, n = s.length(), ans = 0;
        while (i < n) {
            int num = map.get(s.charAt(i));
            if (i < n - 1 && num < map.get(s.charAt(i + 1))) {
                ans += map.get(s.charAt(i + 1)) - num;
                i += 2;
            } else {
                ans += num;
                i++;
            }
        }
        return ans;
    }

    /**
     * 14. 最长公共前缀
     * 编写一个函数来查找字符串数组中的最长公共前缀。
     * 如果不存在公共前缀，返回空字符串 ""。
     * 示例 1：
     * 输入：strs = ["flower","flow","flight"]
     * 输出："fl"
     * 示例 2：
     * 输入：strs = ["dog","racecar","car"]
     * 输出：""
     * 解释：输入不存在公共前缀。
     * 提示：
     * 1 <= strs.length <= 200
     * 0 <= strs[i].length <= 200
     * strs[i] 如果非空，则仅由小写英文字母组成
     */
    public String longestCommonPrefix(String[] strs) {
        StringBuffer sb = new StringBuffer();
        int n0 = strs[0].length(), n = strs.length;
        for (int i = 0; i < n0; i++) {
            char c = strs[0].charAt(i);
            boolean b = false;
            for (int j = 1; j < n; j++) {
                if (i >= strs[j].length() || c != strs[j].charAt(i)) {
                    b = true;
                    break;
                }
            }
            if (b) {
                break;
            }
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 26. 删除有序数组中的重复项
     * 给你一个 非严格递增排列 的数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，返回删除后数组的新长度。
     * 元素的 相对顺序 应该保持 一致 。然后返回 nums 中唯一元素的个数。
     * 考虑 nums 的唯一元素的数量为 k ，你需要做以下事情确保你的题解可以被通过：
     * 更改数组 nums ，使 nums 的前 k 个元素包含唯一元素，并按照它们最初在 nums 中出现的顺序排列。nums 的其余元素与 nums 的大小不重要。
     * 返回 k 。
     * 判题标准:
     * 系统会用下面的代码来测试你的题解:
     * int[] nums = [...]; // 输入数组
     * int[] expectedNums = [...]; // 长度正确的期望答案
     * int k = removeDuplicates(nums); // 调用
     * assert k == expectedNums.length;
     * for (int i = 0; i < k; i++) {
     * assert nums[i] == expectedNums[i];
     * }
     * 如果所有断言都通过，那么您的题解将被 通过。
     * 示例 1：
     * 输入：nums = [1,1,2]
     * 输出：2, nums = [1,2,_]
     * 解释：函数应该返回新的长度 2 ，并且原数组 nums 的前两个元素被修改为 1, 2 。不需要考虑数组中超出新长度后面的元素。
     * 示例 2：
     * 输入：nums = [0,0,1,1,1,2,2,3,3,4]
     * 输出：5, nums = [0,1,2,3,4]
     * 解释：函数应该返回新的长度 5 ， 并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4 。不需要考虑数组中超出新长度后面的元素。
     * 提示：
     * 1 <= nums.length <= 3 * 10^4
     * -10^4 <= nums[i] <= 10^4
     * nums 已按 非严格递增 排列
     */
    public static int removeDuplicates(int[] nums) {
        int idx = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[idx] && i > ++idx) {
                nums[idx] = nums[i];
            }
        }
        return idx + 1;
    }

    /**
     * 1025. 除数博弈
     * 爱丽丝和鲍勃一起玩游戏，他们轮流行动。爱丽丝先手开局。
     * 最初，黑板上有一个数字 n 。在每个玩家的回合，玩家需要执行以下操作：
     * 选出任一 x，满足 0 < x < n 且 n % x == 0 。
     * 用 n - x 替换黑板上的数字 n 。
     * 如果玩家无法执行这些操作，就会输掉游戏。
     * 只有在爱丽丝在游戏中取得胜利时才返回 true 。假设两个玩家都以最佳状态参与游戏。
     * 示例 1：
     * 输入：n = 2
     * 输出：true
     * 解释：爱丽丝选择 1，鲍勃无法进行操作。
     * 示例 2：
     * 输入：n = 3
     * 输出：false
     * 解释：爱丽丝选择 1，鲍勃也选择 1，然后爱丽丝无法进行操作。
     * 提示：
     * 1 <= n <= 1000
     */
    public boolean divisorGame(int n) {
        // 一直选1，回合数就是 n-1, 回合数奇数次先手赢
        return (n - 1) % 2 == 1; //
    }

    /**
     * 2409. 统计共同度过的日子数
     * Alice 和 Bob 计划分别去罗马开会。
     * 给你四个字符串 arriveAlice ，leaveAlice ，arriveBob 和 leaveBob 。
     * Alice 会在日期 arriveAlice 到 leaveAlice 之间在城市里（日期为闭区间），
     * 而 Bob 在日期 arriveBob 到 leaveBob 之间在城市里（日期为闭区间）。
     * 每个字符串都包含 5 个字符，格式为 "MM-DD" ，对应着一个日期的月和日。
     * 请你返回 Alice和 Bob 同时在罗马的天数。
     * 你可以假设所有日期都在 同一个 自然年，而且 不是 闰年。每个月份的天数分别为：[31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31] 。
     * 示例 1：
     * 输入：arriveAlice = "08-15", leaveAlice = "08-18", arriveBob = "08-16", leaveBob = "08-19"
     * 输出：3
     * 解释：Alice 从 8 月 15 号到 8 月 18 号在罗马。Bob 从 8 月 16 号到 8 月 19 号在罗马，他们同时在罗马的日期为 8 月 16、17 和 18 号。所以答案为 3 。
     * 示例 2：
     * 输入：arriveAlice = "10-01", leaveAlice = "10-31", arriveBob = "11-01", leaveBob = "12-31"
     * 输出：0
     * 解释：Alice 和 Bob 没有同时在罗马的日子，所以我们返回 0 。
     * 提示：
     * 所有日期的格式均为 "MM-DD" 。
     * Alice 和 Bob 的到达日期都 早于或等于 他们的离开日期。
     * 题目测试用例所给出的日期均为 非闰年 的有效日期。
     */
    public static int countDaysTogether(String arriveAlice, String leaveAlice, String arriveBob, String leaveBob) {
        int[] datesOfMonths = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int[] prefixSum = new int[13];
        for (int i = 0; i < 12; i++) {
            prefixSum[i + 1] = prefixSum[i] + datesOfMonths[i];
        }

        int arriveAliceDay = calculateDayOfYear(arriveAlice, prefixSum);
        int leaveAliceDay = calculateDayOfYear(leaveAlice, prefixSum);
        int arriveBobDay = calculateDayOfYear(arriveBob, prefixSum);
        int leaveBobDay = calculateDayOfYear(leaveBob, prefixSum);
        return Math.max(0, Math.min(leaveAliceDay, leaveBobDay) - Math.max(arriveAliceDay, arriveBobDay) + 1);
    }

    public static int calculateDayOfYear(String day, int[] prefixSum) {
        int month = Integer.parseInt(day.substring(0, 2));
        int date = Integer.parseInt(day.substring(3));
        return prefixSum[month - 1] + date;
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
     * 解释：小时总共有 24 种选择，分钟总共有 60 种选择。所以总共有 24 * 60 = 1440 种选择。
     * 提示：
     * time 是一个长度为 5 的有效字符串，格式为 "hh:mm" 。
     * "00" <= hh <= "23"
     * "00" <= mm <= "59"
     * 字符串中有的数位是 '?' ，需要用 0 到 9 之间的数字替换。
     */
    public static int countTime(String time) {
        // 012 10 + 10 + 4= 24
        char c1 = time.charAt(0);
        char c2 = time.charAt(1);
        int h1 = c1 == '?' ? (c2 != '?' && c2 > '3' ? 2 : 3) : 1;
        int h2 = c2 == '?' ? (h1 == 3 ? 8 : (c1 == '2' ? 4 : 10)) : 1;
        int m1 = time.charAt(3) == '?' ? 6 : 1;
        int m2 = time.charAt(4) == '?' ? 10 : 1;
        return h1 * h2 * m1 * m2;
    }

    /**
     * 2810. 故障键盘
     * 你的笔记本键盘存在故障，每当你在上面输入字符 'i' 时，它会反转你所写的字符串。而输入其他字符则可以正常工作。
     * 给你一个下标从 0 开始的字符串 s ，请你用故障键盘依次输入每个字符。
     * 返回最终笔记本屏幕上输出的字符串。
     * 示例 1：
     * 输入：s = "string"
     * 输出："rtsng"
     * 解释：
     * 输入第 1 个字符后，屏幕上的文本是："s" 。
     * 输入第 2 个字符后，屏幕上的文本是："st" 。
     * 输入第 3 个字符后，屏幕上的文本是："str" 。
     * 因为第 4 个字符是 'i' ，屏幕上的文本被反转，变成 "rts" 。
     * 输入第 5 个字符后，屏幕上的文本是："rtsn" 。
     * 输入第 6 个字符后，屏幕上的文本是： "rtsng" 。
     * 因此，返回 "rtsng" 。
     * 示例 2：
     * 输入：s = "poiinter"
     * 输出："ponter"
     * 解释：
     * 输入第 1 个字符后，屏幕上的文本是："p" 。
     * 输入第 2 个字符后，屏幕上的文本是："po" 。
     * 因为第 3 个字符是 'i' ，屏幕上的文本被反转，变成 "op" 。
     * 因为第 4 个字符是 'i' ，屏幕上的文本被反转，变成 "po" 。
     * 输入第 5 个字符后，屏幕上的文本是："pon" 。
     * 输入第 6 个字符后，屏幕上的文本是："pont" 。
     * 输入第 7 个字符后，屏幕上的文本是："ponte" 。
     * 输入第 8 个字符后，屏幕上的文本是："ponter" 。
     * 因此，返回 "ponter" 。
     * 提示：
     * 1 <= s.length <= 100
     * s 由小写英文字母组成
     * s[0] != 'i'
     */
    public String finalString(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == 'i') {
                sb.reverse();
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * LCP 44. 开幕式焰火
     * 「力扣挑战赛」开幕式开始了，空中绽放了一颗二叉树形的巨型焰火。
     * 给定一棵二叉树 root 代表焰火，节点值表示巨型焰火这一位置的颜色种类。请帮小扣计算巨型焰火有多少种不同的颜色。
     * 示例 1：
     * 输入：root = [1,3,2,1,null,2]
     * 输出：3
     * 解释：焰火中有 3 个不同的颜色，值分别为 1、2、3
     * 示例 2：
     * 输入：root = [3,3,3]
     * 输出：1
     * 解释：焰火中仅出现 1 个颜色，值为 3
     * 提示：
     * 1 <= 节点个数 <= 1000
     * 1 <= Node.val <= 1000
     */
    int ans = 0;
    boolean[] arr = new boolean[1001];

    public int numColor(TreeNode root) {
        dfs(root);
        return ans;
    }

    private void dfs(TreeNode node) {
        if (node == null) {
            return;
        }
        if (!arr[node.val]) {
            ans++;
            arr[node.val] = true;
        }
        dfs(node.left);
        dfs(node.right);
    }

    /**
     * 3411. 最长乘积等价子数组
     * 给你一个由 正整数 组成的数组 nums。
     * 如果一个数组 arr 满足 prod(arr) == lcm(arr) * gcd(arr)，则称其为 乘积等价数组 ，其中：
     * prod(arr) 表示 arr 中所有元素的乘积。
     * gcd(arr) 表示 arr 中所有元素的最大公因数 (GCD)。
     * lcm(arr) 表示 arr 中所有元素的最小公倍数 (LCM)。
     * 返回数组 nums 的 最长 乘积等价 子数组 的长度。
     * 示例 1：
     * 输入： nums = [1,2,1,2,1,1,1]
     * 输出： 5
     * 解释：
     * 最长的乘积等价子数组是 [1, 2, 1, 1, 1]，其中 prod([1, 2, 1, 1, 1]) = 2， gcd([1, 2, 1, 1, 1]) = 1，以及 lcm([1, 2, 1, 1, 1]) = 2。
     * 示例 2：
     * 输入： nums = [2,3,4,5,6]
     * 输出： 3
     * 解释：
     * 最长的乘积等价子数组是 [3, 4, 5]。
     * 示例 3：
     * 输入： nums = [1,2,3,1,4,5,1]
     * 输出： 5
     * 提示：
     * 2 <= nums.length <= 100
     * 1 <= nums[i] <= 10
     */
    public static int maxLength(int[] nums) {
        // 暴力
        int n = nums.length, ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                long prod = nums[i];
                int gcd = nums[i];
                for (int k = i + 1; k <= j; k++) {
                    prod *= nums[k];
                    gcd = getGCD(gcd, nums[k]);
                }
                int lcm = getLCM(nums, i, j);
                if (prod == (long) gcd * lcm) {
                    int num = j - i + 1;
                    if (num > ans) {
                        ans = num;
                    }
                }
            }
        }
        return ans;
    }

    private static int getGCD(int m, int n) {
        // 辗转相除法求最大公因数
        if (m > n) {
            return getGCD(n, m);
        }
        while (n != 0) {
            int tmp = m;
            m = n;
            n = tmp % n;
        }
        return m;
    }

    private static int getLCM(int[] nums, int i, int j) {
        // 试乘法求最小公倍数
        int m = nums[i];
        while (true) {
            boolean find = true;
            for (int k = i + 1; k <= j; k++) {
                if (m % nums[k] > 0) {
                    find = false;
                    break;
                }
            }
            if (find) {
                return m;
            }
            m += nums[i];
        }
    }

    /**
     * 202. 快乐数
     * 编写一个算法来判断一个数 n 是不是快乐数。
     * 「快乐数」 定义为：
     * 对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和。
     * 然后重复这个过程直到这个数变为 1，也可能是 无限循环 但始终变不到 1。
     * 如果这个过程 结果为 1，那么这个数就是快乐数。
     * 如果 n 是 快乐数 就返回 true ；不是，则返回 false 。
     * 示例 1：
     * 输入：n = 19
     * 输出：true
     * 解释：
     * 1^2 + 9^2 = 82
     * 8^2 + 2^2 = 68
     * 6^2 + 8^2 = 100
     * 1^2 + 0^2 + 0^2 = 1
     * 示例 2：
     * 输入：n = 2
     * 输出：false
     * 提示：
     * 1 <= n <= 2^31 - 1
     */
    public static boolean isHappy(int n) {
        // 只要变换到路径上的元素就会发生循环，不一定非得从第一个循环
//        Map<Integer, Boolean> processed = new HashMap<>();
//        processed.put(n, true);
//        while (n != 1) {
//            int k = 0;
//            while (n > 0) {
//                int digit = n % 10;
//                k += digit * digit;
//                n /= 10;
//            }
//            if (processed.containsKey(k)) return false;
//            processed.put(k, true);
//            n = k;
//        }
//        return true;

        // 快慢指针优化（快指针两步，慢指针一步，相等时即一个循环周期）
        int slow = n, fast = n;
        do {
            slow = process1(slow);
            fast = process1(fast);
            if (fast == 1) { // 快指针先到1
                return true;
            }
            fast = process1(fast);
        } while (slow != fast);
        return slow == 1;
    }

    private static int process1(int n) {
        int k = 0;
        while (n > 0) {
            int digit = n % 10;
            k += digit * digit;
            n /= 10;
        }
        return k;
    }

    /**
     * 3545. 不同字符数量最多为 K 时的最少删除数
     * 给你一个字符串 s（由小写英文字母组成）和一个整数 k。
     * 你的任务是删除字符串中的一些字符（可以不删除任何字符），使得结果字符串中的 不同字符数量 最多为 k。
     * 返回为达到上述目标所需删除的 最小 字符数量。
     * 示例 1：
     * 输入： s = "abc", k = 2
     * 输出： 1
     * 解释：
     * s 有三个不同的字符：'a'、'b' 和 'c'，每个字符的出现频率为 1。
     * 由于最多只能有 k = 2 个不同字符，需要删除某一个字符的所有出现。
     * 例如，删除所有 'c' 后，结果字符串中的不同字符数最多为 k。因此，答案是 1。
     * 示例 2：
     * 输入： s = "aabb", k = 2
     * 输出： 0
     * 解释：
     * s 有两个不同的字符（'a' 和 'b'），它们的出现频率分别为 2 和 2。
     * 由于最多可以有 k = 2 个不同字符，不需要删除任何字符。因此，答案是 0。
     * 示例 3：
     * 输入： s = "yyyzz", k = 1
     * 输出： 2
     * 解释：
     * s 有两个不同的字符（'y' 和 'z'），它们的出现频率分别为 3 和 2。
     * 由于最多只能有 k = 1 个不同字符，需要删除某一个字符的所有出现。
     * 删除所有 'z' 后，结果字符串中的不同字符数最多为 k。因此，答案是 2。
     * 提示：
     * 1 <= s.length <= 16
     * 1 <= k <= 16
     * s 仅由小写英文字母组成。
     */
    public int minDeletion(String s, int k) {
        int[] arr = new int[26];
        int cnt = 0, ans = 0;
        for (int i = 0; i < s.length(); i++) {
            int idx = s.charAt(i) - 'a';
            arr[idx]++;
            if (arr[idx] == 1) {
                cnt++;
            }
        }
        if (cnt > k) {
            Arrays.sort(arr);
            for (int i = 26 - cnt; i < 26 - k; i++) {
                ans += arr[i];
            }
        }
        return ans;
    }

    /**
     * 2335. 装满杯子需要的最短总时长
     * 现有一台饮水机，可以制备冷水、温水和热水。每秒钟，可以装满 2 杯 不同 类型的水或者 1 杯任意类型的水。
     * 给你一个下标从 0 开始、长度为 3 的整数数组 amount ，
     * 其中 amount[0]、amount[1] 和 amount[2] 分别表示需要装满冷水、温水和热水的杯子数量。返回装满所有杯子所需的 最少 秒数。
     * 示例 1：
     * 输入：amount = [1,4,2]
     * 输出：4
     * 解释：下面给出一种方案：
     * 第 1 秒：装满一杯冷水和一杯温水。
     * 第 2 秒：装满一杯温水和一杯热水。
     * 第 3 秒：装满一杯温水和一杯热水。
     * 第 4 秒：装满一杯温水。
     * 可以证明最少需要 4 秒才能装满所有杯子。
     * 示例 2：
     * 输入：amount = [5,4,4]
     * 输出：7
     * 解释：下面给出一种方案：
     * 第 1 秒：装满一杯冷水和一杯热水。
     * 第 2 秒：装满一杯冷水和一杯温水。
     * 第 3 秒：装满一杯冷水和一杯温水。
     * 第 4 秒：装满一杯温水和一杯热水。
     * 第 5 秒：装满一杯冷水和一杯热水。
     * 第 6 秒：装满一杯冷水和一杯温水。
     * 第 7 秒：装满一杯热水。
     * 示例 3：
     * 输入：amount = [5,0,0]
     * 输出：5
     * 解释：每秒装满一杯冷水。
     * 提示：
     * amount.length == 3
     * 0 <= amount[i] <= 100
     */
    public static int fillCups(int[] amount) {
        int ans = 0;
        while (amount[0] > 0 || amount[1] > 0 || amount[2] > 0) {
            if (amount[0] > amount[1]) {
                amount[0]--;
                if (amount[1] > amount[2]) {
                    amount[1]--;
                } else {
                    amount[2]--;
                }
            } else {
                amount[1]--;
                if (amount[0] > amount[2]) {
                    amount[0]--;
                } else {
                    amount[2]--;
                }
            }
            ans++;
        }
        return ans;
    }

    /**
     * LCP 39. 无人机方阵
     * 在 「力扣挑战赛」 开幕式的压轴节目 「无人机方阵」中，每一架无人机展示一种灯光颜色。 无人机方阵通过两种操作进行颜色图案变换：
     * 调整无人机的位置布局
     * 切换无人机展示的灯光颜色
     * 给定两个大小均为 N*M 的二维数组 source 和 target 表示无人机方阵表演的两种颜色图案，
     * 由于无人机切换灯光颜色的耗能很大，请返回从 source 到 target 最少需要多少架无人机切换灯光颜色。
     * 注意： 调整无人机的位置布局时无人机的位置可以随意变动。
     * 示例 1：
     * 输入：source = [[1,3],[5,4]], target = [[3,1],[6,5]]
     * 输出：1
     * 解释： 最佳方案为 将 [0,1] 处的无人机移动至 [0,0] 处； 将 [0,0] 处的无人机移动至 [0,1] 处；
     * 将 [1,0] 处的无人机移动至 [1,1] 处； 将 [1,1] 处的无人机移动至 [1,0] 处，其灯光颜色切换为颜色编号为 6 的灯光；
     * 因此从source 到 target 所需要的最少灯光切换次数为 1。
     * 示例 2：
     * 输入：source = [[1,2,3],[3,4,5]], target = [[1,3,5],[2,3,4]]
     * 输出：0 解释： 仅需调整无人机的位置布局，便可完成图案切换。因此不需要无人机切换颜色
     * 提示：
     * n == source.length == target.length
     * m == source[i].length == target[i].length
     * 1 <= n, m <=100
     * 1 <= source[i][j], target[i][j] <=10^4
     */
    public static int minimumSwitchingTimes(int[][] source, int[][] target) {
        // 调整位置不算次数，则只统计target中存在，且source中不存在的即可
        Map<Integer, Integer> map = new HashMap<>();
        for (int[] row : source) {
            for (int i : row) {
                map.put(i, map.getOrDefault(i, 0) + 1);
            }
        }
        int ans = 0;
        for (int[] row : target) {
            for (int i : row) {
                Integer val = map.get(i);
                if (val == null || val == 0) {
                    ans++;
                } else {
                    map.put(i, val - 1);
                }
            }
        }
        return ans;
    }

    /**
     * 2788. 按分隔符拆分字符串
     * 给你一个字符串数组 words 和一个字符 separator ，请你按 separator 拆分 words 中的每个字符串。
     * 返回一个由拆分后的新字符串组成的字符串数组，不包括空字符串 。
     * 注意
     * separator 用于决定拆分发生的位置，但它不包含在结果字符串中。
     * 拆分可能形成两个以上的字符串。
     * 结果字符串必须保持初始相同的先后顺序。
     * 示例 1：
     * 输入：words = ["one.two.three","four.five","six"], separator = "."
     * 输出：["one","two","three","four","five","six"]
     * 解释：在本示例中，我们进行下述拆分：
     * "one.two.three" 拆分为 "one", "two", "three"
     * "four.five" 拆分为 "four", "five"
     * "six" 拆分为 "six"
     * 因此，结果数组为 ["one","two","three","four","five","six"] 。
     * 示例 2：
     * 输入：words = ["$easy$","$problem$"], separator = "$"
     * 输出：["easy","problem"]
     * 解释：在本示例中，我们进行下述拆分：
     * "$easy$" 拆分为 "easy"（不包括空字符串）
     * "$problem$" 拆分为 "problem"（不包括空字符串）
     * 因此，结果数组为 ["easy","problem"] 。
     * 示例 3：
     * 输入：words = ["|||"], separator = "|"
     * 输出：[]
     * 解释：在本示例中，"|||" 的拆分结果将只包含一些空字符串，所以我们返回一个空数组 [] 。
     * 提示：
     * 1 <= words.length <= 100
     * 1 <= words[i].length <= 20
     * words[i] 中的字符要么是小写英文字母，要么就是字符串 ".,|$#@" 中的字符（不包括引号）
     * separator 是字符串 ".,|$#@" 中的某个字符（不包括引号）
     */
    public static List<String> splitWordsBySeparator(List<String> words, char separator) {
        List<String> ans = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (c != separator) {
                    sb.append(c);
                    continue;
                }
                if (sb.length() > 0) {
                    ans.add(sb.toString());
                    sb.delete(0, sb.length());
                }
            }
            if (sb.length() > 0) {
                ans.add(sb.toString());
                sb.delete(0, sb.length());
            }
        }
        return ans;
    }

    /**
     * 面试题 02.01. 移除重复节点
     * 编写代码，移除未排序链表中的重复节点。保留最开始出现的节点。
     * 示例1：
     * 输入：[1, 2, 3, 3, 2, 1]
     * 输出：[1, 2, 3]
     * 示例2：
     * 输入：[1, 1, 1, 1, 2]
     * 输出：[1, 2]
     * 提示：
     * 链表长度在[0, 20000]范围内。
     * 链表元素在[0, 20000]范围内。
     * 进阶：
     * 如果不得使用临时缓冲区，该怎么解决？
     */
    public ListNode removeDuplicateNodes(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode dummy = new ListNode(), prev;
        dummy.next = head;
        while (head.next != null) {
            boolean exist = false;
            prev = dummy.next;
            while (prev != head.next) {
                if (prev.val == head.next.val) {
                    exist = true;
                    break;
                }
                prev = prev.next;
            }

            if (exist) {
                ListNode tmp = head.next;
                head.next = head.next.next;
                tmp.next = null;
            } else {
                head = head.next;
            }
        }
        return dummy.next;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        List<String> words = Arrays.asList("|||");
        System.out.println(splitWordsBySeparator(words, '|'));
    }
}
