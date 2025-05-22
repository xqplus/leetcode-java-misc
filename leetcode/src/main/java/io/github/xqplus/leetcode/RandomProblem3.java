package io.github.xqplus.leetcode;

import java.util.*;

/**
 * 随机问题类三
 *
 * @author chenq
 * @since 2025.5.13
 */
public class RandomProblem3 {

    /**
     * 598. 区间加法 II
     * 给你一个 m x n 的矩阵 M 和一个操作数组 op 。矩阵初始化时所有的单元格都为 0 。
     * ops[i] = [ai, bi] 意味着当所有的 0 <= x < ai 和 0 <= y < bi 时， M[x][y] 应该加 1。
     * 在 执行完所有操作后 ，计算并返回 矩阵中最大整数的个数 。
     * 示例 1:
     * 输入: m = 3, n = 3，ops = [[2,2],[3,3]]
     * 输出: 4
     * 解释: M 中最大的整数是 2, 而且 M 中有4个值为2的元素。因此返回 4。
     * 示例 2:
     * 输入: m = 3, n = 3, ops = [[2,2],[3,3],[3,3],[3,3],[2,2],[3,3],[3,3],[3,3],[2,2],[3,3],[3,3],[3,3]]
     * 输出: 4
     * 示例 3:
     * 输入: m = 3, n = 3, ops = []
     * 输出: 9
     * 提示:
     * 1 <= m, n <= 4 * 10^4
     * 0 <= ops.length <= 10^4
     * ops[i].length == 2
     * 1 <= ai <= m
     * 1 <= bi <= n
     */
    public static int maxCount(int m, int n, int[][] ops) {
        // 只需要找 ops 中出现的最小 x,y 就行了
        int x = m, y = n;
        for (int[] op : ops) {
            if (op[0] < x) {
                x = op[0];
            }
            if (op[1] < y) {
                y = op[1];
            }
        }
        return x * y;
    }

