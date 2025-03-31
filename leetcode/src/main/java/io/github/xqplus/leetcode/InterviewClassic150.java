package io.github.xqplus.leetcode;

public class InterviewClassic150 {
    public static void main(String[] args) {
        System.out.println(addBinary("1010", "1011"));
    }

    /**
     * 给你两个二进制字符串 a 和 b ，以二进制字符串的形式返回它们的和。<p>
     * 输入：a = "1010", b = "1011"<br>
     * 输出："10101"
     */
    public static String addBinary(String a, String b) {
        String result = "";
        int indexA = a.length() - 1, indexB = b.length() - 1;
        int carry = 0;
        while (indexA >= 0 || indexB >= 0) {
            int bitA = indexA >= 0 ? a.charAt(indexA) - 48 : 0;
            int bitB = indexB >= 0 ? b.charAt(indexB) - 48 : 0;
            int add = bitA + bitB + carry;
            if (add >= 2) {
                add %= 2;
                carry = 1;
            } else {
                carry = 0;
            }
            result = add + result;
            indexA--;
            indexB--;
        }
        if (carry != 0) {
            result = carry + result;
        }
        return result;
    }
}
