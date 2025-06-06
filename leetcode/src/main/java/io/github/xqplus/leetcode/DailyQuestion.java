package io.github.xqplus.leetcode;

import java.util.*;

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

    /**
     * 1061. 按字典序排列最小的等效字符串
     * 给出长度相同的两个字符串s1 和 s2 ，还有一个字符串 baseStr 。
     * 其中  s1[i] 和 s2[i]  是一组等价字符。
     * 举个例子，如果 s1 = "abc" 且 s2 = "cde"，那么就有 'a' == 'c', 'b' == 'd', 'c' == 'e'。
     * 等价字符遵循任何等价关系的一般规则：
     * 自反性 ：'a' == 'a'
     * 对称性 ：'a' == 'b' 则必定有 'b' == 'a'
     * 传递性 ：'a' == 'b' 且 'b' == 'c' 就表明 'a' == 'c'
     * 例如， s1 = "abc" 和 s2 = "cde" 的等价信息和之前的例子一样，
     * 那么 baseStr = "eed" , "acd" 或 "aab"，这三个字符串都是等价的，而 "aab" 是 baseStr 的按字典序最小的等价字符串
     * 利用 s1 和 s2 的等价信息，找出并返回 baseStr 的按字典序排列最小的等价字符串。
     * 示例 1：
     * 输入：s1 = "parker", s2 = "morris", baseStr = "parser"
     * 输出："makkek"
     * 解释：根据 A 和 B 中的等价信息，我们可以将这些字符分为 [m,p], [a,o], [k,r,s], [e,i] 共 4 组。
     * 每组中的字符都是等价的，并按字典序排列。所以答案是 "makkek"。
     * 示例 2：
     * 输入：s1 = "hello", s2 = "world", baseStr = "hold"
     * 输出："hdld"
     * 解释：根据 A 和 B 中的等价信息，我们可以将这些字符分为 [h,w], [d,e,o], [l,r] 共 3 组。
     * 所以只有 S 中的第二个字符 'o' 变成 'd'，最后答案为 "hdld"。
     * 示例 3：
     * 输入：s1 = "leetcode", s2 = "programs", baseStr = "sourcecode"
     * 输出："aauaaaaada"
     * 解释：我们可以把 A 和 B 中的等价字符分为 [a,o,e,r,s,c], [l,p], [g,t] 和 [d,m] 共 4 组，
     * 因此 S 中除了 'u' 和 'd' 之外的所有字母都转化成了 'a'，最后答案为 "aauaaaaada"。
     * 提示：
     * 1 <= s1.length, s2.length, baseStr <= 1000
     * s1.length == s2.length
     * 字符串s1, s2, and baseStr 仅由从 'a' 到 'z' 的小写英文字母组成。
     */
    public static String smallestEquivalentString(String s1, String s2, String baseStr) {
        // 构建并查集，根据baseStr找根
        UnionFindSet unionFindSet = new UnionFindSet(26);
        for (int i = 0; i < s1.length(); i++) {
            unionFindSet.union(s1.charAt(i) - 'a', s2.charAt(i) - 'a');
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < baseStr.length(); i++) {
            int root = unionFindSet.findRoot(baseStr.charAt(i) - 'a');
            sb.append((char) ('a' + root));
        }
        return sb.toString();
    }

    /**
     * 2434. 使用机器人打印字典序最小的字符串
     * 给你一个字符串 s 和一个机器人，机器人当前有一个空字符串 t 。执行以下操作之一，直到 s 和 t 都变成空字符串：
     * 删除字符串 s 的 第一个 字符，并将该字符给机器人。机器人把这个字符添加到 t 的尾部。
     * 删除字符串 t 的 最后一个 字符，并将该字符给机器人。机器人将该字符写到纸上。
     * 请你返回纸上能写出的字典序最小的字符串。
     * 示例 1：
     * 输入：s = "zza"
     * 输出："azz"
     * 解释：用 p 表示写出来的字符串。
     * 一开始，p="" ，s="zza" ，t="" 。
     * 执行第一个操作三次，得到 p="" ，s="" ，t="zza" 。
     * 执行第二个操作三次，得到 p="azz" ，s="" ，t="" 。
     * 示例 2：
     * 输入：s = "bac"
     * 输出："abc"
     * 解释：用 p 表示写出来的字符串。
     * 执行第一个操作两次，得到 p="" ，s="c" ，t="ba" 。
     * 执行第二个操作两次，得到 p="ab" ，s="c" ，t="" 。
     * 执行第一个操作，得到 p="ab" ，s="" ，t="c" 。
     * 执行第二个操作，得到 p="abc" ，s="" ，t="" 。
     * 示例 3：
     * 输入：s = "bdda"
     * 输出："addb"
     * 解释：用 p 表示写出来的字符串。
     * 一开始，p="" ，s="bdda" ，t="" 。
     * 执行第一个操作四次，得到 p="" ，s="" ，t="bdda" 。
     * 执行第二个操作四次，得到 p="addb" ，s="" ，t="" 。
     * 提示：
     * 1 <= s.length <= 10^5
     * s 只包含小写英文字母。
     */
    public String robotWithString(String s) {
        char[] cs = s.toCharArray();
        // 统计每个字符出现的次数
        int[] cnts = new int[26];
        for (char c : cs) {
            cnts[c - 'a']++;
        }

        char min = 'a';
        Deque<Character> stack = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        for (char c : cs) {
            stack.push(c);
            cnts[c - 'a']--;
            // 找之后的最小字符
            while (min != 'z' && cnts[min - 'a'] == 0) {
                min++;
            }
            while (!stack.isEmpty() && stack.peek() <= min) {
                sb.append(stack.pop());
            }
        }
        return sb.toString();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(smallestEquivalentString("leetcode", "programs", "sourcecode"));
    }
}
