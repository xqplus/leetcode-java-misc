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
     * 1379. 找出克隆二叉树中的相同节点
     * 给你两棵二叉树，原始树 original 和克隆树 cloned，以及一个位于原始树 original 中的目标节点 target。
     * 其中，克隆树 cloned 是原始树 original 的一个 副本 。
     * 请找出在树 cloned 中，与 target 相同 的节点，并返回对该节点的引用（在 C/C++ 等有指针的语言中返回 节点指针，其他语言返回节点本身）。
     * 注意：你 不能 对两棵二叉树，以及 target 节点进行更改。只能 返回对克隆树 cloned 中已有的节点的引用。
     * 示例 1:
     * 输入: tree = [7,4,3,null,null,6,19], target = 3
     * 输出: 3
     * 解释: 上图画出了树 original 和 cloned。target 节点在树 original 中，用绿色标记。答案是树 cloned 中的黄颜色的节点（其他示例类似）。
     * 示例 2:
     * 输入: tree = [7], target =  7
     * 输出: 7
     * 示例 3:
     * 输入: tree = [8,null,6,null,5,null,4,null,3,null,2,null,1], target = 4
     * 输出: 4
     * 提示：
     * 树中节点的数量范围为 [1, 10^4] 。
     * 同一棵树中，没有值相同的节点。
     * target 节点是树 original 中的一个节点，并且不会是 null 。
     * 进阶：如果树中允许出现值相同的节点，将如何解答？
     */
    public final TreeNode getTargetCopy(final TreeNode original, final TreeNode cloned, final TreeNode target) {
        return dfs1(cloned, target.val);
        // 进阶：对于浅克隆，只需要通过节点的left right跟 target比较即可，因为引用对象相等
        // 深克隆：需要搜索target在original中的位置，再从cloned相应位置返回即可,
        // original可统计递归的次数，然后cloned每递归一次，次数-1,到0时指向的节点即为答案
    }

    private TreeNode dfs1(TreeNode node, int targetVal) {
        if (node == null || node.val == targetVal) {
            return node;
        }
        TreeNode left = dfs1(node.left, targetVal);
        if (left != null) {
            return left;
        }
        return dfs1(node.right, targetVal);
    }

    /**
     * 2210. 统计数组中峰和谷的数量
     * 给你一个下标从 0 开始的整数数组 nums 。如果两侧距 i 最近的不相等邻居的值均小于 nums[i] ，则下标 i 是 nums 中，某个峰的一部分。
     * 类似地，如果两侧距 i 最近的不相等邻居的值均大于 nums[i] ，则下标 i 是 nums 中某个谷的一部分。
     * 对于相邻下标 i 和 j ，如果 nums[i] == nums[j] ， 则认为这两下标属于 同一个 峰或谷。
     * 注意，要使某个下标所做峰或谷的一部分，那么它左右两侧必须 都 存在不相等邻居。
     * 返回 nums 中峰和谷的数量。
     * 示例 1：
     * 输入：nums = [2,4,1,1,6,5]  242215
     * 输出：3
     * 解释：
     * 在下标 0 ：由于 2 的左侧不存在不相等邻居，所以下标 0 既不是峰也不是谷。
     * 在下标 1 ：4 的最近不相等邻居是 2 和 1 。由于 4 > 2 且 4 > 1 ，下标 1 是一个峰。
     * 在下标 2 ：1 的最近不相等邻居是 4 和 6 。由于 1 < 4 且 1 < 6 ，下标 2 是一个谷。
     * 在下标 3 ：1 的最近不相等邻居是 4 和 6 。由于 1 < 4 且 1 < 6 ，下标 3 符合谷的定义，但需要注意它和下标 2 是同一个谷的一部分。
     * 在下标 4 ：6 的最近不相等邻居是 1 和 5 。由于 6 > 1 且 6 > 5 ，下标 4 是一个峰。
     * 在下标 5 ：由于 5 的右侧不存在不相等邻居，所以下标 5 既不是峰也不是谷。
     * 共有 3 个峰和谷，所以返回 3 。
     * 示例 2：
     * 输入：nums = [6,6,5,5,4,1]
     * 输出：0
     * 解释：
     * 在下标 0 ：由于 6 的左侧不存在不相等邻居，所以下标 0 既不是峰也不是谷。
     * 在下标 1 ：由于 6 的左侧不存在不相等邻居，所以下标 1 既不是峰也不是谷。
     * 在下标 2 ：5 的最近不相等邻居是 6 和 4 。由于 5 < 6 且 5 > 4 ，下标 2 既不是峰也不是谷。
     * 在下标 3 ：5 的最近不相等邻居是 6 和 4 。由于 5 < 6 且 5 > 4 ，下标 3 既不是峰也不是谷。
     * 在下标 4 ：4 的最近不相等邻居是 5 和 1 。由于 4 < 5 且 4 > 1 ，下标 4 既不是峰也不是谷。
     * 在下标 5 ：由于 1 的右侧不存在不相等邻居，所以下标 5 既不是峰也不是谷。
     * 共有 0 个峰和谷，所以返回 0 。
     * 提示：
     * 3 <= nums.length <= 100
     * 1 <= nums[i] <= 100
     */
    public static int countHillValley(int[] nums) {
//        int n = nums.length, ans = 0;
//        boolean prev = false;
//        for (int i = 1; i < n - 1; i++) {
//            int cur = nums[i], prevNe = 0, nextNe = 0;
//            for (int j = i - 1; j >= 0; j--) {
//                if (nums[j] != cur) {
//                    prevNe = nums[j];
//                    break;
//                }
//            }
//            if (prevNe > 0) {
//                for (int j = i + 1; j < n; j++) {
//                    if (nums[j] != cur) {
//                        nextNe = nums[j];
//                        break;
//                    }
//                }
//            }
//            if (prevNe > 0 && nextNe > 0
//                    && ((cur > prevNe && cur > nextNe) || (cur < prevNe && cur < nextNe))) {
//                if (!prev || cur != nums[i - 1]) { // 前一个相邻不为峰谷 || 是峰谷但是不相等
//                    ans++;
//                }
//                prev = true;
//            } else {
//                prev = false;
//            }
//        }
//        return ans;

        // 只需要维护之前的单调性，就能判断当前是否存在峰谷
        int ans = 0;
        boolean prevUp = nums[0] < nums[1];
        boolean prevDown = nums[0] > nums[1];
        for (int i = 2; i < nums.length; i++) {
            if (nums[i - 1] < nums[i]) { // 当前递增
                if (prevDown) { // 之前递减
                    // 存在谷
                    ans++;
                }
                prevUp = true;
                prevDown = false;
            } else if (nums[i - 1] > nums[i]) {
                if (prevUp) {
                    ans++;
                }
                prevUp = false;
                prevDown = true;
            } // nums[i - 1] == nums[i] 不影响之前的单调性
        }
        return ans;
    }

    /**
     * 3492. 船上可以装载的最大集装箱数量
     * 给你一个正整数 n，表示船上的一个 n x n 的货物甲板。甲板上的每个单元格可以装载一个重量 恰好 为 w 的集装箱。
     * 然而，如果将所有集装箱装载到甲板上，其总重量不能超过船的最大承载重量 maxWeight。
     * 请返回可以装载到船上的 最大 集装箱数量。
     * 示例 1：
     * 输入： n = 2, w = 3, maxWeight = 15
     * 输出： 4
     * 解释：
     * 甲板有 4 个单元格，每个集装箱的重量为 3。将所有集装箱装载后，总重量为 12，未超过 maxWeight。
     * 示例 2：
     * 输入： n = 3, w = 5, maxWeight = 20
     * 输出： 4
     * 解释：
     * 甲板有 9 个单元格，每个集装箱的重量为 5。可以装载的最大集装箱数量为 4，此时总重量不超过 maxWeight。
     * 提示：
     * 1 <= n <= 1000
     * 1 <= w <= 1000
     * 1 <= maxWeight <= 10^9
     */
    public int maxContainers(int n, int w, int maxWeight) {
        n = n * n;
        return n * w <= maxWeight ? n : maxWeight / w;
    }

    /**
     * 696. 计数二进制子串
     * 给定一个字符串 s，统计并返回具有相同数量 0 和 1 的非空（连续）子字符串的数量，并且这些子字符串中的所有 0 和所有 1 都是成组连续的。
     * 重复出现（不同位置）的子串也要统计它们出现的次数。
     * 示例 1：
     * 输入：s = "00110011"
     * 输出：6
     * 解释：6 个子串满足具有相同数量的连续 1 和 0 ："0011"、"01"、"1100"、"10"、"0011" 和 "01" 。
     * 注意，一些重复出现的子串（不同位置）要统计它们出现的次数。
     * 另外，"00110011" 不是有效的子串，因为所有的 0（还有 1 ）没有组合在一起。
     * 示例 2：
     * 输入：s = "10101"
     * 输出：4
     * 解释：有 4 个子串："10"、"01"、"10"、"01" ，具有相同数量的连续 1 和 0 。
     * 提示：
     * 1 <= s.length <= 10^5
     * s[i] 为 '0' 或 '1'
     */
    public static int countBinarySubstrings(String s) {
        // 依次找01或10，找到后向两边扩展
        char[] cs = s.toCharArray();
        int n = cs.length, ans = 0;
        for (int i = 1; i < n; i++) {
            if (cs[i] == cs[i - 1]) {
                continue;
            }
            ans++;
            int l = i - 2, r = i + 1;
            while (l >= 0 && r < n && cs[l] == cs[i - 1] && cs[r] == cs[i]) {
                ans++;
                l--;
                r++;
            }
        }
        return ans;
    }

    /**
     * 1763. 最长的美好子字符串
     * 当一个字符串 s 包含的每一种字母的大写和小写形式 同时 出现在 s 中，就称这个字符串 s 是 美好 字符串。
     * 比方说，"abABB" 是美好字符串，因为 'A' 和 'a' 同时出现了，且 'B' 和 'b' 也同时出现了。
     * 然而，"abA" 不是美好字符串因为 'b' 出现了，而 'B' 没有出现。
     * 给你一个字符串 s ，请你返回 s 最长的 美好子字符串 。如果有多个答案，请你返回 最早 出现的一个。如果不存在美好子字符串，请你返回一个空字符串。
     * 示例 1：
     * 输入：s = "YazaAay"
     * 输出："aAa"
     * 解释："aAa" 是一个美好字符串，因为这个子串中仅含一种字母，其小写形式 'a' 和大写形式 'A' 也同时出现了。
     * "aAa" 是最长的美好子字符串。
     * 示例 2：
     * 输入：s = "Bb"
     * 输出："Bb"
     * 解释："Bb" 是美好字符串，因为 'B' 和 'b' 都出现了。整个字符串也是原字符串的子字符串。
     * 示例 3：
     * 输入：s = "c"
     * 输出：""
     * 解释：没有美好子字符串。
     * 示例 4：
     * 输入：s = "dDzeE"
     * 输出："dD"
     * 解释："dD" 和 "eE" 都是最长美好子字符串。
     * 由于有多个美好子字符串，返回 "dD" ，因为它出现得最早。
     * 提示：
     * 1 <= s.length <= 100
     * s 只包含大写和小写英文字母。
     */
    public String longestNiceSubstring(String s) {
        char[] cs = s.toCharArray();
        int n = cs.length;
        String ans = "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {

            }
        }
        return ans;
    }

    /**
     * 1018. 可被 5 整除的二进制前缀
     * 给定一个二进制数组 nums ( 索引从0开始 )。
     * 我们将xi 定义为其二进制表示形式为子数组 nums[0..i] (从最高有效位到最低有效位)。
     * 例如，如果 nums =[1,0,1] ，那么 x0 = 1, x1 = 2, 和 x2 = 5。
     * 返回布尔值列表 answer，只有当 xi 可以被 5 整除时，答案 answer[i] 为 true，否则为 false。
     * 示例 1：
     * 输入：nums = [0,1,1]
     * 输出：[true,false,false]
     * 解释：
     * 输入数字为 0, 01, 011；也就是十进制中的 0, 1, 3 。只有第一个数可以被 5 整除，因此 answer[0] 为 true 。
     * 示例 2：
     * 输入：nums = [1,1,1]
     * 输出：[false,false,false]
     * 提示：
     * 1 <= nums.length <= 10^5
     * nums[i] 仅为 0 或 1
     */
    public static List<Boolean> prefixesDivBy5(int[] nums) {
        List<Boolean> ans = new ArrayList<>();
        int n = 0;
        for (int num : nums) {
            n = ((n << 1) + num) % 5; // 防止结果溢出
            ans.add(n == 0);
        }
        return ans;
    }

    /**
     * 2180. 统计各位数字之和为偶数的整数个数
     * 给你一个正整数 num ，请你统计并返回 小于或等于 num 且各位数字之和为 偶数 的正整数的数目。
     * 正整数的 各位数字之和 是其所有位上的对应数字相加的结果。
     * 示例 1：
     * 输入：num = 4
     * 输出：2
     * 解释：
     * 只有 2 和 4 满足小于等于 4 且各位数字之和为偶数。
     * 示例 2：
     * 输入：num = 30
     * 输出：14
     * 解释：
     * 只有 14 个整数满足小于等于 30 且各位数字之和为偶数，分别是：
     * 2、4、6、8、11、13、15、17、19、20、22、24、26 和 28 。
     * 提示：
     * 1 <= num <= 1000
     */
    public int countEven(int num) {
        int m = num / 10, n = num % 10;
        int s1 = m > 0 ? m * 5 - 1 : 0;
        int i = m, k = 0;
        while (i > 0) {
            k += i % 10;
            i /= 10;
        }
        int s2 = k % 2 == 0 ? n / 2 + 1 : n / 2 + n % 2;
        return s1 + s2 - (m == 0 ? 1 : 0); // 个位数的话会多算0
        // 2 4 6 8
        // 11 13 15 17 19
        // 20 22 24 26 28
        // 31 33 35 37 39
        // 40 42 44 46 48
        // ...
        // 91 93 95 97 99
        // 100 103 105 107 109
        // ...
        // 110 112

        // 0: 1 0  0/2=0+1=1 0
        // 1: 1 1  1/2=0+1=1 1
        // 2: 2 1  2/2=1+1=2 2
        // 3: 2 2  3/2=1
        // 4: 3 2
        // 5: 3 3
        // 6: 4 3
        // 7: 4 4
        // 8: 5 4
        // 9: 5 5
    }

    /**
     * 3340. 检查平衡字符串
     * 给你一个仅由数字 0 - 9 组成的字符串 num。如果偶数下标处的数字之和等于奇数下标处的数字之和，则认为该数字字符串是一个 平衡字符串。
     * 如果 num 是一个 平衡字符串，则返回 true；否则，返回 false。
     * 示例 1：
     * 输入：num = "1234"
     * 输出：false
     * 解释：
     * 偶数下标处的数字之和为 1 + 3 = 4，奇数下标处的数字之和为 2 + 4 = 6。
     * 由于 4 不等于 6，num 不是平衡字符串。
     * 示例 2：
     * 输入：num = "24123"
     * 输出：true
     * 解释：
     * 偶数下标处的数字之和为 2 + 1 + 3 = 6，奇数下标处的数字之和为 4 + 2 = 6。
     * 由于两者相等，num 是平衡字符串。
     * 提示：
     * 2 <= num.length <= 100
     * num 仅由数字 0 - 9 组成。
     */
    public static boolean isBalanced(String num) {
        int n = num.length(), es = 0, os = 0;
        for (int i = 0; i < n; i += 2) {
            es += num.charAt(i) - '0';
        }
        for (int i = 1; i < n; i += 2) {
            os += num.charAt(i) - '0';
        }
        return es == os;
    }

    /**
     * 2357. 使数组中所有元素都等于零
     * 给你一个非负整数数组 nums 。在一步操作中，你必须：
     * 选出一个正整数 x ，x 需要小于或等于 nums 中 最小 的 非零 元素。
     * nums 中的每个正整数都减去 x。
     * 返回使 nums 中所有元素都等于 0 需要的 最少 操作数。
     * 示例 1：
     * 输入：nums = [1,5,0,3,5]
     * 输出：3
     * 解释：
     * 第一步操作：选出 x = 1 ，之后 nums = [0,4,0,2,4] 。
     * 第二步操作：选出 x = 2 ，之后 nums = [0,2,0,0,2] 。
     * 第三步操作：选出 x = 2 ，之后 nums = [0,0,0,0,0] 。
     * 示例 2：
     * 输入：nums = [0]
     * 输出：0
     * 解释：nums 中的每个元素都已经是 0 ，所以不需要执行任何操作。
     * 提示：
     * 1 <= nums.length <= 100
     * 0 <= nums[i] <= 100
     */
    public int minimumOperations(int[] nums) {
        // 1 18 2 30 7
        // 0 17 1 29 6
        // 0 16 0 28 5
        // 0 11 0 23 0
        // 0 0 0 12 0
        // 0 0 0 0 0

        // 0 1 3 5 5
        // max 5 n = 3
        // 0 1 3 5 6
        // 0 0 2 4 5
        // 0 0 0 2 3
        // 0 0 0 0 1

        boolean[] types = new boolean[101];
        int ans = 0;
        for (int num : nums) {
            if (num > 0 && !types[num]) {
                types[num] = true;
                ans++;
            }
        }
        return ans;
    }

    /**
     * 1528. 重新排列字符串
     * 给你一个字符串 s 和一个 长度相同 的整数数组 indices 。
     * 请你重新排列字符串 s ，其中第 i 个字符需要移动到 indices[i] 指示的位置。
     * 返回重新排列后的字符串。
     * 示例 1：
     * 输入：s = "codeleet", indices = [4,5,6,7,0,2,1,3]
     * 输出："leetcode"
     * 解释：如图所示，"codeleet" 重新排列后变为 "leetcode" 。
     * 示例 2：
     * 输入：s = "abc", indices = [0,1,2]
     * 输出："abc"
     * 解释：重新排列后，每个字符都还留在原来的位置上。
     * 提示：
     * s.length == indices.length == n
     * 1 <= n <= 100
     * s 仅包含小写英文字母
     * 0 <= indices[i] < n
     * indices 的所有的值都是 唯一 的
     */
    public static String restoreString(String s, int[] indices) {
        char[] cs = s.toCharArray();
        char[] cs2 = new char[cs.length];
        for (int i = 0; i < indices.length; i++) {
            cs2[indices[i]] = cs[i];
        }
        return new String(cs2);
    }

    /**
     * 653. 两数之和 IV - 输入二叉搜索树
     * 给定一个二叉搜索树 root 和一个目标结果 k，如果二叉搜索树中存在两个元素且它们的和等于给定的目标结果，则返回 true。
     * 示例 1：
     * 5
     * 3  6
     * 2  4  n 7
     * 输入: root = [5,3,6,2,4,null,7], k = 9
     * 输出: true
     * 示例 2：
     * 输入: root = [5,3,6,2,4,null,7], k = 28
     * 输出: false
     * 提示:
     * 二叉树的节点个数的范围是  [1, 10^4].
     * -10^4 <= Node.val <= 10^4
     * 题目数据保证，输入的 root 是一棵 有效 的二叉搜索树
     * -10^5 <= k <= 10^5
     */
    public boolean findTarget(TreeNode root, int k) {
        Map<Integer, Boolean> map = new HashMap<>();
        return dfs2(root, map, k);
    }

    private boolean dfs2(TreeNode node, Map<Integer, Boolean> map, int k) {
        if (node == null) {
            return false;
        }
        if (map.containsKey(k - node.val)) {
            return true;
        }
        map.put(node.val, true);
        if (dfs2(node.left, map, k)) {
            return true;
        }
        return dfs2(node.right, map, k);
    }

    /**
     * 806. 写字符串需要的行数
     * 我们要把给定的字符串 S 从左到右写到每一行上，每一行的最大宽度为100个单位，
     * 如果我们在写某个字母的时候会使这行超过了100 个单位，那么我们应该把这个字母写到下一行。
     * 我们给定了一个数组 widths ，这个数组 widths[0] 代表 'a' 需要的单位，
     * widths[1] 代表 'b' 需要的单位，...， widths[25] 代表 'z' 需要的单位。
     * 现在回答两个问题：至少多少行能放下S，以及最后一行使用的宽度是多少个单位？将你的答案作为长度为2的整数列表返回。
     * 示例 1:
     * 输入:
     * widths = [10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10]
     * S = "abcdefghijklmnopqrstuvwxyz"
     * 输出: [3, 60]
     * 解释:
     * 所有的字符拥有相同的占用单位10。所以书写所有的26个字母，
     * 我们需要2个整行和占用60个单位的一行。
     * 示例 2:
     * 输入:
     * widths = [4,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10]
     * S = "bbbcccdddaaa"
     * 输出: [2, 4]
     * 解释:
     * 除去字母'a'所有的字符都是相同的单位10，并且字符串 "bbbcccdddaa" 将会覆盖 9 * 10 + 2 * 4 = 98 个单位.
     * 最后一个字母 'a' 将会被写到第二行，因为第一行只剩下2个单位了。
     * 所以，这个答案是2行，第二行有4个单位宽度。
     * 注:
     * 字符串 S 的长度在 [1, 1000] 的范围。
     * S 只包含小写字母。
     * widths 是长度为 26的数组。
     * widths[i] 值的范围在 [2, 10]。
     */
    public int[] numberOfLines(int[] widths, String s) {
        int[] ans = new int[2];
        for (int i = 0; i < s.length(); i++) {
            int cLen = widths[s.charAt(i) - 'a'];
            if (ans[1] + cLen > 100) {
                ans[0]++;
                ans[1] = cLen;
            } else {
                ans[1] += cLen;
            }
        }
        ans[0]++;
        return ans;
    }

    /**
     * 1351. 统计有序矩阵中的负数
     * 给你一个 m * n 的矩阵 grid，矩阵中的元素无论是按行还是按列，都以非严格递减顺序排列。 请你统计并返回 grid 中 负数 的数目。
     * 示例 1：
     * 4  3  2 -1
     * 3  2  1 -1
     * 1  1 -1 -2
     * -1 -1 -2 -3
     * 输入：grid = [[4,3,2,-1],[3,2,1,-1],[1,1,-1,-2],[-1,-1,-2,-3]]
     * 输出：8
     * 解释：矩阵中共有 8 个负数。
     * 示例 2：
     * 输入：grid = [[3,2],[1,0]]
     * 输出：0
     * 提示：
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 100
     * -100 <= grid[i][j] <= 100
     * 进阶：你可以设计一个时间复杂度为 O(n + m) 的解决方案吗？
     */
    public static int countNegatives(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        if (grid[0][0] < 0) {
            return m * n;
        }
        int ans = 0, idx = -1;
        // 二分查找第一个负数，且根据有序原则更新右边界
        for (int[] row : grid) {
            int l = 0, r = idx == -1 ? n - 1 : idx;
            while (l <= r) {
                int mid = l + (r - l) / 2;
                if (row[mid] < 0) {
                    idx = mid;
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }
            if (idx != -1) {
                ans += n - idx;
            }
        }
        return ans;
    }

    /**
     * 83. 删除排序链表中的重复元素
     * 给定一个已排序的链表的头 head ， 删除所有重复的元素，使每个元素只出现一次 。返回 已排序的链表 。
     * 示例 1：
     * 输入：head = [1,1,2]
     * 输出：[1,2]
     * 示例 2：
     * 输入：head = [1,1,2,3,3]
     * 输出：[1,2,3]
     * 提示：
     * 链表中节点数目在范围 [0, 300] 内
     * -100 <= Node.val <= 100
     * 题目数据保证链表已经按升序 排列
     */
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        while (head.next != null) {
            if (head.val == head.next.val) {
                head.next = head.next.next;
            } else {
                head = head.next;
            }
        }
        return dummy.next;
    }

    /**
     * 2220. 转换数字的最少位翻转次数
     * 一次 位翻转 定义为将数字 x 二进制中的一个位进行 翻转 操作，即将 0 变成 1 ，或者将 1 变成 0 。
     * 比方说，x = 7 ，二进制表示为 111 ，我们可以选择任意一个位（包含没有显示的前导 0 ）并进行翻转。
     * 比方说我们可以翻转最右边一位得到 110 ，或者翻转右边起第二位得到 101 ，或者翻转右边起第五位（这一位是前导 0 ）得到 10111 等等。
     * 给你两个整数 start 和 goal ，请你返回将 start 转变成 goal 的 最少位翻转 次数。
     * 示例 1：
     * 输入：start = 10, goal = 7
     * 输出：3
     * 解释：10 和 7 的二进制表示分别为 1010 和 0111 。我们可以通过 3 步将 10 转变成 7 ：
     * - 翻转右边起第一位得到：1010 -> 1011 。
     * - 翻转右边起第三位：1011 -> 1111 。
     * - 翻转右边起第四位：1111 -> 0111 。
     * 我们无法在 3 步内将 10 转变成 7 。所以我们返回 3 。
     * 示例 2：
     * 输入：start = 3, goal = 4
     * 输出：3
     * 解释：3 和 4 的二进制表示分别为 011 和 100 。我们可以通过 3 步将 3 转变成 4 ：
     * - 翻转右边起第一位：011 -> 010 。
     * - 翻转右边起第二位：010 -> 000 。
     * - 翻转右边起第三位：000 -> 100 。
     * 我们无法在 3 步内将 3 变成 4 。所以我们返回 3 。
     * 提示：
     * 0 <= start, goal <= 10^9
     */
    public int minBitFlips(int start, int goal) {
        String s = Integer.toBinaryString(start), g = Integer.toBinaryString(goal);
        int ns = s.length() - 1, ng = g.length() - 1, ans = 0;
        while (ns >= 0 || ng >= 0) {
            char cs = ns >= 0 ? s.charAt(ns) : '0';
            char cg = ng >= 0 ? g.charAt(ng) : '0';
            if (cs != cg) {
                ans++;
            }
            ns--;
            ng--;
        }
        return ans;

        // Integer.bitCount(start ^ goal);
    }

    /**
     * 2089. 找出数组排序后的目标下标
     * 给你一个下标从 0 开始的整数数组 nums 以及一个目标元素 target 。
     * 目标下标 是一个满足 nums[i] == target 的下标 i 。
     * 将 nums 按 非递减 顺序排序后，返回由 nums 中目标下标组成的列表。如果不存在目标下标，返回一个 空 列表。返回的列表必须按 递增 顺序排列。
     * 示例 1：
     * 输入：nums = [1,2,5,2,3], target = 2
     * 输出：[1,2]
     * 解释：排序后，nums 变为 [1,2,2,3,5] 。
     * 满足 nums[i] == 2 的下标是 1 和 2 。
     * 示例 2：
     * 输入：nums = [1,2,5,2,3], target = 3
     * 输出：[3]
     * 解释：排序后，nums 变为 [1,2,2,3,5] 。
     * 满足 nums[i] == 3 的下标是 3 。
     * 示例 3：
     * 输入：nums = [1,2,5,2,3], target = 5
     * 输出：[4]
     * 解释：排序后，nums 变为 [1,2,2,3,5] 。
     * 满足 nums[i] == 5 的下标是 4 。
     * 示例 4：
     * 输入：nums = [1,2,5,2,3], target = 4
     * 输出：[]
     * 解释：nums 中不含值为 4 的元素。
     * 提示：
     * 1 <= nums.length <= 100
     * 1 <= nums[i], target <= 100
     */
    public List<Integer> targetIndices(int[] nums, int target) {
        int i = 0, n = 0;
        for (int num : nums) {
            if (num == target) {
                n++;
            } else if (num < target) {
                i++;
            }
        }
        List<Integer> ans = new ArrayList<>();
        for (int i1 = 0; i1 < n; i1++) {
            ans.add(i + i1);
        }
        return ans;
    }

    /**
     * 2706. 购买两块巧克力
     * 给你一个整数数组 prices ，它表示一个商店里若干巧克力的价格。同时给你一个整数 money ，表示你一开始拥有的钱数。
     * 你必须购买 恰好 两块巧克力，而且剩余的钱数必须是 非负数 。同时你想最小化购买两块巧克力的总花费。
     * 请你返回在购买两块巧克力后，最多能剩下多少钱。如果购买任意两块巧克力都超过了你拥有的钱，请你返回 money 。注意剩余钱数必须是非负数。
     * 示例 1：
     * 输入：prices = [1,2,2], money = 3
     * 输出：0
     * 解释：分别购买价格为 1 和 2 的巧克力。你剩下 3 - 3 = 0 块钱。所以我们返回 0 。
     * 示例 2：
     * 输入：prices = [3,2,3], money = 3
     * 输出：3
     * 解释：购买任意 2 块巧克力都会超过你拥有的钱数，所以我们返回 3 。
     * 提示：
     * 2 <= prices.length <= 50
     * 1 <= prices[i] <= 100
     * 1 <= money <= 100
     */
    public int buyChoco(int[] prices, int money) {
        int min1 = Math.min(prices[0], prices[1]);
        int min2 = Math.max(prices[0], prices[1]);
        for (int i = 2; i < prices.length; i++) {
            if (prices[i] < min1) {
                min2 = min1;
                min1 = prices[i];
            } else if (prices[i] < min2) {
                min2 = prices[i];
            }
        }
        int sum = min1 + min2;
        return sum > money ? money : money - sum;
    }

    /**
     * 682. 棒球比赛
     * 你现在是一场采用特殊赛制棒球比赛的记录员。这场比赛由若干回合组成，过去几回合的得分可能会影响以后几回合的得分。
     * 比赛开始时，记录是空白的。你会得到一个记录操作的字符串列表 ops，其中 ops[i] 是你需要记录的第 i 项操作，ops 遵循下述规则：
     * 整数 x - 表示本回合新获得分数 x
     * "+" - 表示本回合新获得的得分是前两次得分的总和。题目数据保证记录此操作时前面总是存在两个有效的分数。
     * "D" - 表示本回合新获得的得分是前一次得分的两倍。题目数据保证记录此操作时前面总是存在一个有效的分数。
     * "C" - 表示前一次得分无效，将其从记录中移除。题目数据保证记录此操作时前面总是存在一个有效的分数。
     * 请你返回记录中所有得分的总和。
     * 示例 1：
     * 输入：ops = ["5","2","C","D","+"]
     * 输出：30
     * 解释：
     * "5" - 记录加 5 ，记录现在是 [5]
     * "2" - 记录加 2 ，记录现在是 [5, 2]
     * "C" - 使前一次得分的记录无效并将其移除，记录现在是 [5].
     * "D" - 记录加 2 * 5 = 10 ，记录现在是 [5, 10].
     * "+" - 记录加 5 + 10 = 15 ，记录现在是 [5, 10, 15].
     * 所有得分的总和 5 + 10 + 15 = 30
     * 示例 2：
     * 输入：ops = ["5","-2","4","C","D","9","+","+"]
     * 输出：27
     * 解释：
     * "5" - 记录加 5 ，记录现在是 [5]
     * "-2" - 记录加 -2 ，记录现在是 [5, -2]
     * "4" - 记录加 4 ，记录现在是 [5, -2, 4]
     * "C" - 使前一次得分的记录无效并将其移除，记录现在是 [5, -2]
     * "D" - 记录加 2 * -2 = -4 ，记录现在是 [5, -2, -4]
     * "9" - 记录加 9 ，记录现在是 [5, -2, -4, 9]
     * "+" - 记录加 -4 + 9 = 5 ，记录现在是 [5, -2, -4, 9, 5]
     * "+" - 记录加 9 + 5 = 14 ，记录现在是 [5, -2, -4, 9, 5, 14]
     * 所有得分的总和 5 + -2 + -4 + 9 + 5 + 14 = 27
     * 示例 3：
     * 输入：ops = ["1"]
     * 输出：1
     * 提示：
     * 1 <= ops.length <= 1000
     * ops[i] 为 "C"、"D"、"+"，或者一个表示整数的字符串。整数范围是 [-3 * 104, 3 * 104]
     * 对于 "+" 操作，题目数据保证记录此操作时前面总是存在两个有效的分数
     * 对于 "C" 和 "D" 操作，题目数据保证记录此操作时前面总是存在一个有效的分数
     */
    public int calPoints(String[] operations) {
        int[] nums = new int[operations.length];
        int ans = 0, idx = 0;
        for (String operation : operations) {
            if ("+".equals(operation)) {
                nums[idx] = nums[idx - 1] + nums[idx - 2];
                ans += nums[idx++];
            } else if ("D".equals(operation)) {
                nums[idx] = nums[idx - 1] * 2;
                ans += nums[idx++];
            } else if ("C".equals(operation)) {
                ans -= nums[--idx];
            } else {
                nums[idx] = Integer.parseInt(operation);
                ans += nums[idx++];
            }
        }
        return ans;
    }

    /**
     * 349. 两个数组的交集
     * 给定两个数组 nums1 和 nums2 ，返回 它们的 交集 。输出结果中的每个元素一定是 唯一 的。我们可以 不考虑输出结果的顺序 。
     * 示例 1：
     * 输入：nums1 = [1,2,2,1], nums2 = [2,2]
     * 输出：[2]
     * 示例 2：
     * 输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
     * 输出：[9,4]
     * 解释：[4,9] 也是可通过的
     * 提示：
     * 1 <= nums1.length, nums2.length <= 1000
     * 0 <= nums1[i], nums2[i] <= 1000
     */
    public int[] intersection(int[] nums1, int[] nums2) {
        int[] counts = new int[1001];
        List<Integer> list = new ArrayList<>();
        for (int i : nums1) {
            if (counts[i] == 0) {
                counts[i]++;
            }
        }
        for (int i : nums2) {
            if (counts[i] == 1) {
                list.add(i);
                counts[i]++;
            }
        }
        return list.stream().mapToInt(i -> i).toArray();
    }

    /**
     * 3379. 转换数组
     * 给你一个整数数组 nums，它表示一个循环数组。请你遵循以下规则创建一个大小 相同 的新数组 result ：
     * 对于每个下标 i（其中 0 <= i < nums.length），独立执行以下操作：
     * 如果 nums[i] > 0：从下标 i 开始，向 右 移动 nums[i] 步，在循环数组中落脚的下标对应的值赋给 result[i]。
     * 如果 nums[i] < 0：从下标 i 开始，向 左 移动 abs(nums[i]) 步，在循环数组中落脚的下标对应的值赋给 result[i]。
     * 如果 nums[i] == 0：将 nums[i] 的值赋给 result[i]。
     * 返回新数组 result。
     * 注意：由于 nums 是循环数组，向右移动超过最后一个元素时将回到开头，向左移动超过第一个元素时将回到末尾。
     * 示例 1：
     * 输入： nums = [3,-2,1,1]
     * 输出： [1,1,1,3]
     * 解释：
     * 对于 nums[0] 等于 3，向右移动 3 步到 nums[3]，因此 result[0] 为 1。
     * 对于 nums[1] 等于 -2，向左移动 2 步到 nums[3]，因此 result[1] 为 1。
     * 对于 nums[2] 等于 1，向右移动 1 步到 nums[3]，因此 result[2] 为 1。
     * 对于 nums[3] 等于 1，向右移动 1 步到 nums[0]，因此 result[3] 为 3。
     * 示例 2：
     * 输入： nums = [-1,4,-1]
     * 输出： [-1,-1,4]
     * 解释：
     * 对于 nums[0] 等于 -1，向左移动 1 步到 nums[2]，因此 result[0] 为 -1。
     * 对于 nums[1] 等于 4，向右移动 4 步到 nums[2]，因此 result[1] 为 -1。
     * 对于 nums[2] 等于 -1，向左移动 1 步到 nums[1]，因此 result[2] 为 4。
     * 提示：
     * 1 <= nums.length <= 100
     * -100 <= nums[i] <= 100
     */
    public static int[] constructTransformedArray(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            int idx = nums[i] < 0 ? (i + (n + nums[i] % n)) % n : (i + nums[i]) % n;
            ans[i] = nums[idx];
        }
        return ans;
    }

    /**
     * 面试题 02.02. 返回倒数第 k 个节点
     * 实现一种算法，找出单向链表中倒数第 k 个节点。返回该节点的值。
     * 注意：本题相对原题稍作改动
     * 示例：
     * 输入： 1->2->3->4->5 和 k = 2
     * 输出： 4
     * 说明：
     * 给定的 k 保证是有效的。
     */
    private static int ans1 = 0;
    private static int gk = 0;
    public int kthToLast(ListNode head, int k) {
        gk = k;
        dfs3(head);
        return ans1;
    }

    private void dfs3(ListNode node) {
        if (node == null) {
            return;
        }
        dfs3(node.next);
        if (--gk == 0) {
            ans1 = node.val;
        }
    }

    /**
     * 3512. 使数组和能被 K 整除的最少操作次数
     * 给你一个整数数组 nums 和一个整数 k。你可以执行以下操作任意次：
     * 选择一个下标 i，并将 nums[i] 替换为 nums[i] - 1。
     * 返回使数组元素之和能被 k 整除所需的最小操作次数。
     * 示例 1：
     * 输入： nums = [3,9,7], k = 5
     * 输出： 4
     * 解释：
     * 对 nums[1] = 9 执行 4 次操作。现在 nums = [3, 5, 7]。
     * 数组之和为 15，可以被 5 整除。
     * 示例 2：
     * 输入： nums = [4,1,3], k = 4
     * 输出： 0
     * 解释：
     * 数组之和为 8，已经可以被 4 整除。因此不需要操作。
     * 示例 3：
     * 输入： nums = [3,2], k = 6
     * 输出： 5
     * 解释：
     * 对 nums[0] = 3 执行 3 次操作，对 nums[1] = 2 执行 2 次操作。现在 nums = [0, 0]。
     * 数组之和为 0，可以被 6 整除。
     * 提示：
     * 1 <= nums.length <= 1000
     * 1 <= nums[i] <= 1000
     * 1 <= k <= 100
     */
    public int minOperations(int[] nums, int k) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        return sum - (sum / k * k);
    }

    /**
     * 1961. 检查字符串是否为数组前缀
     * 给你一个字符串 s 和一个字符串数组 words ，请你判断 s 是否为 words 的 前缀字符串 。
     * 字符串 s 要成为 words 的 前缀字符串 ，需要满足：s 可以由 words 中的前 k（k 为 正数 ）个字符串按顺序相连得到，且 k 不超过 words.length 。
     * 如果 s 是 words 的 前缀字符串 ，返回 true ；否则，返回 false 。
     * 示例 1：
     * 输入：s = "iloveleetcode", words = ["i","love","leetcode","apples"]
     * 输出：true
     * 解释：
     * s 可以由 "i"、"love" 和 "leetcode" 相连得到。
     * 示例 2：
     * 输入：s = "iloveleetcode", words = ["apples","i","love","leetcode"]
     * 输出：false
     * 解释：
     * 数组的前缀相连无法得到 s 。
     * 提示：
     * 1 <= words.length <= 100
     * 1 <= words[i].length <= 20
     * 1 <= s.length <= 1000
     * words[i] 和 s 仅由小写英文字母组成
     */
    public boolean isPrefixString(String s, String[] words) {
        int n = s.length();
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(word);
            if (sb.length() > n) {
                return false;
            }
            if (sb.length() == n) {
                return sb.toString().equals(s);
            }
        }
        return false;
    }

    /**
     * 530. 二叉搜索树的最小绝对差
     * 给你一个二叉搜索树的根节点 root ，返回 树中任意两不同节点值之间的最小差值 。
     * 差值是一个正数，其数值等于两值之差的绝对值。
     * 示例 1：
     * 输入：root = [4,2,6,1,3]
     * 输出：1
     * 示例 2：
     * 输入：root = [1,0,48,null,null,12,49]
     * 输出：1
     * 提示：
     * 树中节点的数目范围是 [2, 10^4]
     * 0 <= Node.val <= 10^5
     */
    public int getMinimumDifference(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        dfs2(root, list);
        int ans = 100000;
        for (int i = 1; i < list.size(); i++) {
            ans = Math.min(ans, list.get(i) - list.get(i - 1));
        }
        return ans;
    }

    private void dfs2(TreeNode node, List<Integer> list) {
        // 中序遍历
        if (node == null) {
            return;
        }
        dfs2(node.left, list);
        list.add(node.val);
        dfs2(node.right, list);
    }

    /**
     * 2389. 和有限的最长子序列
     * 给你一个长度为 n 的整数数组 nums ，和一个长度为 m 的整数数组 queries 。
     * 返回一个长度为 m 的数组 answer ，其中 answer[i] 是 nums 中 元素之和小于等于 queries[i] 的 子序列 的 最大 长度  。
     * 子序列 是由一个数组删除某些元素（也可以不删除）但不改变剩余元素顺序得到的一个数组。
     * 示例 1：
     * 输入：nums = [4,5,2,1], queries = [3,10,21]
     * 输出：[2,3,4]
     * 解释：queries 对应的 answer 如下：
     * - 子序列 [2,1] 的和小于或等于 3 。可以证明满足题目要求的子序列的最大长度是 2 ，所以 answer[0] = 2 。
     * - 子序列 [4,5,1] 的和小于或等于 10 。可以证明满足题目要求的子序列的最大长度是 3 ，所以 answer[1] = 3 。
     * - 子序列 [4,5,2,1] 的和小于或等于 21 。可以证明满足题目要求的子序列的最大长度是 4 ，所以 answer[2] = 4 。
     * 示例 2：
     * 输入：nums = [2,3,4,5], queries = [1]
     * 输出：[0]
     * 解释：空子序列是唯一一个满足元素和小于或等于 1 的子序列，所以 answer[0] = 0 。
     * 提示：
     * n == nums.length
     * m == queries.length
     * 1 <= n, m <= 1000
     * 1 <= nums[i], queries[i] <= 10^6
     */
    public int[] answerQueries(int[] nums, int[] queries) {
        Arrays.sort(nums);
        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int sum = 0;
            for (int num : nums) {
                sum += num;
                if (sum > queries[i]) {
                    break;
                }
                ans[i]++;
            }
        }
        return ans;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        int[] nums = {3,-2,1,1};
        System.out.println(Arrays.toString(constructTransformedArray(nums)));
    }
}
