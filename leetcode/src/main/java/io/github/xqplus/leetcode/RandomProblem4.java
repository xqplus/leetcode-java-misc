package io.github.xqplus.leetcode;

/**
 * 随机问题类四
 *
 * @author chenq
 * @since 2025.5.16
 */
public class RandomProblem4 {

    /**
     * 3335. 字符串转换后的长度 I
     * 给你一个字符串 s 和一个整数 t，表示要执行的 转换 次数。每次 转换 需要根据以下规则替换字符串 s 中的每个字符：
     * 如果字符是 'z'，则将其替换为字符串 "ab"。
     * 否则，将其替换为字母表中的下一个字符。例如，'a' 替换为 'b'，'b' 替换为 'c'，依此类推。
     * 返回 恰好 执行 t 次转换后得到的字符串的 长度。
     * 由于答案可能非常大，返回其对 10^9 + 7 取余的结果。
     * 示例 1：
     * 输入： s = "abcyy", t = 2
     * 输出： 7
     * 解释：
     * 第一次转换 (t = 1)
     * 'a' 变为 'b'
     * 'b' 变为 'c'
     * 'c' 变为 'd'
     * 'y' 变为 'z'
     * 'y' 变为 'z'
     * 第一次转换后的字符串为："bcdzz"
     * 第二次转换 (t = 2)
     * 'b' 变为 'c'
     * 'c' 变为 'd'
     * 'd' 变为 'e'
     * 'z' 变为 "ab"
     * 'z' 变为 "ab"
     * 第二次转换后的字符串为："cdeabab"
     * 最终字符串长度：字符串为 "cdeabab"，长度为 7 个字符。
     * 示例 2：
     * 输入： s = "azbk", t = 1
     * 输出： 5
     * 解释：
     * 第一次转换 (t = 1)
     * 'a' 变为 'b'
     * 'z' 变为 "ab"
     * 'b' 变为 'c'
     * 'k' 变为 'l'
     * 第一次转换后的字符串为："babcl"
     * 最终字符串长度：字符串为 "babcl"，长度为 5 个字符。
     * 提示：
     * 1 <= s.length <= 10^5
     * s 仅由小写英文字母组成。
     * 1 <= t <= 10^5
     */
    private static final int MOD = 1_000_000_007;
    public static int lengthAfterTransformations(String s, int t) {
        // 模拟
        int[] cntArr = new int[26];
        for (int i = 0; i < s.length(); i++) {
            cntArr[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < t; i++) {
            int prev = cntArr[0];
            for (int j = 1; j < 26; j++) {
                int curr = cntArr[j];
                cntArr[j] = prev;
                prev = curr;
            }
            cntArr[0] = prev;
            cntArr[1] = (cntArr[1] + prev) % MOD;
        }
        int ans = 0;
        for (int i = 0; i < 26; i++) {
            ans = (ans + cntArr[i]) % MOD;
        }
        return ans;
    }

    /**
     * 762. 二进制表示中质数个计算置位
     * 给你两个整数 left 和 right ，在闭区间 [left, right] 范围内，统计并返回 计算置位位数为质数 的整数个数。
     * 计算置位位数 就是二进制表示中 1 的个数。
     * 例如， 21 的二进制表示 10101 有 3 个计算置位。
     * 示例 1：
     * 输入：left = 6, right = 10
     * 输出：4
     * 解释：
     * 6 -> 110 (2 个计算置位，2 是质数)
     * 7 -> 111 (3 个计算置位，3 是质数)
     * 9 -> 1001 (2 个计算置位，2 是质数)
     * 10-> 1010 (2 个计算置位，2 是质数)
     * 共计 4 个计算置位为质数的数字。
     * 示例 2：
     * 输入：left = 10, right = 15
     * 输出：5
     * 解释：
     * 10 -> 1010 (2 个计算置位, 2 是质数)
     * 11 -> 1011 (3 个计算置位, 3 是质数)
     * 12 -> 1100 (2 个计算置位, 2 是质数)
     * 13 -> 1101 (3 个计算置位, 3 是质数)
     * 14 -> 1110 (3 个计算置位, 3 是质数)
     * 15 -> 1111 (4 个计算置位, 4 不是质数)
     * 共计 5 个计算置位为质数的数字。
     * 提示：
     * 1 <= left <= right <= 10^6
     * 0 <= right - left <= 10^4
     */
    public int countPrimeSetBits(int left, int right) {
        int ans = 0;
        for (int i = left; i <= right; i++) {
            if (isPrime(Integer.bitCount(i))) {
                ans++;
            }
        }
        return ans;
    }

    private boolean isPrime(int num) {
        if (num < 2) {
            return false;
        }
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(lengthAfterTransformations("jqktcurgdvlibczdsvnsg", 7517));
    }
}