    /**
     * 3541. 找到频率最高的元音和辅音
     * 给你一个由小写英文字母（'a' 到 'z'）组成的字符串 s。
     * 你的任务是找出出现频率 最高 的元音（'a'、'e'、'i'、'o'、'u' 中的一个）和出现频率最高的辅音（除元音以外的所有字母），并返回这两个频率之和。
     * 注意：如果有多个元音或辅音具有相同的最高频率，可以任选其中一个。如果字符串中没有元音或没有辅音，则其频率视为 0。
     * 一个字母 x 的 频率 是它在字符串中出现的次数。
     * 示例 1：
     * 输入: s = "successes"
     * 输出: 6
     * 解释:
     * 元音有：'u' 出现 1 次，'e' 出现 2 次。最大元音频率 = 2。
     * 辅音有：'s' 出现 4 次，'c' 出现 2 次。最大辅音频率 = 4。
     * 输出为 2 + 4 = 6。
     * 示例 2：
     * 输入: s = "aeiaeia"
     * 输出: 3
     * 解释:
     * 元音有：'a' 出现 3 次，'e' 出现 2 次，'i' 出现 2 次。最大元音频率 = 3。
     * s 中没有辅音。因此，最大辅音频率 = 0。
     * 输出为 3 + 0 = 3。
     * 提示:
     * 1 <= s.length <= 100
     * s 只包含小写英文字母
     */
    public int maxFreqSum(String s) {
        int maxy = 0, maxf = 0;
        int[] freq = new int[26];
        for (char c : s.toCharArray()) {
            int f = ++freq[c - 'a'];
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                if (f > maxy) {
                    maxy = f;
                }
            } else {
                if (f > maxf) {
                    maxf = f;
                }
            }
        }
        return maxy + maxf;
    }

    /**
     * 784. 字母大小写全排列
     * 给定一个字符串 s ，通过将字符串 s 中的每个字母转变大小写，我们可以获得一个新的字符串。
     * 返回 所有可能得到的字符串集合 。以 任意顺序 返回输出。
     * 示例 1：
     * 输入：s = "a1b2"
     * 输出：["a1b2", "a1B2", "A1b2", "A1B2"]
     * 示例 2:
     * 输入: s = "3z4"
     * 输出: ["3z4","3Z4"]
     * 提示:
     * 1 <= s.length <= 12
     * s 由小写英文字母、大写英文字母和数字组成
     */
    public static List<String> letterCasePermutation(String s) {
        // 递归 + 回溯
        List<String> ans = new ArrayList<>();
        rec(ans, new StringBuffer(), s);
        return ans;
    }

    private static void rec(List<String> ans, StringBuffer sb, String s) {
        if (sb.length() == s.length()) {
            ans.add(sb.toString());
            return;
        }
        char c = s.charAt(sb.length());
        sb.append(c);
        rec(ans, sb, s);
        sb.deleteCharAt(sb.length() - 1);

        if (c >= 'a' && c <= 'z') {
            sb.append((char) (c - 32));
            rec(ans, sb, s);
            sb.deleteCharAt(sb.length() - 1);
        } else if (c >= 'A' && c <= 'Z') {
            sb.append((char) (c + 32));
            rec(ans, sb, s);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    /**
     * 1600. 王位继承顺序
     * 一个王国里住着国王、他的孩子们、他的孙子们等等。每一个时间点，这个家庭里有人出生也有人死亡。
     * 这个王国有一个明确规定的王位继承顺序，第一继承人总是国王自己。我们定义递归函数 Successor(x, curOrder) ，
     * 给定一个人 x 和当前的继承顺序，该函数返回 x 的下一继承人。
     * Successor(x, curOrder):
     * 如果 x 没有孩子或者所有 x 的孩子都在 curOrder 中：
     * 如果 x 是国王，那么返回 null
     * 否则，返回 Successor(x 的父亲, curOrder)
     * 否则，返回 x 不在 curOrder 中最年长的孩子
     * 比方说，假设王国由国王，他的孩子 Alice 和 Bob （Alice 比 Bob 年长）和 Alice 的孩子 Jack 组成。
     * 一开始， curOrder 为 ["king"].
     * 调用 Successor(king, curOrder) ，返回 Alice ，所以我们将 Alice 放入 curOrder 中，得到 ["king", "Alice"] 。
     * 调用 Successor(Alice, curOrder) ，返回 Jack ，所以我们将 Jack 放入 curOrder 中，得到 ["king", "Alice", "Jack"] 。
     * 调用 Successor(Jack, curOrder) ，返回 Bob ，所以我们将 Bob 放入 curOrder 中，得到 ["king", "Alice", "Jack", "Bob"] 。
     * 调用 Successor(Bob, curOrder) ，返回 null 。最终得到继承顺序为 ["king", "Alice", "Jack", "Bob"] 。
     * 通过以上的函数，我们总是能得到一个唯一的继承顺序。
     * 请你实现 ThroneInheritance 类：
     * ThroneInheritance(string kingName) 初始化一个 ThroneInheritance 类的对象。国王的名字作为构造函数的参数传入。
     * void birth(string parentName, string childName) 表示 parentName 新拥有了一个名为 childName 的孩子。
     * void death(string name) 表示名为 name 的人死亡。一个人的死亡不会影响 Successor 函数，也不会影响当前的继承顺序。你可以只将这个人标记为死亡状态。
     * string[] getInheritanceOrder() 返回 除去 死亡人员的当前继承顺序列表。
     * 示例：
     * 输入：
     * ["ThroneInheritance", "birth", "birth", "birth", "birth", "birth", "birth", "getInheritanceOrder", "death", "getInheritanceOrder"]
     * [["king"], ["king", "andy"], ["king", "bob"], ["king", "catherine"], ["andy", "matthew"], ["bob", "alex"], ["bob", "asha"], [null], ["bob"], [null]]
     * 输出：
     * [null, null, null, null, null, null, null, ["king", "andy", "matthew", "bob", "alex", "asha", "catherine"], null, ["king", "andy", "matthew", "alex", "asha", "catherine"]]
     * 解释：
     * ThroneInheritance t= new ThroneInheritance("king"); // 继承顺序：king
     * t.birth("king", "andy"); // 继承顺序：king > andy
     * t.birth("king", "bob"); // 继承顺序：king > andy > bob
     * t.birth("king", "catherine"); // 继承顺序：king > andy > bob > catherine
     * t.birth("andy", "matthew"); // 继承顺序：king > andy > matthew > bob > catherine
     * t.birth("bob", "alex"); // 继承顺序：king > andy > matthew > bob > alex > catherine
     * t.birth("bob", "asha"); // 继承顺序：king > andy > matthew > bob > alex > asha > catherine
     * t.getInheritanceOrder(); // 返回 ["king", "andy", "matthew", "bob", "alex", "asha", "catherine"]
     * t.death("bob"); // 继承顺序：king > andy > matthew > bob（已经去世）> alex > asha > catherine
     * t.getInheritanceOrder(); // 返回 ["king", "andy", "matthew", "alex", "asha", "catherine"]
     * 提示：
     * 1 <= kingName.length, parentName.length, childName.length, name.length <= 15
     * kingName，parentName， childName 和 name 仅包含小写英文字母。
     * 所有的参数 childName 和 kingName 互不相同。
     * 所有 death 函数中的死亡名字 name 要么是国王，要么是已经出生了的人员名字。
     * 每次调用 birth(parentName, childName) 时，测试用例都保证 parentName 对应的人员是活着的。
     * 最多调用 10^5 次birth 和 death 。
     * 最多调用 10 次 getInheritanceOrder 。
     */
    static class ThroneInheritance {

        private Person king;
        private Map<String, Person> namePerson;

        public ThroneInheritance(String kingName) {
            king = new Person(kingName);
            namePerson = new HashMap<>();
            namePerson.put(kingName, king);
        }

        public void birth(String parentName, String childName) {
            Person child = new Person(childName);
            namePerson.put(childName, child);
            namePerson.get(parentName).addChild(child);
        }

        public void death(String name) {
            namePerson.get(name).setAlive(false);
        }

        public List<String> getInheritanceOrder() {
            List<String> order = new ArrayList<>();
            dfs(order, king);
            return order;
        }

        private void dfs(List<String> names, Person person) {
            if (person.isAlive()) {
                names.add(person.getName());
            }
            if (person.getChildren() == null) {
                return;
            }
            person.getChildren().forEach(c -> dfs(names, c));
        }

        static class Person {
            private String name;
            private boolean alive;
            private List<Person> children;

            public Person(String name) {
                this.name = name;
                this.alive = true;
            }

            public String getName() {
                return name;
            }

            public void setAlive(boolean alive) {
                this.alive = alive;
            }

            public boolean isAlive() {
                return alive;
            }

            public List<Person> getChildren() {
                return children;
            }

            public void addChild(Person child) {
                if (children == null) {
                    children = new ArrayList<>();
                }
                children.add(child);
            }
        }
    }

    /**
     * 1220. 统计元音字母序列的数目
     * 给你一个整数 n，请你帮忙统计一下我们可以按下述规则形成多少个长度为 n 的字符串：
     * 字符串中的每个字符都应当是小写元音字母（'a', 'e', 'i', 'o', 'u'）
     * 每个元音 'a' 后面都只能跟着 'e'
     * 每个元音 'e' 后面只能跟着 'a' 或者是 'i'
     * 每个元音 'i' 后面 不能 再跟着另一个 'i'
     * 每个元音 'o' 后面只能跟着 'i' 或者是 'u'
     * 每个元音 'u' 后面只能跟着 'a'
     * 由于答案可能会很大，所以请你返回 模 10^9 + 7 之后的结果。
     * 示例 1：
     * 输入：n = 1
     * 输出：5
     * 解释：所有可能的字符串分别是："a", "e", "i" , "o" 和 "u"。
     * 示例 2：
     * 输入：n = 2
     * 输出：10
     * 解释：所有可能的字符串分别是："ae", "ea", "ei", "ia", "ie", "io", "iu", "oi", "ou" 和 "ua"。
     * 示例 3：
     * 输入：n = 5
     * 输出：68
     * 提示：
     * 1 <= n <= 2 * 10^4
     */
    public static int countVowelPermutation(int n) {
//        long[][] dp = new long[n + 1][5]; // dp[i][j] 表示生成长度为i，以元音字符j结尾的字符串个数
//        dp[1][0] = dp[1][1] = dp[1][2] = dp[1][3] = dp[1][4] = 1;
//        // a前面只能是 e i u
//        // e前面只能是 a i
//        // i前面只能是 e o
//        // o前面只能是 i
//        // u前面只能是 i o
//        long mod = 1000000007;
//        for (int i = 2; i <= n; i++) {
//            dp[i][0] = (dp[i - 1][1] + dp[i - 1][2] + dp[i - 1][4]) % mod;
//            dp[i][1] = (dp[i - 1][0] + dp[i - 1][2]) % mod;
//            dp[i][2] = (dp[i - 1][1] + dp[i - 1][3]) % mod;
//            dp[i][3] = dp[i - 1][2];
//            dp[i][4] = (dp[i - 1][2] + dp[i - 1][3]) % mod;
//        }
//        long sum = dp[n][0] + dp[n][1] + dp[n][2] + dp[n][3] + dp[n][4];
//        return (int) (sum % 1000000007);

        // a前面只能是 e i u
        // e前面只能是 a i
        // i前面只能是 e o
        // o前面只能是 i
        // u前面只能是 i o
//        if (n == 1) {
//            return 5;
//        }
//        long[] dp = new long[5];
//        long[] pdp = new long[5];
//        pdp[0] = pdp[1] = pdp[2] = pdp[3] = pdp[4] = 1;
//        long mod = 1000000007;
//        for (int i = 2; i <= n; i++) {
//            dp[0] = (pdp[1] + pdp[2] + pdp[4]) % mod;
//            dp[1] = (pdp[0] + pdp[2]) % mod;
//            dp[2] = (pdp[1] + pdp[3]) % mod;
//            dp[3] = pdp[2];
//            dp[4] = (pdp[2] + pdp[3]) % mod;
//
//            pdp[0] = dp[0];
//            pdp[1] = dp[1];
//            pdp[2] = dp[2];
//            pdp[3] = dp[3];
//            pdp[4] = dp[4];
//        }
//        return (int) ((dp[0] + dp[1] + dp[2] + dp[3] + dp[4]) % mod);

        // a前面只能是 e i u
        // e前面只能是 a i
        // i前面只能是 e o
        // o前面只能是 i
        // u前面只能是 i o
        long a = 1, e = 1, i = 1, o = 1, u = 1, mod = 1000000007;
        for (int k = 2; k <= n; k++) {
            long aa = (e + i + u) % mod;
            long ee = (a + i) % mod;
            long ii = (e + o) % mod;
            long oo = i;
            long uu = (i + o) % mod;

            a = aa;
            e = ee;
            i = ii;
            o = oo;
            u = uu;
        }
        return (int) ((a + e + i + o + u) % mod);
    }

    /**
     * LCR 073. 爱吃香蕉的狒狒
     * 狒狒喜欢吃香蕉。这里有 N 堆香蕉，第 i 堆中有 piles[i] 根香蕉。警卫已经离开了，将在 H 小时后回来。
     * 狒狒可以决定她吃香蕉的速度 K （单位：根/小时）。每个小时，她将会选择一堆香蕉，从中吃掉 K 根。
     * 如果这堆香蕉少于 K 根，她将吃掉这堆的所有香蕉，然后这一小时内不会再吃更多的香蕉，下一个小时才会开始吃另一堆的香蕉。
     * 狒狒喜欢慢慢吃，但仍然想在警卫回来前吃掉所有的香蕉。
     * 返回她可以在 H 小时内吃掉所有香蕉的最小速度 K（K 为整数）。
     * 示例 1：
     * 输入: piles = [3,6,7,11], H = 8
     * 输出: 4
     * 示例 2：
     * 输入: piles = [30,11,23,4,20], H = 5
     * 输出: 30
     * 示例 3：
     * 输入: piles = [30,11,23,4,20], H = 6
     * 输出: 23
     * 提示：
     * 1 <= piles.length <= 10^4
     * piles.length <= H <= 10^9
     * 1 <= piles[i] <= 10^9
     */
    public int minEatingSpeed(int[] piles, int h) {
        int low = 1, high = 0;
        for (int pile : piles) {
            high = Math.max(high, pile);
        }
        int k = high;
        while (low < high) {
            int speed = low + (high - low) / 2;
            int time = 0;
            for (int pile : piles) {
                time += (pile + speed - 1) / speed;
            }
            if (time <= h) {
                high = speed;
                k = speed;
            } else {
                low = speed + 1;
            }
        }
        return k;
    }

    /**
     * 503. 下一个更大元素 II
     * 给定一个循环数组 nums （ nums[nums.length - 1] 的下一个元素是 nums[0] ），返回 nums 中每个元素的 下一个更大元素 。
     * 数字 x 的 下一个更大的元素 是按数组遍历顺序，这个数字之后的第一个比它更大的数，这意味着你应该循环地搜索它的下一个更大的数。如果不存在，则输出 -1 。
     * 示例 1:
     * 输入: nums = [1,2,1]
     * 输出: [2,-1,2]
     * 解释: 第一个 1 的下一个更大的数是 2；
     * 数字 2 找不到下一个更大的数；
     * 第二个 1 的下一个最大的数需要循环搜索，结果也是 2。
     * 示例 2:
     * 输入: nums = [1,2,3,4,3]
     * 输出: [2,3,4,-1,4]
     * 提示:
     * 1 <= nums.length <= 10^4
     * -10^9 <= nums[i] <= 10^9
     */
    public static int[] nextGreaterElements(int[] nums) {
//        // 1.暴力搜索
//        int[] ans = new int[nums.length];
//        for (int i = 0; i < nums.length; i++) {
//            int idx = -1;
//            for (int j = i + 1; j < nums.length; j++) {
//                if (nums[j] > nums[i]) {
//                    idx = j;
//                    break;
//                }
//            }
//            if (idx == -1) {
//                for (int j = 0; j < i; j++) {
//                    if (nums[j] > nums[i]) {
//                        idx = j;
//                        break;
//                    }
//                }
//            }
//            ans[i] = idx != -1 ? nums[idx] : -1;
//        }
//        return ans;

        // 2.单调栈实现
        int n = nums.length;
        int[] ans = new int[nums.length];
        Arrays.fill(ans, -1);
        Deque<Integer> stack = new LinkedList<>();
        // 实现循环访问，上界为 n * 2 - 1
        for (int i = 0; i < n * 2 - 1; i++) {
            int idx = i % n;
            while (!stack.isEmpty() && nums[stack.peek()] < nums[idx]) {
                ans[stack.pop()] = nums[idx];
            }
            stack.push(idx);
        }
        return ans;
    }

    /**
     * 1488. 避免洪水泛滥
     * 你的国家有无数个湖泊，所有湖泊开始都是空的。当第 n 个湖泊下雨前是空的，那么它就会装满水。
     * 如果第 n 个湖泊下雨前是 满的 ，这个湖泊会发生 洪水 。你的目标是避免任意一个湖泊发生洪水。
     * 给你一个整数数组 rains ，其中：
     * rains[i] > 0 表示第 i 天时，第 rains[i] 个湖泊会下雨。
     * rains[i] == 0 表示第 i 天没有湖泊会下雨，你可以选择 一个 湖泊并 抽干 这个湖泊的水。
     * 请返回一个数组 ans ，满足：
     * ans.length == rains.length
     * 如果 rains[i] > 0 ，那么ans[i] == -1 。
     * 如果 rains[i] == 0 ，ans[i] 是你第 i 天选择抽干的湖泊。
     * 如果有多种可行解，请返回它们中的 任意一个 。如果没办法阻止洪水，请返回一个 空的数组 。
     * 请注意，如果你选择抽干一个装满水的湖泊，它会变成一个空的湖泊。但如果你选择抽干一个空的湖泊，那么将无事发生。
     * 示例 1：
     * 输入：rains = [1,2,3,4]
     * 输出：[-1,-1,-1,-1]
     * 解释：第一天后，装满水的湖泊包括 [1]
     * 第二天后，装满水的湖泊包括 [1,2]
     * 第三天后，装满水的湖泊包括 [1,2,3]
     * 第四天后，装满水的湖泊包括 [1,2,3,4]
     * 没有哪一天你可以抽干任何湖泊的水，也没有湖泊会发生洪水。
     * 示例 2：
     * 输入：rains = [1,2,0,0,2,1]
     * 输出：[-1,-1,2,1,-1,-1]
     * 解释：第一天后，装满水的湖泊包括 [1]
     * 第二天后，装满水的湖泊包括 [1,2]
     * 第三天后，我们抽干湖泊 2 。所以剩下装满水的湖泊包括 [1]
     * 第四天后，我们抽干湖泊 1 。所以暂时没有装满水的湖泊了。
     * 第五天后，装满水的湖泊包括 [2]。
     * 第六天后，装满水的湖泊包括 [1,2]。
     * 可以看出，这个方案下不会有洪水发生。同时， [-1,-1,1,2,-1,-1] 也是另一个可行的没有洪水的方案。
     * 示例 3：
     * 输入：rains = [1,2,0,1,2]
     * 输出：[]
     * 解释：第二天后，装满水的湖泊包括 [1,2]。我们可以在第三天抽干一个湖泊的水。
     * 但第三天后，湖泊 1 和 2 都会再次下雨，所以不管我们第三天抽干哪个湖泊的水，另一个湖泊都会发生洪水。
     * 提示：
     * 1 <= rains.length <= 10^5
     * 0 <= rains[i] <= 10^9
     */
    public static int[] avoidFlood(int[] rains) {
        int[] ans = new int[rains.length];
        // 存放抽水的日期
        List<Integer> dryDays = new ArrayList<>();
        // 存放下雨湖泊-下雨日期键值对
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < rains.length; i++) {
            if (rains[i] == 0) {
                // 当天抽水
                dryDays.add(i);
                continue;
            }
            ans[i] = -1;
            if (map.containsKey(rains[i])) {
                // 抽水日须在下雨日之后的最近日期
                int prev = map.get(rains[i]), dry = 0, idx = 0;
                for (int j = 0; j < dryDays.size(); j++) {
                    if (dryDays.get(j) > prev) {
                        dry = dryDays.get(j);
                        idx = j;
                        break;
                    }
                }
                if (dry == 0) {
                    return new int[0];
                }

                ans[dry] = rains[i];
                dryDays.remove(idx);
            }
            map.put(rains[i], i);
        }
        dryDays.forEach(i -> ans[i] = 1);
        return ans;
    }

    /**
     * 3280. 将日期转换为二进制表示
     * 给你一个字符串 date，它的格式为 yyyy-mm-dd，表示一个公历日期。
     * date 可以重写为二进制表示，只需要将年、月、日分别转换为对应的二进制表示（不带前导零）并遵循 year-month-day 的格式。
     * 返回 date 的 二进制 表示。
     * 示例 1：
     * 输入： date = "2080-02-29"
     * 输出： "100000100000-10-11101"
     * 解释：
     * 100000100000, 10 和 11101 分别是 2080, 02 和 29 的二进制表示。
     * 示例 2：
     * 输入： date = "1900-01-01"
     * 输出： "11101101100-1-1"
     * 解释：
     * 11101101100, 1 和 1 分别是 1900, 1 和 1 的二进制表示。
     * 提示：
     * date.length == 10
     * date[4] == date[7] == '-'，其余的 date[i] 都是数字。
     * 输入保证 date 代表一个有效的公历日期，日期范围从 1900 年 1 月 1 日到 2100 年 12 月 31 日（包括这两天）。
     */
    public static String convertDateToBinary(String date) {
        String ans = "";
        String[] split = date.split("-");
        for (int i = 0; i < 3; i++) {
            ans += Integer.toBinaryString(Integer.parseInt(split[i]));
            if (i < 2) {
                ans += "-";
            }
        }
        return ans;
    }

    /**
     * 面试题 16.16. 部分排序
     * 给定一个整数数组，编写一个函数，找出索引m和n，只要将索引区间[m,n]的元素排好序，整个数组就是有序的。
     * 注意：n-m尽量最小，也就是说，找出符合条件的最短序列。函数返回值为[m,n]，若不存在这样的m和n（例如整个数组是有序的），请返回[-1,-1]。
     * 示例：
     * 输入： [1,2,4,7,10,11,7,12,6,7,16,18,19]
     * 输出： [3,9]
     * 提示：
     * 0 <= len(array) <= 1000000
     */
    public static int[] subSort(int[] array) {
        // 若当前数左边存在比它大的值，或者右边存在比它小的值，就需要排序
        if (array.length == 0) {
            return new int[]{-1, -1};
        }
        int max = array[0], n = -1;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            } else if (array[i] < max) {
                n = i;
            }
        }
        if (n == -1) {
            return new int[]{-1, -1};
        }

        int min = array[array.length - 1], m = -1;
        for (int i = array.length - 2; i >= 0; i--) {
            if (array[i] < min) {
                min = array[i];
            } else if (array[i] > min) {
                m = i;
            }
        }
        return m == -1 ? new int[]{-1, -1} : new int[]{m, n};
    }

    /**
     * 936. 戳印序列
     * 你想要用小写字母组成一个目标字符串 target。
     * 开始的时候，序列由 target.length 个 '?' 记号组成。而你有一个小写字母印章 stamp。
     * 在每个回合，你可以将印章放在序列上，并将序列中的每个字母替换为印章上的相应字母。你最多可以进行 10 * target.length  个回合。
     * 举个例子，如果初始序列为 "?????"，而你的印章 stamp 是 "abc"，那么在第一回合，你可以得到 "abc??"、"?abc?"、"??abc"。
     * （请注意，印章必须完全包含在序列的边界内才能盖下去。）
     * 如果可以印出序列，那么返回一个数组，该数组由每个回合中被印下的最左边字母的索引组成。如果不能印出序列，就返回一个空数组。
     * 例如，如果序列是 "ababc"，印章是 "abc"，那么我们就可以返回与操作 "?????" -> "abc??" -> "ababc" 相对应的答案 [0, 2]；
     * 另外，如果可以印出序列，那么需要保证可以在 10 * target.length 个回合内完成。任何超过此数字的答案将不被接受。
     * 示例 1：
     * 输入：stamp = "abc", target = "ababc"
     * 输出：[0,2]
     * （[1,0,2] 以及其他一些可能的结果也将作为答案被接受）
     * 示例 2：
     * 输入：stamp = "abca", target = "aabcaca"
     * 输出：[3,0,1]
     * 提示：
     * 1 <= stamp.length <= target.length <= 1000
     * stamp 和 target 只包含小写字母。
     */
    public static int[] movesToStamp(String stamp, String target) {
        // 逆推，将 stamp 印戳到 target 上将其变成只有 ? 的序列
        if (stamp.length() == target.length()) {
            return stamp.equals(target) ? new int[]{0} : new int[0];
        }
        char[] stampCharArr = stamp.toCharArray();
        char[] targetCharArr = target.toCharArray();
        int stampLen = stampCharArr.length, targetLen = targetCharArr.length;
        int sum = 0; // targetCharArr中 ? 的数量
        List<Integer> idxList = new ArrayList<>();
        while (sum < targetLen) {
            int idx = -1;
            for (int i = 0; i < targetLen - stampLen + 1; i++) {
                // TODO 优化：记录下一个开头字母的下标，可以跳过一些项
                boolean found = true, hasLetter = false;
                for (int j = 0; j < stampLen; j++) {
                    if (targetCharArr[i + j] != '?') {
                        hasLetter = true;
                        if (targetCharArr[i + j] != stampCharArr[j]) {
                            found = false;
                            break;
                        }
                    }
                }
                if (found && hasLetter) {
                    idx = i;
                    break;
                }
            }
            if (idx == -1) {
                return new int[0];
            }
            idxList.add(idx);
            for (int i = idx; i < idx + stampLen; i++) {
                if (targetCharArr[i] != '?') {
                    targetCharArr[i] = '?';
                    sum++;
                }
            }
        }
        Collections.reverse(idxList);
        return idxList.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * 282. 给表达式添加运算符
     * 给定一个仅包含数字 0-9 的字符串 num 和一个目标值整数 target ，在 num 的数字之间添加 二元 运算符（不是一元）+、- 或 * ，
     * 返回 所有 能够得到 target 的表达式。
     * 注意，返回表达式中的操作数 不应该 包含前导零。
     * 示例 1:
     * 输入: num = "123", target = 6
     * 输出: ["1+2+3", "1*2*3"]
     * 解释: “1*2*3” 和 “1+2+3” 的值都是6。
     * 示例 2:
     * 输入: num = "232", target = 8
     * 输出: ["2*3+2", "2+3*2"]
     * 解释: “2*3+2” 和 “2+3*2” 的值都是8。
     * 示例 3:
     * 输入: num = "3456237490", target = 9191
     * 输出: []
     * 解释: 表达式 “3456237490” 无法得到 9191 。
     * 提示：
     * 1 <= num.length <= 10
     * num 仅含数字
     * -2^31 <= target <= 2^31 - 1
     */
    public static List<String> addOperators(String num, int target) {
        // 递归+回溯，注意越界
        List<String> ans = new ArrayList<>();
        dfs(ans, num, target, new StringBuffer(), 0, 0, 0);
        return ans;
    }

    private static void dfs(List<String> ans, String num, int target, StringBuffer sb, long sum, long mul, int i) {
        // 边界条件
        if (i == num.length()) { // 这里不能通过 sum > target 进行剪枝，存在减法
            if (sum == target) {
                ans.add(sb.toString());
            }
            return;
        }

        int opIdx = sb.length(); // 记录操作符下标，便于回溯
        if (i > 0) {
            sb.append(' '); // 操作符占位
        }

        long value = 0; // 表达式下一个数字
        for (int j = i; j < num.length() && (j == i || num.charAt(i) != '0'); j++) {
            char c = num.charAt(j);
            value = value * 10 + c - '0';
            sb.append(c);

            if (i == 0) {
                // 表达式首位，必须为数字
                dfs(ans, num, target, sb, value, value, j + 1);
            } else {
                sb.setCharAt(opIdx, '+');
                dfs(ans, num, target, sb, sum + value, value, j + 1);

                sb.setCharAt(opIdx, '-');
                dfs(ans, num, target, sb, sum - value, -value, j + 1);

                sb.setCharAt(opIdx, '*');
                dfs(ans, num, target, sb, sum - mul + mul * value, mul * value, j + 1);
            }
        }
        sb.setLength(opIdx);
    }

    /**
     * 2363. 合并相似的物品
     * 给你两个二维整数数组 items1 和 items2 ，表示两个物品集合。每个数组 items 有以下特质：
     * items[i] = [valuei, weighti] 其中 valuei 表示第 i 件物品的 价值 ，weighti 表示第 i 件物品的 重量 。
     * items 中每件物品的价值都是 唯一的 。
     * 请你返回一个二维数组 ret，其中 ret[i] = [valuei, weighti]， weighti 是所有价值为 valuei 物品的 重量之和 。
     * 注意：ret 应该按价值 升序 排序后返回。
     * 示例 1：
     * 输入：items1 = [[1,1],[4,5],[3,8]], items2 = [[3,1],[1,5]]
     * 输出：[[1,6],[3,9],[4,5]]
     * 解释：
     * value = 1 的物品在 items1 中 weight = 1 ，在 items2 中 weight = 5 ，总重量为 1 + 5 = 6 。
     * value = 3 的物品再 items1 中 weight = 8 ，在 items2 中 weight = 1 ，总重量为 8 + 1 = 9 。
     * value = 4 的物品在 items1 中 weight = 5 ，总重量为 5 。
     * 所以，我们返回 [[1,6],[3,9],[4,5]] 。
     * 示例 2：
     * 输入：items1 = [[1,1],[3,2],[2,3]], items2 = [[2,1],[3,2],[1,3]]
     * 输出：[[1,4],[2,4],[3,4]]
     * 解释：
     * value = 1 的物品在 items1 中 weight = 1 ，在 items2 中 weight = 3 ，总重量为 1 + 3 = 4 。
     * value = 2 的物品在 items1 中 weight = 3 ，在 items2 中 weight = 1 ，总重量为 3 + 1 = 4 。
     * value = 3 的物品在 items1 中 weight = 2 ，在 items2 中 weight = 2 ，总重量为 2 + 2 = 4 。
     * 所以，我们返回 [[1,4],[2,4],[3,4]] 。
     * 示例 3：
     * 输入：items1 = [[1,3],[2,2]], items2 = [[7,1],[2,2],[1,4]]
     * 输出：[[1,7],[2,4],[7,1]]
     * 解释：
     * value = 1 的物品在 items1 中 weight = 3 ，在 items2 中 weight = 4 ，总重量为 3 + 4 = 7 。
     * value = 2 的物品在 items1 中 weight = 2 ，在 items2 中 weight = 2 ，总重量为 2 + 2 = 4 。
     * value = 7 的物品在 items2 中 weight = 1 ，总重量为 1 。
     * 所以，我们返回 [[1,7],[2,4],[7,1]] 。
     * 提示：
     * 1 <= items1.length, items2.length <= 1000
     * items1[i].length == items2[i].length == 2
     * 1 <= valuei, weighti <= 1000
     * items1 中每个 valuei 都是 唯一的 。
     * items2 中每个 valuei 都是 唯一的 。
     */
    public List<List<Integer>> mergeSimilarItems(int[][] items1, int[][] items2) {
        Map<Integer, Integer> map = new TreeMap<>();
        for (int[] ints : items1) {
            map.put(ints[0], map.getOrDefault(ints[0], 0) + ints[1]);
        }
        for (int[] ints : items2) {
            map.put(ints[0], map.getOrDefault(ints[0], 0) + ints[1]);
        }
        List<List<Integer>> ans = new ArrayList<>();
        map.forEach((k, v) -> ans.add(Arrays.asList(k, v)));
        return ans;
    }

    /**
     * 19. 删除链表的倒数第 N 个结点
     * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
     * 示例 1：
     * 输入：head = [1,2,3,4,5], n = 2
     * 输出：[1,2,3,5]
     * 示例 2：
     * 输入：head = [1], n = 1
     * 输出：[]
     * 示例 3：
     * 输入：head = [1,2], n = 1
     * 输出：[1]
     * 提示：
     * 链表中结点的数目为 sz
     * 1 <= sz <= 30
     * 0 <= Node.val <= 100
     * 1 <= n <= sz
     * 进阶：你能尝试使用一趟扫描实现吗？
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // 列表预存
//        List<ListNode> list = new ArrayList<>();
//        while (head != null) {
//            list.add(head);
//            head = head.next;
//        }
//        if (n == list.size()) {
//            return list.get(0).next;
//        }
//        ListNode prev = list.get(list.size() - n - 1);
//        prev.next = prev.next.next;
//        return list.get(0);

        // 双指针
        ListNode dummy = new ListNode(0, head);
        ListNode first = head;
        ListNode second = dummy;
        while (n-- > 0) {
            first = first.next;
        }
        while (first != null) {
            first = first.next;
            second = second.next;
        }
        second.next = second.next.next;
        return dummy.next;
    }

    /**
     * 766. 托普利茨矩阵
     * 给你一个 m x n 的矩阵 matrix 。如果这个矩阵是托普利茨矩阵，返回 true ；否则，返回 false 。
     * 如果矩阵上每一条由左上到右下的对角线上的元素都相同，那么这个矩阵是 托普利茨矩阵 。
     * 示例 1：
     * 输入：matrix = [[1,2,3,4],[5,1,2,3],[9,5,1,2]]
     * 1 2 3 4
     * 5 1 2 3
     * 9 5 1 2
     * 输出：true
     * 解释：
     * 在上述矩阵中, 其对角线为:
     * "[9]", "[5, 5]", "[1, 1, 1]", "[2, 2, 2]", "[3, 3]", "[4]"。
     * 各条对角线上的所有元素均相同, 因此答案是 True 。
     * 示例 2：
     * 输入：matrix = [[1,2],[2,2]]
     * 输出：false
     * 解释：
     * 对角线 "[1, 2]" 上的元素不同。
     * 提示：
     * m == matrix.length
     * n == matrix[i].length
     * 1 <= m, n <= 20
     * 0 <= matrix[i][j] <= 99
     * 进阶：
     * 如果矩阵存储在磁盘上，并且内存有限，以至于一次最多只能将矩阵的一行加载到内存中，该怎么办？
     * 如果矩阵太大，以至于一次只能将不完整的一行加载到内存中，该怎么办？
     */
    public boolean isToeplitzMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (i > 0 && j > 0 && matrix[i][j] != matrix[i - 1][j - 1]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 1594. 矩阵的最大非负积
     * 给你一个大小为 m x n 的矩阵 grid 。最初，你位于左上角 (0, 0) ，每一步，你可以在矩阵中 向右 或 向下 移动。
     * 在从左上角 (0, 0) 开始到右下角 (m - 1, n - 1) 结束的所有路径中，找出具有 最大非负积 的路径。路径的积是沿路径访问的单元格中所有整数的乘积。
     * 返回 最大非负积 对 109 + 7 取余 的结果。如果最大积为 负数 ，则返回 -1 。
     * 注意，取余是在得到最大积之后执行的。
     * 示例 1：
     * 输入：grid = [[-1,-2,-3],[-2,-3,-3],[-3,-3,-2]]
     * 输出：-1
     * 解释：从 (0, 0) 到 (2, 2) 的路径中无法得到非负积，所以返回 -1 。
     * 示例 2：
     * 输入：grid = [[1,-2,1],[1,-2,1],[3,-4,1]]
     * 输出：8
     * 解释：最大非负积对应的路径如图所示 (1 * 1 * -2 * -4 * 1 = 8)
     * 示例 3：
     * 输入：grid = [[1,3],[0,-4]]
     * 输出：0
     * 解释：最大非负积对应的路径如图所示 (1 * 0 * -4 = 0)
     * 提示：
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 15
     * -4 <= grid[i][j] <= 4
     */
    public static int maxProductPath(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        long[][] dpMax = new long[m][n];
        long[][] dpMin = new long[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i > 0 && j > 0) {
                    if (grid[i][j] > 0) {
                        dpMax[i][j] = grid[i][j] * Math.max(dpMax[i - 1][j], dpMax[i][j - 1]);
                        dpMin[i][j] = grid[i][j] * Math.min(dpMin[i - 1][j], dpMin[i][j - 1]);
                    } else {
                        dpMax[i][j] = grid[i][j] * Math.min(dpMin[i - 1][j], dpMin[i][j - 1]);
                        dpMin[i][j] = grid[i][j] * Math.max(dpMax[i - 1][j], dpMax[i][j - 1]);
                    }
                } else if (i == 0 && j > 0) {
                    dpMax[i][j] = dpMin[i][j] = grid[i][j] * dpMax[i][j - 1];
                } else if (i > 0) {
                    dpMax[i][j] = dpMin[i][j] = grid[i][j] * dpMax[i - 1][j];
                } else { // i == 0 && j == 0
                    dpMax[i][j] = dpMin[i][j] = grid[i][j];
                }
            }
        }
        if (dpMax[m - 1][n - 1] < 0) {
            return -1;
        }
        return (int) (dpMax[m - 1][n - 1] % 1000000007);
    }

    /**
     * 1515. 服务中心的最佳位置
     * 一家快递公司希望在新城市建立新的服务中心。
     * 公司统计了该城市所有客户在二维地图上的坐标，并希望能够以此为依据为新的服务中心选址：使服务中心 到所有客户的欧几里得距离的总和最小 。
     * 给你一个数组 positions ，其中 positions[i] = [xi, yi] 表示第 i 个客户在二维地图上的位置，返回到所有客户的 欧几里得距离的最小总和 。
     * 换句话说，请你为服务中心选址，该位置的坐标 [xcentre, ycentre] 需要使下面的公式取到最小值：
     * Math.sqrt(Math.pow(xc - xi) + Math.pow(yc - yi)) 求和 i=0...n-1
     * 与真实值误差在 10^-5之内的答案将被视作正确答案。
     * 示例 1：
     * 输入：positions = [[0,1],[1,0],[1,2],[2,1]]
     * 输出：4.00000
     * 解释：如图所示，你可以选 [xcentre, ycentre] = [1, 1] 作为新中心的位置，这样一来到每个客户的距离就都是 1，所有距离之和为 4 ，这也是可以找到的最小值。
     * 示例 2：
     * 输入：positions = [[1,1],[3,3]]
     * 输出：2.82843
     * 解释：欧几里得距离可能的最小总和为 sqrt(2) + sqrt(2) = 2.82843
     * 提示：
     * 1 <= positions.length <= 50
     * positions[i].length == 2
     * 0 <= xi, yi <= 100
     */
    public static double getMinDistSum(int[][] positions) {
        // Weiszfeld算法
        // 1.初始化，求算术平均值或中位数（优先使用中位数）
        // 2.根据公式迭代：
        // dis = Math.sqrt(Math.pow(x1 - xi) + Math.pow(y1 - yi))
        // x2 = (E(i=1...n) xi / dis) / (E(i=1...n) 1 / dis)
        // y2 = (E(i=1...n) yi / dis) / (E(i=1...n) 1 / dis)
        // 注意dis为0时，代表迭代点与数据项重合，跳过
        // 3.根据要求的阈值停止迭代

        double x = 0.0, y = 0.0;
        for (int[] position : positions) {
            x += position[0];
            y += position[1];
        }
        x /= positions.length;
        y /= positions.length;

        // 设置eps=1e-8是为了确保迭代足够接近最优解，从而保证总距离的误差满足题目要求的1e-5。
        // 这种设计是数值计算中常见的保守策略，通过牺牲少量额外计算步骤（通常只需多几次迭代），换取结果的可靠性。
        // 实际应用中，这一选择在精度与效率之间达到了合理平衡。
        // 实际编码中，通常会选择一个比题目要求小2~3个数量级的收敛阈值
        double eps = 1e-8;
        while (true) {
            double nextX = 0.0, nextY = 0.0, totalWeight = 0.0;
            for (int[] position : positions) {
                double distance = Math.sqrt(Math.pow(position[0] - x, 2) + Math.pow(position[1] - y, 2));
                if (distance < eps) {
                    continue;
                }
                double weight = 1.0 / distance;
                nextX += position[0] * weight;
                nextY += position[1] * weight;
                totalWeight += weight;
            }
            if (nextX == 0 && nextY == 0 && totalWeight == 0) { // 都为0说明所有点相同
                break;
            }
            nextX /= totalWeight;
            nextY /= totalWeight;
            if (Math.abs(nextX - x) < eps && Math.abs(nextY - y) < eps) {
                break;
            }
            x = nextX;
            y = nextY;
        }

        System.out.println(x);
        System.out.println(y);
        double ans = 0.0;
        for (int[] position : positions) {
            ans += Math.sqrt(Math.pow(position[0] - x, 2) + Math.pow(position[1] - y, 2));
        }
        return ans;
    }

    /**
     * 2610. 转换二维数组
     * 给你一个整数数组 nums 。请你创建一个满足以下条件的二维数组：
     * 二维数组应该 只 包含数组 nums 中的元素。
     * 二维数组中的每一行都包含 不同 的整数。
     * 二维数组的行数应尽可能 少 。
     * 返回结果数组。如果存在多种答案，则返回其中任何一种。
     * 请注意，二维数组的每一行上可以存在不同数量的元素。
     * 示例 1：
     * 输入：nums = [1,3,4,1,2,3,1]
     * 输出：[[1,3,4,2],[1,3],[1]]
     * 解释：根据题目要求可以创建包含以下几行元素的二维数组：
     * - 1,3,4,2
     * - 1,3
     * - 1
     * nums 中的所有元素都有用到，并且每一行都由不同的整数组成，所以这是一个符合题目要求的答案。
     * 可以证明无法创建少于三行且符合题目要求的二维数组。
     * 示例 2：
     * 输入：nums = [1,2,3,4]
     * 输出：[[4,3,2,1]]
     * 解释：nums 中的所有元素都不同，所以我们可以将其全部保存在二维数组中的第一行。
     * 提示：
     * 1 <= nums.length <= 200
     * 1 <= nums[i] <= nums.length
     */
    public List<List<Integer>> findMatrix(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        Map<Integer, Integer> numIdxMap = new HashMap<>();
        for (int num : nums) {
            Integer idx = numIdxMap.get(num);
            if (idx == null) { // 不存在num
                List<Integer> row;
                if (ans.isEmpty()) {
                    row = new ArrayList<>();
                    ans.add(row);
                } else {
                    row = ans.get(0);
                }
                row.add(num);
                numIdxMap.put(num, 0);
            } else {
                List<Integer> row;
                if (idx == ans.size() - 1) { // 最后一个num存在最后一个数组
                    row = new ArrayList<>();
                    ans.add(row);
                    numIdxMap.put(num, ans.size() - 1);
                } else {
                    row = ans.get(idx + 1);
                    numIdxMap.put(num, idx + 1);
                }
                row.add(num);
            }
        }
        return ans;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        int[][] pos = {{3, 3}, {3,3}, {3,3}};
        System.out.println(getMinDistSum(pos));
    }
}
