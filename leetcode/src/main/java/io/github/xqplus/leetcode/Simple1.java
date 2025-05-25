package io.github.xqplus.leetcode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(countTime("?5:00"));
    }
}
