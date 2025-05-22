package io.github.xqplus.leetcode;

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
     * @param args
     */
    public static void main(String[] args) {
        // ('I', 'V', 'X', 'L', 'C', 'D', 'M')
        System.out.println((int) 'I' - 'A');
        System.out.println((int) 'V' - 'A');
        System.out.println((int) 'X' - 'A');
        System.out.println((int) 'L' - 'A');
        System.out.println((int) 'C' - 'A');
        System.out.println((int) 'D' - 'A');
        System.out.println((int) 'M' - 'A');
        System.out.println((int) 'I' + 'V' - 'A');
        System.out.println((int) 'I' + 'X' - 'A');
        System.out.println((int) 'X' + 'L' - 'A');
        System.out.println((int) 'X' + 'C' - 'A');
        System.out.println((int) 'C' + 'D' - 'A');
        System.out.println((int) 'C' + 'M' - 'A');
    }
}
