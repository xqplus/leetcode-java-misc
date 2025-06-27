package io.github.xqplus.leetcode;

import java.util.Arrays;

public class Simple3 {

    /**
     * 2037. 使每位学生都有座位的最少移动次数
     * 一个房间里有 n 个 空闲 座位和 n 名 站着的 学生，房间用一个数轴表示。给你一个长度为 n 的数组 seats ，其中 seats[i] 是第 i 个座位的位置。
     * 同时给你一个长度为 n 的数组 students ，其中 students[j] 是第 j 位学生的位置。
     * 你可以执行以下操作任意次：
     * 增加或者减少第 i 位学生的位置，每次变化量为 1 （也就是将第 i 位学生从位置 x 移动到 x + 1 或者 x - 1）
     * 请你返回使所有学生都有座位坐的 最少移动次数 ，并确保没有两位学生的座位相同。
     * 请注意，初始时有可能有多个座位或者多位学生在 同一 位置。
     * 示例 1：
     * 输入：seats = [3,1,5], students = [2,7,4]
     * 输出：4
     * 解释：学生移动方式如下：
     * - 第一位学生从位置 2 移动到位置 1 ，移动 1 次。
     * - 第二位学生从位置 7 移动到位置 5 ，移动 2 次。
     * - 第三位学生从位置 4 移动到位置 3 ，移动 1 次。
     * 总共 1 + 2 + 1 = 4 次移动。
     * 示例 2：
     * 输入：seats = [4,1,5,9], students = [1,3,2,6]
     * 输出：7
     * 解释：学生移动方式如下：
     * - 第一位学生不移动。
     * - 第二位学生从位置 3 移动到位置 4 ，移动 1 次。
     * - 第三位学生从位置 2 移动到位置 5 ，移动 3 次。
     * - 第四位学生从位置 6 移动到位置 9 ，移动 3 次。
     * 总共 0 + 1 + 3 + 3 = 7 次移动。
     * 示例 3：
     * 输入：seats = [2,2,6,6], students = [1,3,2,6]
     * 输出：4
     * 解释：学生移动方式如下：
     * - 第一位学生从位置 1 移动到位置 2 ，移动 1 次。
     * - 第二位学生从位置 3 移动到位置 6 ，移动 3 次。
     * - 第三位学生不移动。
     * - 第四位学生不移动。
     * 总共 1 + 3 + 0 + 0 = 4 次移动。
     * 提示：
     * n == seats.length == students.length
     * 1 <= n <= 100
     * 1 <= seats[i], students[j] <= 100
     */
    public int minMovesToSeat(int[] seats, int[] students) {
        Arrays.sort(seats);
        Arrays.sort(students);
        int ans = 0;
        for (int i = 0; i < seats.length; i++) {
            ans += Math.abs(seats[i] - students[i]);
        }
        return ans;
    }

    /**
     * 2710. 移除字符串中的尾随零
     * 给你一个用字符串表示的正整数 num ，请你以字符串形式返回不含尾随零的整数 num 。
     * 示例 1：
     * 输入：num = "51230100"
     * 输出："512301"
     * 解释：整数 "51230100" 有 2 个尾随零，移除并返回整数 "512301" 。
     * 示例 2：
     * 输入：num = "123"
     * 输出："123"
     * 解释：整数 "123" 不含尾随零，返回整数 "123" 。
     * 提示：
     * 1 <= num.length <= 1000
     * num 仅由数字 0 到 9 组成
     * num 不含前导零
     */
    public String removeTrailingZeros(String num) {
        int n = num.length(), i = n - 1;
        while (num.charAt(i) == '0') {
            i--;
        }
        return i == n - 1 ? num : num.substring(0, i + 1);
    }

    /**
     * 1539. 第 k 个缺失的正整数
     * 给你一个 严格升序排列 的正整数数组 arr 和一个整数 k 。
     * 请你找到这个数组里第 k 个缺失的正整数。
     * 示例 1：
     * 输入：arr = [2,3,4,7,11], k = 5
     * 输出：9
     * 解释：缺失的正整数包括 [1,5,6,8,9,10,12,13,...] 。第 5 个缺失的正整数为 9 。
     * 示例 2：
     * 输入：arr = [1,2,3,4], k = 2
     * 输出：6
     * 解释：缺失的正整数包括 [5,6,7,...] 。第 2 个缺失的正整数为 6 。
     * 提示：
     * 1 <= arr.length <= 1000
     * 1 <= arr[i] <= 1000
     * 1 <= k <= 1000
     * 对于所有 1 <= i < j <= arr.length 的 i 和 j 满足 arr[i] < arr[j]
     * 进阶：
     * 你可以设计一个时间复杂度小于 O(n) 的算法解决此问题吗？
     */
    public static int findKthPositive(int[] arr, int k) {
        int n = arr.length;
        if (arr[n - 1] - n < k) {
            return k + n;
        }
        // 二分变法
        int l = 0, r = n - 1;
        while (l < r) {
            int mid = l + ((r - l) >> 1);
            int m = arr[mid] - mid - 1; // 表示arr[mid]之前有多少个缺失数
            if (m < k) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }
        return r + k;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        int[] nums = {1,7,11,14,29,31,40,44};
        System.out.println(findKthPositive(nums, 20));
    }
}
