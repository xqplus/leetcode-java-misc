package io.github.xqplus.leetcode;

import java.util.ArrayList;
import java.util.List;

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

    public static void main(String[] args) {
        System.out.println(answerString("aann", 2));
    }
}
