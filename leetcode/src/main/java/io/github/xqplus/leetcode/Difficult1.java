package io.github.xqplus.leetcode;

import java.util.Arrays;

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

    public static void main(String[] args) {
        System.out.println(minInsertions("leetcode"));
    }
}
